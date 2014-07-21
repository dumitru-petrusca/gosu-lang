/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser.coercers;

import gw.lang.parser.CoercionUtil;
import gw.lang.reflect.IType;

public class ByteCoercer extends BaseBoxedCoercer
{
  private static final ByteCoercer INSTANCE = new ByteCoercer();

  public Object coerceValue( IType typeToCoerceTo, Object value )
  {
    Integer integer = CoercionUtil.makeIntegerFrom(value);
    return integer.byteValue();
  }

  public static ByteCoercer instance()
  {
    return INSTANCE;
  }
}