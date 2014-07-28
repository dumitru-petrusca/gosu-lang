/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser;

import gw.lang.reflect.gs.IFileSystemGosuClassRepository;

public class FileSource implements ISource {

  private String _cache;
  private IFileSystemGosuClassRepository.IClassFileInfo _file;

  public FileSource(IFileSystemGosuClassRepository.IClassFileInfo file) {
    _file = file;
  }

  public String getSource() {
    if (_cache == null) {
      _cache = _file.getContent();
    }
    return _cache;
  }

}
