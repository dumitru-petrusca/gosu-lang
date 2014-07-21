/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.reflect.java;

import gw.lang.reflect.FunctionType;
import gw.lang.reflect.IFunctionType;
import gw.lang.reflect.IType;
import gw.lang.reflect.TypeSystem;
import gw.lang.reflect.TypeSystemShutdownListener;
import gw.lang.reflect.gs.ClassType;
import gw.lang.reflect.gs.GosuClassTypeLoader;
import gw.lang.reflect.gs.IGosuClass;
import gw.lang.reflect.gs.ISourceFileHandle;
import gw.lang.reflect.gs.StringSourceFileHandle;
import gw.util.StreamUtil;
import gw.util.concurrent.LockingLazyVar;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class GosuTypes {
  private static LockingLazyVar<IFunctionType> DEF_CTOR_TYPE = new LockingLazyVar<IFunctionType>() {
    protected IFunctionType init() {
      return new FunctionType( "__def_ctor", JavaTypes.pVOID(), null );
    }
  };
  public static final String IMONITORLOCK_NAME = "gw.lang.IMonitorLock";
  public static Map<String, IType> CACHE = new HashMap<String, IType>();

  static {
    TypeSystem.addShutdownListener(new TypeSystemShutdownListener() {
      public void shutdown() {
        DEF_CTOR_TYPE.clear();
        CACHE.clear();
      }
    });
  }

  public static IType AUTOCREATE() {
    return getType("gw.lang.Autocreate");
  }

  public static IType IDISPOSABLE() {
    return getType("gw.lang.IDisposable");
  }

  public static IType IMONITORLOCK() {
    return getType(IMONITORLOCK_NAME);
  }

  public static IFunctionType DEF_CTOR_TYPE() {
    return DEF_CTOR_TYPE.get();
  }

  public static IType getType(String fqn) {
    IType type = CACHE.get(fqn);
    if (type == null) {
      type = TypeSystem.getByFullNameIfValid(fqn, TypeSystem.getGlobalModule());
      if (type == null) {
        String path = fqn.replace('.', '/') + ".gs";
        byte[] content;
        InputStream is = null;
        try {
          is = GosuTypes.class.getClassLoader().getResourceAsStream(path);
          content = StreamUtil.getContent(is);
        } catch (IOException e) {
          throw new RuntimeException(e);
        } finally {
          try {
            is.close();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
        ISourceFileHandle sourceFile = new StringSourceFileHandle(fqn, new String(content), false, ClassType.Class);
        type = GosuClassTypeLoader.getDefaultClassLoader().makeNewClass(sourceFile);
      }
      CACHE.put(fqn, type);
    }
    return type;
  }
}
