package gw.lang.gosuc.simple;

import gw.config.CommonServices;
import gw.config.IMemoryMonitor;
import gw.config.IPlatformHelper;
import gw.fs.FileFactory;
import gw.fs.IDirectory;
import gw.fs.IFile;
import gw.lang.gosuc.GosucDependency;
import gw.lang.gosuc.GosucModule;
import gw.lang.init.GosuInitialization;
import gw.lang.parser.IParseIssue;
import gw.lang.parser.exceptions.ParseResultsException;
import gw.lang.parser.exceptions.ParseWarning;
import gw.lang.reflect.IType;
import gw.lang.reflect.TypeSystem;
import gw.lang.reflect.gs.IGosuClass;
import gw.lang.reflect.gs.ISourceFileHandle;
import gw.lang.reflect.module.IExecutionEnvironment;
import gw.lang.reflect.module.IModule;
import gw.util.concurrent.LocklessLazyVar;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

import static gw.lang.gosuc.simple.ICompilerDriver.*;

public class GosuCompiler implements IGosuCompiler {
  protected static ICompilerDriver _driver;
  protected GosuInitialization _gosuInitialization;
  protected File _compilingSourceFile;

  public GosuCompiler(ICompilerDriver driver) {
    _driver = driver;
  }

  public boolean compile(File sourceFile) throws Exception {
    _compilingSourceFile = sourceFile;

    IType type = getType(_compilingSourceFile);
    if (type == null) {
      _driver.sendCompileIssue(_compilingSourceFile, ERROR, 0, 0, 0, "Cannot find type in the Gosu Type System.");
      return false;
    }

    if (isCompilable(type)) {
      if (type.isValid()) {
        createOutputFiles((IGosuClass) type);
      } else {
        ParseResultsException parseException = ((IGosuClass) type).getParseResultsException();
        if (parseException != null) {
          for (IParseIssue issue : parseException.getParseIssues()) {
            int category = issue instanceof ParseWarning ? WARNING : ERROR;
            _driver.sendCompileIssue(_compilingSourceFile, category, issue.getTokenStart(), issue.getLine(), issue.getColumn(), issue.getUIMessage());
          }
        }
      }
    }

    return false;
  }

  private IType getType(File file) {
    IFile ifile = FileFactory.instance().getIFile(file);
    IModule module = TypeSystem.getGlobalModule();
    String[] typesForFile = TypeSystem.getTypesForFile(module, ifile);
    if (typesForFile.length != 0) {
      return TypeSystem.getByFullNameIfValid(typesForFile[0], module);
    }
    return null;
  }

  private boolean isCompilable(IType type) {
    IType doNotVerifyAnnotation = TypeSystem.getByFullNameIfValid("gw.testharness.DoNotVerifyResource");
    return type instanceof IGosuClass && !type.getTypeInfo().hasAnnotation(doNotVerifyAnnotation);
  }

  private void createOutputFiles(IGosuClass gsClass) {
    IDirectory moduleOutputDirectory = TypeSystem.getGlobalModule().getOutputPath();
    if (moduleOutputDirectory == null) {
      throw new RuntimeException("Can't make class file, no output path defined.");
    }

    final String outRelativePath = gsClass.getName().replace('.', File.separatorChar) + ".class";
    File child = new File(moduleOutputDirectory.getPath().getFileSystemPathString());
    mkdirs(child);
    try {
      for (StringTokenizer tokenizer = new StringTokenizer(outRelativePath, File.separator + "/"); tokenizer.hasMoreTokens(); ) {
        String token = tokenizer.nextToken();
        child = new File(child, token);
        if (!child.exists()) {
          if (token.endsWith(".class")) {
            createNewFile(child);
          } else {
            mkDir(child);
          }
        }
      }
      createClassFile(child, gsClass);
    } catch (Exception e) {
      e.printStackTrace();
      _driver.sendCompileIssue(_compilingSourceFile, ERROR, 0, 0, 0, combine("Cannot create .class files.", toMessage(e)));
    }

    maybeCopySourceFile(child.getParentFile(), gsClass, _compilingSourceFile);
  }

  private String toMessage(Throwable e) {
    String msg = e.getMessage();
    while (e.getCause() != null) {
      e = e.getCause();
      String newMsg = e.getMessage();
      if (newMsg != null) {
        msg = newMsg;
      }
    }
    return msg;
  }

  private String combine(String message1, String message2) {
    if (message1 == null) {
      message1 = "";
    } else {
      message1 = message1 + "\n";
    }
    return message1 + message2;
  }

  private void mkDir(File file) {
    file.mkdir();
  }

  private void mkdirs(File file) {
    file.mkdirs();
  }

  private void createNewFile(File file) throws IOException {
    file.createNewFile();
  }

  private void maybeCopySourceFile(File parent, IGosuClass gsClass, File sourceFile) {
    ISourceFileHandle sfh = gsClass.getSourceFileHandle();
    IFile srcFile = sfh.getFile();
    if (srcFile != null) {
      File file = new File(srcFile.getPath().getFileSystemPathString());
      if (file.isFile()) {
        try {
          File destFile = new File(parent, file.getName());
          copyFile(file, destFile);
          _driver.registerOutput(_compilingSourceFile, destFile);
        } catch (IOException e) {
          e.printStackTrace();
          _driver.sendCompileIssue(sourceFile, ERROR, 0, 0, 0, "Cannot copy source file to output folder.");
        }
      }
    }
  }

  public void copyFile(File sourceFile, File destFile) throws IOException {
    if (sourceFile.isDirectory()) {
      mkdirs(destFile);
      return;
    }

    if (!destFile.exists()) {
      mkdirs(destFile.getParentFile());
      createNewFile(destFile);
    }

    try (FileChannel source = new FileInputStream(sourceFile).getChannel();
         FileChannel destination = new FileOutputStream(destFile).getChannel()) {
      destination.transferFrom(source, 0, source.size());
    }
  }

  private void createClassFile(File outputFile, IGosuClass gosuClass) throws IOException {
    final byte[] bytes = TypeSystem.getGosuClassLoader().getBytes(gosuClass);
    try (OutputStream out = new FileOutputStream(outputFile)) {
      out.write(bytes);
      _driver.registerOutput(_compilingSourceFile, outputFile);
    }
    for (IGosuClass innerClass : gosuClass.getInnerClasses()) {
      final String innerClassName = String.format("%s$%s.class", outputFile.getName().substring(0, outputFile.getName().lastIndexOf('.')), innerClass.getRelativeName());
      File innerClassFile = new File(outputFile.getParent(), innerClassName);
      if (innerClassFile.isFile()) {
        createNewFile(innerClassFile);
      }
      createClassFile(innerClassFile, innerClass);
    }
  }

  public long initializeGosu(List<String> contentRoots, List<File> cfaModules, List<String> sourceFolders, List<String> classpath, String outputPath) {
    final long start = System.currentTimeMillis();

    CommonServices.getKernel().redefineService_Privileged(IMemoryMonitor.class, new CompilerMemoryMonitor());
    CommonServices.getKernel().redefineService_Privileged(IPlatformHelper.class, new CompilerPlatformHelper());

    IExecutionEnvironment execEnv = TypeSystem.getExecutionEnvironment();
    _gosuInitialization = GosuInitialization.instance(execEnv);
    GosucModule gosucModule = new GosucModule(
        IExecutionEnvironment.DEFAULT_SINGLE_MODULE_NAME, contentRoots, sourceFolders, classpath,
        outputPath, Collections.<GosucDependency>emptyList(), Collections.<String>emptyList());
    _gosuInitialization.initializeCompiler(gosucModule);

    return System.currentTimeMillis() - start;
  }

  public void unitializeGosu() {
    TypeSystem.shutdown(TypeSystem.getExecutionEnvironment());
    if (_gosuInitialization != null) {
      if (_gosuInitialization.isInitialized()) {
        _gosuInitialization.uninitializeCompiler();
      }
      _gosuInitialization = null;
    }
  }

  public boolean isPathIgnored(String sourceFile) {
    return CommonServices.getPlatformHelper().isPathIgnored(sourceFile);
  }
}
