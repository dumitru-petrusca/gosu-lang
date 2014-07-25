/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.parser;

import gw.lang.reflect.java.IJavaClassGenericArrayType;
import gw.lang.reflect.java.IJavaClassType;
import gw.lang.reflect.java.asm.IAsmType;
import gw.lang.reflect.module.IModule;

public class AsmGenericArrayTypeJavaClassGenericArrayType extends AsmTypeJavaClassType implements IJavaClassGenericArrayType {

  public AsmGenericArrayTypeJavaClassGenericArrayType(IAsmType genericArrayType) {
    super(genericArrayType);
  }

  @Override
  public IJavaClassType getGenericComponentType() {
    return AsmTypeJavaClassType.createType( getType().getComponentType());
  }

  @Override
  public IJavaClassType getConcreteType() {
    return null;
  }

  @Override
  public String getSimpleName() {
    return getType().getSimpleName();
  }
}
