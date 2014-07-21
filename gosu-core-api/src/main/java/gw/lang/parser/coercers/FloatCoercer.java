/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class FloatCoercer extends BaseBoxedCoercer
{
  private static final FloatCoercer INSTANCE = new FloatCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    return CoercionUtil.makeFloatFrom(value);
  }

  public static FloatCoercer instance()
  {
    return INSTANCE;
  }
}