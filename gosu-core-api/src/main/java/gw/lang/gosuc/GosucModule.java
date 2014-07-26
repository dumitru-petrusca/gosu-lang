/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.gosuc;

import gw.fs.IDirectory;
import gw.lang.parser.ISourceCodeTokenizer;
import gw.lang.parser.IToken;
import gw.lang.reflect.module.INativeModule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GosucModule implements INativeModule, Serializable {
  private String _name;
  private List<String> _contentRoots;
  private List<String> _allSourceRoots;
  private List<String> _excludedRoots;
  private List<String> _classpath;
  private String _outputPath;

  public GosucModule(String name,
                     List<String> contentRoots,
                     List<String> allSourceRoots,
                     List<String> classpath,
                     String outputPath,
                     List<String> excludedRoots) {

    _contentRoots = contentRoots;
    _allSourceRoots = new ArrayList<String>();
    for (String sourceRoot : allSourceRoots) {
      if (!sourceRoot.endsWith(".jar")) {
        _allSourceRoots.add(sourceRoot);
      }
    }
    _excludedRoots = new ArrayList<>(excludedRoots);
    _classpath = classpath;
    _outputPath = outputPath;
    _name = name;
  }

  public List<String> getContentRoots() {
    return _contentRoots;
  }

  public String getContentRoot() {
    return _contentRoots.get(0);
  }

  public List<String> getExcludedRoots() {
    return _excludedRoots;
  }

  public List<String> getClasspath() {
    return _classpath;
  }

  public String getName() {
    return _name;
  }

  @Override
  public Object getNativeModule() {
    return this;
  }

  @Override
  public IDirectory getOutputPath() {
    return _outputPath != null ? GosucUtil.getDirectoryForPath(_outputPath) : null;
  }

  public List<String> getAllSourceRoots() {
    return _allSourceRoots;
  }

  private String writeRoots(List<String> roots) {
    StringBuilder sb = new StringBuilder();
    for (String sourceRoot : roots) {
      sb.append("    ").append("\"").append(sourceRoot).append("\",\n");
    }
    return sb.toString();
  }
}
