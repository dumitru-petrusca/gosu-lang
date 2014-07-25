/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.reflect.java.asm;

import gw.util.cache.FqnCache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 */
public class AsmClassLoader {
  private final static AsmClassLoader CACHE = new AsmClassLoader();
  private FqnCache<AsmClass> _cache;

  public static AsmClass loadClass(String fqn, InputStream is) {
    AsmClassLoader loader = getAsmClassLoader();
    return loader.findClass( fqn, is );
  }

  private static AsmClassLoader getAsmClassLoader() {
    return CACHE;
  }

  private AsmClassLoader( ) {
    _cache = new FqnCache<AsmClass>();
  }

  private AsmClass findClass( String fqn, InputStream is ) {
    AsmClass asmClass = _cache.get( fqn );
    if( asmClass == null ) {
      asmClass = _cache.get( fqn );
      if( asmClass == null ) {
        asmClass = new AsmClass(getContent( is ) );
        _cache.add( fqn, asmClass );
      }
    }
    return asmClass;
  }

  private static byte[] getContent( InputStream is ) {
    byte[] buf = new byte[1024];
    ExposedByteArrayOutputStream out = new ExposedByteArrayOutputStream();
    while( true ) {
      int count = 0;
      try {
        count = is.read( buf );
      }
      catch( IOException e ) {
        throw new RuntimeException( e );
      }
      if( count < 0 ) {
        break;
      }
      out.write( buf, 0, count );
    }
    try {
      out.flush();
      is.close();
      return out.getByteArray();
    }
    catch( Exception e ) {
      throw new RuntimeException( e );
    }
  }

  public static class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
    public ExposedByteArrayOutputStream() {
      super( 1024 );
    }

    public byte[] getByteArray() {
      return buf;
    }
  }

}
