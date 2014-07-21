/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class DoubleCoercer extends BaseBoxedCoercer
{
  private static final DoubleCoercer INSTANCE = new DoubleCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    return CoercionUtil.makeDoubleFrom(value);
  }

  public static DoubleCoercer instance()
  {
    return INSTANCE;
  }
}