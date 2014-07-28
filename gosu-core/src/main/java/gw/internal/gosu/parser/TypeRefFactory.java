/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.parser;

import gw.internal.ext.org.objectweb.asm.Opcodes;
import gw.lang.reflect.IType;
import gw.lang.reflect.ITypeRefFactory;
import gw.lang.reflect.TypeSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * There is one TypeRefFactory per ModuleTypeLoader.
 */
public class TypeRefFactory implements ITypeRefFactory
{
//  private static boolean TRACE = false;
//  private static boolean VERIFY = false;
//  private static boolean ASM_CHECKER = false;
//  private static final int JAVA_VER = Opcodes.V1_5;
//  private static final Map<Class<? extends IType>, Class<? extends AbstractTypeRef>> ITYPE_PROXY_CLASS_BY_ITYPE_CLASS =
//    new HashMap<Class<? extends IType>, Class<? extends AbstractTypeRef>>();
  private final Map<String, IType> _refByName;
//  private boolean _bClearing;

  public TypeRefFactory()
  {
    _refByName = new ConcurrentHashMap<>();
  }

  /**
   * Wraps the actual class with a proxy.
   */
  @Override
  public IType create( IType type )
  {
    String strTypeName = TypeLord.getNameWithQualifiedTypeVariables( type, true );
    return getRefTheFastWay(type, strTypeName);
  }

  private IType getRefTheFastWay(IType type, String strTypeName) {
    IType ref = getRef(_refByName, strTypeName);
    if (ref == null) {
      TypeSystem.lock();
      try {
        ref = getRef(_refByName, strTypeName);
        if (ref == null) {
          ref = type;
          putRef(_refByName, strTypeName, ref);
          return ref;
        }
      } finally {
        TypeSystem.unlock();
      }
    }
    return ref;
  }

 @Override
  public IType get( IType type )
  {
    String strTypeName = TypeLord.getNameWithQualifiedTypeVariables( type, true );
    if( strTypeName == null || strTypeName.length() == 0 )
    {
      throw new IllegalStateException( "Type has no name" );
    }

    return getRef( _refByName, strTypeName);
  }

  @Override
  public IType get( String strTypeName )
  {
    return getRef( _refByName, strTypeName);
  }

  @Override
  public void clearCaches()
  {
    _refByName.clear();
  }

  @Override
  public boolean isClearing()
  {
    return false;
  }

  private static void putRef( Map<String, IType> map, String key, IType value )
  {
    map.put(key, value);
  }

  private static IType getRef(Map<String, IType> map, String key )
  {
    return map.get( key );
  }
}
