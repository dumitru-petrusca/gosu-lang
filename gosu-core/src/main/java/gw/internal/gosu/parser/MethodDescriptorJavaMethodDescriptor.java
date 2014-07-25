/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.parser;

import gw.lang.reflect.java.IJavaMethodDescriptor;
import gw.lang.reflect.java.IJavaClassMethod;
import gw.lang.reflect.IScriptabilityModifier;
import gw.lang.reflect.module.IModule;

import java.lang.reflect.Method;

public class MethodDescriptorJavaMethodDescriptor implements IJavaMethodDescriptor {
  private GWMethodDescriptor _md;
  private Method _method;

  public MethodDescriptorJavaMethodDescriptor(GWMethodDescriptor md) {
    _md = md;
    _method = md.getMethod();
    if( _method == null ) {
      throw new IllegalStateException( "MethodDescriptor without method." );
    }
  }

  private MethodJavaClassMethod javaMethod;

  @Override
  public IJavaClassMethod getMethod() {
    if (javaMethod == null) {
      javaMethod = new MethodJavaClassMethod(_method);
    }
    return javaMethod;
  }

  @Override
  public String getName() {
    return _md.getName();
  }

  @Override
  public boolean isHiddenViaFeatureDescriptor() {
    return _md.isHidden();
  }

  @Override
  public boolean isVisibleViaFeatureDescriptor(IScriptabilityModifier constraint) {
    return _md.isVisible(constraint);
  }
}