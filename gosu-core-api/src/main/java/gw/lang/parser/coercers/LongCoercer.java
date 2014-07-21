/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class LongCoercer extends BaseBoxedCoercer
{
  private static final LongCoercer INSTANCE = new LongCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    return CoercionUtil.makeLongFrom(value);
  }

  public static LongCoercer instance()
  {
    return INSTANCE;
  }
}