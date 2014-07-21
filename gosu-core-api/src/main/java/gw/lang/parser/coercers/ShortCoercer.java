/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class ShortCoercer extends BaseBoxedCoercer
{
  private static final ShortCoercer INSTANCE = new ShortCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    Integer integer = CoercionUtil.makeIntegerFrom(value);
    return integer.shortValue();
  }

  public static ShortCoercer instance()
  {
    return INSTANCE;
  }
}