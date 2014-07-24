package gw.lang.gosuc.simple;

import gw.config.CommonServices;
import gw.config.IMemoryMonitor;
import gw.config.IPlatformHelper;
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
  private final LocklessLazyVar<IType> doNotVerifyResourceType = new LocklessLazyVar<IType>() {
    protected IType init() {
      return TypeSystem.getByFullNameIfValid("gw.testharness.DoNotVerifyResource");
    }
  };
  protected static ICompilerDriver _driver;
  protected GosuInitialization _gosuInitialization;

  public GosuCompiler(ICompilerDriver driver) {
    _driver = driver;
  }

  public boolean compile(File sourceFile, Collection<File> outputFiles) throws Exception {
    IFile file = FileFactory.instance().getIFile(sourceFile);
    IModule module = TypeSystem.getExecutionEnvironment().getGlobalModule();
    List typeNames = Arrays.asList(TypeSystem.getTypesForFile(module, file));

    List<IType> types = new GosucCompiler(_driver).compile(typeNames);

    boolean hasErrors = false;
    for (IType type : types) {
      if (type instanceof IGosuClass) {
        if (outputFiles != null) {
          IDirectory moduleOutputDirectory = module.getOutputPath();
          if (moduleOutputDirectory != null) {
            String outRelativePath = type.getName().replace('.', File.separatorChar) + ".class";
            File outputFile = new File(moduleOutputDirectory.getPath().getFileSystemPathString(), outRelativePath);
            if (outputFile.exists() && outputFile.length() > 0) {
              collectInnerClassOutputFiles(outputFile, (IGosuClass)type, outputFiles);
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

    CommonServices.getKernel().redefineService_Privileged(IMemoryMonitor.class, new CompilerMemoryMonitor());
    CommonServices.getKernel().redefineService_Privileged(IPlatformHelper.class, new CompilerPlatformHelper());

    IExecutionEnvironment execEnv = TypeSystem.getExecutionEnvironment();
    _gosuInitialization = GosuInitialization.instance(execEnv);
    GosucModule gosucModule = new GosucModule(
        IExecutionEnvironment.DEFAULT_SINGLE_MODULE_NAME, contentRoots, sourceFolders, classpath,
        outputPath, Collections.<GosucDependency>emptyList(), Collections.<String>emptyList());
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

  protected boolean shouldCompile(IGosuClass gosuClass) {
    return !gosuClass.getTypeInfo().hasAnnotation(doNotVerifyResourceType.get());
  }
}
