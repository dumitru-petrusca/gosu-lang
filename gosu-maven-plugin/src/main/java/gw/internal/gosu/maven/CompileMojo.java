/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.maven;

import com.google.common.collect.Lists;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 */
@SuppressWarnings("unused")
@Mojo(name = "compile",
        threadSafe = true,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
        defaultPhase = LifecyclePhase.COMPILE)
public class CompileMojo extends AbstractCompileMojo {

  @Parameter(property = "project.build.outputDirectory")
  private File output;

  @Override
  protected File getOutputFolder() {
    return output;
  }

  @Override
  protected List<File> getSources() {
    List<File> sources = Lists.newArrayList();
    for (String source : mavenProject.getCompileSourceRoots()) {
      sources.add(new File(source));
    }
    return sources;
  }

  @Override
  protected List<String> getClassPath() {
    List<String> classPath = super.getClassPath();
    String outputDirectory = mavenProject.getBuild().getOutputDirectory();
    classPath.add(outputDirectory);
    return classPath;
  }

  protected List<String> getJreClassPath() {
    return Collections.emptyList();
  }
}
