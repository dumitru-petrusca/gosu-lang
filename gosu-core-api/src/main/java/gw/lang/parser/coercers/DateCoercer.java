/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class DateCoercer extends StandardCoercer
{
  private static final DateCoercer INSTANCE = new DateCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    return CoercionUtil.makeDateFrom(value);
  }

  public static DateCoercer instance()
  {
    return INSTANCE;
  }
}