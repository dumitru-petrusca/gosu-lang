package gw.lang.gosuc.simple;

import gw.config.CommonServices;
import gw.fs.FileFactory;
import gw.fs.IDirectory;
import gw.fs.IFile;
import gw.lang.gosuc.GosucCompiler;
import gw.lang.gosuc.GosucDependency;
import gw.lang.gosuc.GosucModule;
import gw.lang.init.GosuInitialization;
import gw.lang.parser.IParseIssue;
import gw.lang.parser.exceptions.ParseResultsException;
import gw.lang.reflect.IType;
import gw.lang.reflect.TypeSystem;
import gw.lang.reflect.gs.IGosuClass;
import gw.lang.reflect.module.IExecutionEnvironment;
import gw.lang.reflect.module.IModule;
import gw.util.concurrent.LocklessLazyVar;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GosuCompiler implements IGosuCompiler {
  protected static ICompilerDriver _driver;
  protected GosuInitialization _gosuInitialization;

  public GosuCompiler(ICompilerDriver driver) {
    _driver = driver;
  }

  private static int reportIssuesImpl(File file, List<IParseIssue> issues, int kind) throws Exception {
    for (IParseIssue issue : issues) {
      _driver.sendCompileIssue(file, kind, issue.getTokenStart().intValue(), issue.getLine(), issue.getColumn(), issue.getUIMessage());
    }
    return issues.size();
  }

  private boolean reportIssues(File file, ParseResultsException e) throws Exception {
    reportIssuesImpl(file, e.getParseWarnings(), ICompilerDriver.WARNING);
    final int errorCount = reportIssuesImpl(file, e.getParseExceptions(), ICompilerDriver.ERROR);
    return errorCount > 0;
  }

  public boolean compile(File sourceFile, Collection<File> outputFiles) throws Exception {
    IFile file = FileFactory.instance().getIFile(sourceFile);
    IModule module = TypeSystem.getExecutionEnvironment().getGlobalModule();
    List typeNames = Arrays.asList(TypeSystem.getTypesForFile(module, file));
    List<IType> types = new GosucCompiler().compile(typeNames);
    boolean hasErrors = false;
    for (IType type : types) {
      if (type instanceof IGosuClass) {
        final IGosuClass klass = (IGosuClass) type;
        final ParseResultsException e = klass.getParseResultsException();
        if (e != null && shouldReportIssues(klass)) {
          hasErrors |= reportIssues(sourceFile, e);
        }
        if (outputFiles != null) {
          IDirectory moduleOutputDirectory = module.getOutputPath();
          if (moduleOutputDirectory != null) {
            String outRelativePath = klass.getName().replace('.', File.separatorChar) + ".class";
            File outputFile = new File(moduleOutputDirectory.getPath().getFileSystemPathString(), outRelativePath);
            if (outputFile.exists() && outputFile.length() > 0) {
              collectInnerClassOutputFiles(outputFile, klass, outputFiles);
            }
          }
        }
      }
    }
    return hasErrors;
  }

  private void collectInnerClassOutputFiles(File outputFile, IGosuClass gosuClass, Collection<File> outputFiles) throws IOException {
    outputFiles.add(outputFile);
    for (IGosuClass innerClass : gosuClass.getInnerClasses()) {
      final String innerClassName = String.format("%s$%s.class", outputFile.getName().substring( 0, outputFile.getName().lastIndexOf( '.' ) ), innerClass.getRelativeName());
      File innerClassFile = new File( outputFile.getParent(), innerClassName );
      if (innerClassFile.exists() && innerClassFile.length() > 0) {
        collectInnerClassOutputFiles(innerClassFile, innerClass, outputFiles);
      }
    }
  }

  public void initializeGosu(List<String> contentRoots, List<File> cfaModules, List<String> sourceFolders, List<String> classpath, String outputPath) {
    final long start = System.currentTimeMillis();

    IExecutionEnvironment execEnv = TypeSystem.getExecutionEnvironment();
    _gosuInitialization = GosuInitialization.instance(execEnv);
    GosucModule gosucModule = new GosucModule("module name", contentRoots, sourceFolders, classpath, outputPath, Collections.<GosucDependency>emptyList(), Collections.<String>emptyList());
    _gosuInitialization.initializeCompiler(gosucModule);

    System.out.println("Initialized Gosu Compiler -> " + (System.currentTimeMillis() - start) + "ms");
  }

  public void unitializeGosu() {
    TypeSystem.shutdown(TypeSystem.getExecutionEnvironment());
    if (_gosuInitialization != null) {
      _gosuInitialization.uninitializeCompiler();
      _gosuInitialization = null;
    }
  }

  public boolean isPathIgnored(String sourceFile) {
    return CommonServices.getPlatformHelper().isPathIgnored(sourceFile);
  }

  protected boolean shouldReportIssues(IGosuClass gosuClass) {
    return false;
  }
}
