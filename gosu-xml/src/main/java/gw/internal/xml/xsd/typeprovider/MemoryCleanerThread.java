/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.xml.xsd.typeprovider;

import gw.config.ExecutionMode;

import java.util.ArrayList;
import java.util.List;

public class MemoryCleanerThread extends Thread {

  private static List<Runnable> _todo = new ArrayList<Runnable>();
  private static volatile boolean stop = false;

  public MemoryCleanerThread() {
    super( "XML Memory Cleaner" );
    setDaemon( true );
  }

  static {
      startThread();
  }

  @Override
  public void run() {
    //noinspection InfiniteLoopStatement
    while ( !stop ) {
      List<Runnable> todo;
      synchronized( MemoryCleanerThread.class ) {
        while ( _todo.isEmpty() && !stop ) {
          try {
            MemoryCleanerThread.class.wait();
          }
          catch ( InterruptedException e ) {
            e.printStackTrace();
          }
        }
        todo = _todo;
        _todo = new ArrayList<Runnable>();
      }
      for ( Runnable runnable : todo ) {
        try {
          runnable.run();
        }
        catch ( Throwable t ) {
          t.printStackTrace();
        }
      }
    }
  }

  public static synchronized void invokeLater( Runnable r ) {
    if (ExecutionMode.isRuntime()) {
      _todo.add( r );
      MemoryCleanerThread.class.notifyAll();
    }
  }

  public static void startThread() {
    if (ExecutionMode.isRuntime()) {
      new MemoryCleanerThread().start();
    }
  }

  public static synchronized void stopCleaner() {
    if (ExecutionMode.isRuntime()) {
      stop = true;
      MemoryCleanerThread.class.notifyAll();
    }
  }
}
