/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class BooleanCoercer extends BaseBoxedCoercer
{
  private static final BooleanCoercer INSTANCE = new BooleanCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    return CoercionUtil.makeBooleanFrom(value);
  }

  public static BooleanCoercer instance()
  {
    return INSTANCE;
  }
}
