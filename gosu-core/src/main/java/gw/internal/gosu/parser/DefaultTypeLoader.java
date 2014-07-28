/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.parser;

import gw.config.ExecutionMode;
import gw.fs.IDirectory;
import gw.internal.gosu.coercer.FunctionToInterfaceClassGenerator;
import gw.internal.gosu.compiler.GosuClassLoader;
import gw.internal.gosu.parser.java.classinfo.JavaSourceClass;
import gw.lang.reflect.IDefaultTypeLoader;
import gw.lang.reflect.IErrorType;
import gw.lang.reflect.IExtendedTypeLoader;
import gw.lang.reflect.IType;
import gw.lang.reflect.RefreshKind;
import gw.lang.reflect.RefreshRequest;
import gw.lang.reflect.SimpleTypeLoader;
import gw.lang.reflect.TypeSystem;
import gw.lang.reflect.gs.GosuClassPathThing;
import gw.lang.reflect.gs.IGosuClassLoader;
import gw.lang.reflect.gs.IGosuObject;
import gw.lang.reflect.gs.ISourceFileHandle;
import gw.lang.reflect.gs.TypeName;
import gw.lang.reflect.java.IJavaClassInfo;
import gw.lang.reflect.java.IJavaClassType;
import gw.lang.reflect.java.IJavaType;
import gw.lang.reflect.java.JavaTypes;
import gw.lang.reflect.java.asm.AsmClass;
import gw.lang.reflect.module.IModule;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultTypeLoader extends SimpleTypeLoader implements IExtendedTypeLoader, IDefaultTypeLoader {
  private ClassCache _classCache;
  private IGosuClassLoader _gosuClassLoader;            //## todo: use a ConcurrentWeakValueHashMap here?
  private Map<String, IJavaClassInfo> _classInfoCache = new ConcurrentHashMap<String, IJavaClassInfo>(1000);
  protected Set<String> _namespaces;

  public static DefaultTypeLoader instance() {
    return TypeSystem.getGlobalModule().getModuleTypeLoader().getTypeLoader(DefaultTypeLoader.class);
  }

  public DefaultTypeLoader(final IModule module) {
    super();
    _classCache = new ClassCache();
  }

  @Override
  public IType getType(String fullyQualifiedName) {
    IJavaClassInfo classInfo = getJavaClassInfo( fullyQualifiedName );
    if (classInfo != null) {
      return JavaType.create(classInfo, this);
    } else {
      return null;
    }
  }

  public IJavaType getInnerType(String fqn) {
    IType cachedType = ((ModuleTypeLoader) TypeSystem.getGlobalModule().getModuleTypeLoader()).getCachedType( fqn );
    if (cachedType != null && !(cachedType instanceof IErrorType)) {
      return (IJavaType) cachedType;
    } else {
      return (IJavaType) getType(fqn);
    }
  }

  public IJavaClassInfo getJavaClassInfo(String fullyQualifiedName) {
    if (fullyQualifiedName.startsWith("[")) {
      throw new IllegalArgumentException("Cannot call getJavaClassInfo with a raw array descriptor");
    }
    if (ExecutionMode.isIDE()) {
      return null;
    }

    // strip off all trailing array brackets "[]"
    String fqnNoArrays = ModuleTypeLoader.stripArrayBrackets(fullyQualifiedName);

    IJavaClassInfo result = _classInfoCache.get(fqnNoArrays);
    if (result == null) {
      result = resolveJavaClassInfo(fqnNoArrays);
      if (result == null) {
        result = IJavaClassInfo.NULL_TYPE;
      }
      _classInfoCache.put(fqnNoArrays, result);
    }
    if( result != IJavaClassType.NULL_TYPE ) {
      int numArrays = (fullyQualifiedName.length() - fqnNoArrays.length()) / 2;
      for( int i = 0; i < numArrays; i++ )
      {
        result = result.getArrayType();
      }
    }
    return result == IJavaClassInfo.NULL_TYPE ? null : result;
  }

  @Override
  public IJavaClassInfo getJavaClassInfoForClassDirectly(Class clazz) {
    return new ClassJavaClassInfo(clazz);
  }

  public IJavaClassInfo getJavaClassInfo(Class aClass) {
    if( ExecutionMode.isIDE() ) {
      return null;
    }
    String fullyQualifiedName = aClass.getName().replace('$', '.');
    IJavaClassInfo result = _classInfoCache.get( fullyQualifiedName );
    if( result == null ) {
      result = new ClassJavaClassInfo( aClass);
      _classInfoCache.put( fullyQualifiedName, result );
    }
    return result == IJavaClassInfo.NULL_TYPE ? null : result;
  }

  public IJavaClassInfo getJavaClassInfo(AsmClass aClass) {
    if (ExecutionMode.isIDE()) {
      return null;
    } else {
      String fullyQualifiedName = aClass.getName().replace('$', '.');
      IJavaClassInfo result = _classInfoCache.get(fullyQualifiedName);
      if (result == null) {
        result = new AsmClassJavaClassInfo(aClass);
        _classInfoCache.put(fullyQualifiedName, result);
      }
      return result == IJavaClassInfo.NULL_TYPE ? null : result;
    }
  }

  public IJavaClassInfo resolveJavaClassInfo(String fullyQualifiedName) {
    if (!ExecutionMode.isIDE()) {
      return getByClass(fullyQualifiedName);
    }

    ISourceFileHandle fileHandle = getSouceFileHandle( fullyQualifiedName );
    if (fileHandle == null) {
      return getByClass(fullyQualifiedName);
    }
    if( fileHandle.getParentType() != null && !fileHandle.getParentType().isEmpty() )
    {
      String parentType = fileHandle.getTypeNamespace();
      IJavaClassInfo parentClassInfo = getJavaClassInfo(parentType);
      if (parentClassInfo == null) {
        return null;
      }
      IJavaClassInfo[] declaredClasses = parentClassInfo.getDeclaredClasses();
      IJavaClassInfo inner = null;
      for (IJavaClassInfo declaredClass : declaredClasses) {
        String name = declaredClass.getName();
        // cache all inner classes now
        if (!_classInfoCache.containsKey(name)) {
          _classInfoCache.put(name, declaredClass);
        }
        //## todo: these names should be consistent
        if (fullyQualifiedName.equals(name) ||
            name.replace( '$', '.').equals( fullyQualifiedName ) ) {
          inner = declaredClass;
        }
      }
      return inner;
    }

    return JavaSourceClass.createTopLevel(fileHandle);
  }

  @Override
  public ISourceFileHandle getSouceFileHandle(String qualifiedName) {
    ISourceFileHandle aClass = TypeSystem.getGlobalModule().getFileRepository().findClass(qualifiedName, EXTENSIONS_ARRAY);
    if (aClass == null || !aClass.getClassType().isJava()) {
      return null;
    }
    return aClass;
  }

  private IJavaClassInfo getByClass(String className) {
    DefaultTypeLoader loader = (DefaultTypeLoader)TypeSystem.getGlobalModule().getTypeLoaders(IDefaultTypeLoader.class).get( 0 );
    if(ExecutionMode.isRuntime() ) {
      Class theClass = loader.loadClass( className );
      if( theClass == null ) {
        return null;
      }
      return getJavaClassInfo( theClass);
    } else {
      AsmClass theClass = loader.loadAsmClass( className );
      if( theClass == null ) {
        return null;
      }
      return getJavaClassInfo( theClass);
    }
  }

  @Override
  public IType getIntrinsicTypeFromObject(Object object) {
    if (object == null) {
      return null;
    }

    if (object instanceof IGosuObject) {
      return ((IGosuObject) object).getIntrinsicType();
    }

    if (object instanceof IType) {
      return MetaType.get((IType) object);
    }

    return TypeSystem.get(object.getClass()); // Call allllll the way back through the stack if this is a Class;
  }

  @Override
  public Set<String> computeTypeNames() {
    Set<String> allTypeNames = _classCache.getAllTypeNames();
    allTypeNames.addAll(TypeSystem.getGlobalModule().getFileRepository().getAllTypeNames(DOT_JAVA_EXTENSION));
    return allTypeNames;
  }

  @Override
  public URL getResource(String name) {
    return getGosuClassLoader().getActualLoader().getResource(name);
  }

  @Override
  public void refreshedTypesImpl(RefreshRequest request) {
    for (String fullyQualifiedTypeName : request.types) {
      _classCache.remove(fullyQualifiedTypeName);
      _classInfoCache.remove(fullyQualifiedTypeName);
    }
    TypeSystem.getGlobalModule().getFileRepository().typesRefreshed( request );
  }

  @Override
  public boolean isCaseSensitive() {
    return true;
  }

  @Override
  public List<String> getHandledPrefixes() {
    return Collections.emptyList();
  }

  @Override
  public boolean handlesNonPrefixLoads() {
    return true;
  }

  public void refreshedImpl() {
    JavaType.unloadTypes();
    if (ExecutionMode.isRuntime()) {
      _classCache.clearClasspathInfo();
    } else {
      _classCache.dispose();
      _classCache = new ClassCache();
      dumpGosuClassLoader();
    }
    _namespaces = null;

    _classInfoCache.clear();

    TypeSystem.getGlobalModule().getFileRepository().typesRefreshed( null );

    JavaTypes.flushCache();
  }

  public void clearMisses() {
    Iterator<Map.Entry<String,IJavaClassInfo>> iterator = _classInfoCache.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, IJavaClassInfo> entry = iterator.next();
      if (entry.getValue() == IJavaClassInfo.NULL_TYPE) {
        iterator.remove();
      }
    }
  }

  public Class loadClass(String className) {
    return _classCache.loadClass( className );
  }

  public AsmClass loadAsmClass(String className) {
    return _classCache.loadAsmClass( className );
  }

  public IGosuClassLoader getGosuClassLoader() {
    if (_gosuClassLoader == null) {
      _gosuClassLoader = new GosuClassLoader(TypeSystem.getGlobalModule().getModuleClassLoader());
      GosuClassPathThing.init();
    }
    return _gosuClassLoader;
  }

  void dumpGosuClassLoader() {
    if (_gosuClassLoader != null) {
      _gosuClassLoader.dumpAllClasses();
      // The FunctionToInterfaceClassGenerator holds onto Class objects that could be from
      // the GosuClassLoader; if we don't clear its map, we'll end up leaking the old GosuClassLoader
      FunctionToInterfaceClassGenerator.clearCachedClasses();
      // The module classloader should be a PluginContainer, which should be new at this point; the old one should be disposed
      if( !haveWeRecreatedTheModuleLoader() ) {
        // Gosu's classloader hasn't been recreated, which implies the _classCache was not recreated,
        // which means we have to manually dump and recreate the plugin loader and the module loader.
        _classCache.reassignClassLoader();
      }
      _gosuClassLoader.assignParent( TypeSystem.getGlobalModule().getModuleClassLoader() );
      GosuClassPathThing.init();
    }
  }

  private boolean haveWeRecreatedTheModuleLoader() {
    ClassLoader gosusLoader = _gosuClassLoader.getActualLoader();
    ClassLoader csr = TypeSystem.getGlobalModule().getModuleClassLoader();
    while( csr != null ) {
      if( csr == gosusLoader ) {
        return false;
      }
      csr = csr.getParent();
    }
    return true;
  }

  @Override
  public Set<String> getExtensions() {
    return EXTENSIONS;
  }

  @Override
  public boolean hasNamespace(String namespace) {
    return TypeSystem.getGlobalModule().getFileRepository().hasNamespace(namespace) > 0 || _classCache.hasNamespace(namespace);
  }

  @Override
  public Set<String> getAllNamespaces() {
    if (_namespaces == null) {
      try {
        _namespaces = TypeSystem.getNamespacesFromTypeNames(getAllTypeNames(), new HashSet<String>());
      } catch (NullPointerException e) {
        //!! hack to get past dependency issue with tests
        return Collections.emptySet();
      }
    }
    return _namespaces;
  }

  @Override
  public void refreshedNamespace(String namespace, IDirectory dir, RefreshKind kind) {
    if (_namespaces != null) {
      if (kind == RefreshKind.CREATION) {
        _namespaces.add(namespace);
      } else if (kind == RefreshKind.DELETION) {
        _namespaces.remove(namespace);
      }
    }
  }

  @Override
  public Set<TypeName> getTypeNames(String namespace) {
    Set<TypeName> names = new HashSet<TypeName>();
    names.addAll(TypeSystem.getGlobalModule().getFileRepository().getTypeNames(namespace, Collections.singleton(".java"), this));
    names.addAll(_classCache.getTypeNames(namespace));
    return names;
  }

}
