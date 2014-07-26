/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.reflect.module;

import gw.fs.IDirectory;
import gw.fs.IFile;
import gw.fs.IResource;
import gw.lang.reflect.ITypeLoader;
import gw.lang.UnstableAPI;
import gw.lang.reflect.gs.IFileSystemGosuClassRepository;

import java.io.File;
import java.net.URL;
import java.util.List;

@UnstableAPI
public interface IModule
{
  public static final String CONFIG_RESOURCE_PREFIX = "config";
  public static final String CONFIG_RESOURCE_PREFIX_2 = "./config";

  IExecutionEnvironment getExecutionEnvironment();

  /**
   * @return A unique name relative to all other modules in a given execution 
   *   environment.
   */
  String getName();

  void setName(String name);

  List<IDirectory> getRoots();

  void setRoots(List<IDirectory> roots);

  ITypeLoaderStack getModuleTypeLoader();

  /**
   * @return The path[s] having source files that should be exposed to this 
   *   module.
   */
  List<IDirectory> getSourcePath();

  void setSourcePath( List<IDirectory> path );

  List<IDirectory> getJavaClassPath();

  void setJavaClassPath(List<IDirectory> paths);

  List<IDirectory> getExcludedPaths();

  void setExcludedPaths(List<IDirectory> paths);

  /**
   * Configure both source and Java classpaths of the module in a semi-automated way. First parameter
   * is Java classpath. Second parameter is extended with all paths from Java classpath that are marked
   * to have Gosu "sources" (through MANIFEST.MF with Contains-Sources header) and used as Gosu source path.
   *
   * @param classpath
   */
  void configurePaths(List<IDirectory> classpath, List<IDirectory> sourcePaths);

  /**
   * Returns typeloaders of the given class that are local to this module as well as such
   * typeloaders from dependent modules.
   *
   * @param typeLoaderClass
   * @param <T>
   * @return
   */
  <T extends ITypeLoader> List<? extends T> getTypeLoaders(Class<T> typeLoaderClass);

  IFileSystemGosuClassRepository getFileRepository();

  String pathRelativeToRoot(IResource resource);

  /**
   * Get class loader, associated with this module.
   * @return
   */
  ClassLoader getModuleClassLoader();

  void disposeLoader();
}

