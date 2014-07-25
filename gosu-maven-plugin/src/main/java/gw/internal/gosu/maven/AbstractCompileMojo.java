/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.maven;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gw.lang.gosuc.simple.GosuCompiler;
import gw.lang.gosuc.simple.SoutCompilerDriver;
import gw.lang.reflect.gs.GosuClassPathThing;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 */
@SuppressWarnings("unused")
public abstract class AbstractCompileMojo extends AbstractMojo {
  Set<String> EXTENSIONS = Sets.newHashSet("gs", "gsx", "gsp", "gst");

  @Component
  protected MavenProject mavenProject;

  @Parameter(property = "gosu.compile.skip")
  protected boolean skip;

  @Parameter
  protected List<String> exclusions = Collections.emptyList();

  @Parameter
  protected List<File> roots;

  /**
   * Ignore all compilation errors.
   */
  @Parameter(defaultValue = "false")
  protected boolean ignoreErrors;

  // We need really global lock due to the URL#handlers being JVM-wide global.
  private final static Object LOCK = "reallygloballock";

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    Log log = getLog();
    if (skip) {
      log.info("Skipping Gosu compiler plugin");
      return;
    }

    // Sure, we are thread safe.
    synchronized (LOCK) {
      // FIXME-isd: instead of resetting gosu protocol handler, we would rather like to have total control over
      // Gosu classloading.
      GosuClassPathThing.cleanup();

      List<String> contentRoots = Lists.newArrayList();
      for (File root : getRoots()) {
        contentRoots.add(root.getAbsolutePath());
      }
      List<String> sources = Lists.newArrayList();
      for (File root : getSources()) {
        sources.add(root.getAbsolutePath());
      }
      List<String> classpath = getClasspath();

      SoutCompilerDriver driver = new SoutCompilerDriver();
      GosuCompiler compiler = new GosuCompiler(driver);
      long initTime, compileTime;
      try {
        String outputPath = getOutputFolder().getAbsolutePath();
        initTime = compiler.initializeGosu(contentRoots, null, sources, classpath, outputPath);
        List<File> compilableFiles = getCompilableFiles(sources);
        log.info(String.format("Compiling %s source file to %s", compilableFiles.size(), outputPath));
        logLine();
        compileTime = System.currentTimeMillis();
        for (File compilableFile : compilableFiles) {
          compiler.compile(compilableFile);
        }
        compileTime = System.currentTimeMillis() - compileTime;
      } catch (Exception e) {
        e.printStackTrace();
        throw new MojoExecutionException("Failed to compile gosu classes", e);
      } finally {
        compiler.unitializeGosu();
      }

      if (driver.hasErrors()) {
        log.error("COMPILATION ERROR :");
        logLine();
        for (String error : driver.getErrors()) {
          log.error(error);
        }
        for (String warning : driver.getWarnings()) {
          log.error(warning);
        }
        throw new MojoFailureException("Gosu compilation failed.");
      }

      log.info(String.format("Initialization took %sms.", initTime));
      log.info(String.format("Compilation took %sms.", compileTime));
    }
  }

  private void logLine() {
    getLog().info("-------------------------------------------------------------");
  }

  private List<File> getCompilableFiles(List<String> sources) {
    List<File> allFiles = new ArrayList<>();
    for (String source : sources) {
      collectCompilableFiles(new File(source), allFiles);
    }
    return allFiles;
  }

  private void collectCompilableFiles(File file, List<File> allFiles) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        for (File childFile : files) {
          collectCompilableFiles(childFile, allFiles);
        }
      }
    } else if (file.isFile()) {
      if (isCompilable(file)) {
        allFiles.add(file);
      }
    }
  }



  private boolean isCompilable(File file) {
    String fileName = file.getName();
    String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

    if (!EXTENSIONS.contains(ext)) {
      return false;
    }
    if (fileName.contains("Errant")) {
      return false;
    }
    for (String exclusion : exclusions) {
      if (fileName.startsWith(exclusion)) {
        return false;
      }
    }
    return true;
  }

  private List<String> getClasspath() {
    List<String> classpath = Lists.newArrayList();

    // Let's get all JARs from the bootstrap classloader.
    URLClassLoader bootstrap = (URLClassLoader) ClassLoader.getSystemClassLoader().getParent();
    for (URL url : bootstrap.getURLs()) {
      File file;
      try {
        file = new File(url.toURI());
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
      classpath.add(file.getAbsolutePath());
    }

    // Wee need rt.jar, too
    URL url = bootstrap.getResource("java/lang/Object.class");
    if (url != null) {
      File rtJar;
      try {
        final JarURLConnection connection = (JarURLConnection) url.openConnection();
        rtJar = new File(connection.getJarFileURL().toURI());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      classpath.add(rtJar.getAbsolutePath());
    }

    classpath.addAll(getClassPath());
    classpath.addAll(getDependencies());
    classpath.addAll(getJreClassPath());
    return classpath;
  }

  protected abstract File getOutputFolder();

  protected abstract List<File> getSources();

  protected abstract List<String> getClassPath();

  protected List<String> getJreClassPath() {
    return Collections.emptyList();
  }

  protected List<File> getRoots() {
    return roots == null ? Collections.singletonList(mavenProject.getBasedir()) : roots;
  }

  protected List<String> getDependencies() {
    List<String> cp = Lists.newArrayList();
    for (Artifact art : mavenProject.getArtifacts()) {
      if (art.getArtifactHandler().isAddedToClasspath() && art.getFile() != null) {
        cp.add(art.getFile().getAbsolutePath());
      }
    }
    return cp;
  }

}
