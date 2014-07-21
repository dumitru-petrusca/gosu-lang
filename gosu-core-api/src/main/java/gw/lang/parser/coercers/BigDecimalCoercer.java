/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class BigDecimalCoercer extends StandardCoercer
{
  private static final BigDecimalCoercer INSTANCE = new BigDecimalCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    return CoercionUtil.makeBigDecimalFrom(value);
  }

  public static BigDecimalCoercer instance()
  {
    return INSTANCE;
  }
}