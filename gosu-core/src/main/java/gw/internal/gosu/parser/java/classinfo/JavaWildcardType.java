/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.internal.gosu.parser.java.classinfo;

import gw.lang.parser.TypeVarToTypeMap;
import gw.lang.reflect.IType;
import gw.lang.reflect.java.IJavaClassType;
import gw.lang.reflect.java.IJavaClassWildcardType;
import gw.lang.reflect.module.IModule;

public class JavaWildcardType implements IJavaClassWildcardType {
  private IJavaClassType _bound;

  public JavaWildcardType( IJavaClassType bound ) {
    _bound = bound;
  }

  @Override
  public IJavaClassType getConcreteType() {
    return getUpperBound();
  }

  @Override
  public String getNamespace() {
    return null;
  }

  @Override
  public String getName() {
    return "? extends " + _bound.getName();
  }

  @Override
  public String getSimpleName() {
    return getName();
  }

  public String toString() {
    return getName();
  }

  @Override
  public IType getActualType( TypeVarToTypeMap typeMap) {
    return _bound.getActualType(typeMap);
  }

  @Override
  public IType getActualType( TypeVarToTypeMap typeMap, boolean bKeepTypeVars) {
    return _bound.getActualType(typeMap, bKeepTypeVars);
  }

  @Override
  public IJavaClassType getUpperBound() {
    return _bound;
  }

  public void setBound(IJavaClassType bound) {
    _bound = bound;
  }
}
