/*
 * Copyright 2013 Guidewire Software, Inc.
 */

package gw.internal.gosu.parser;

import gw.config.BaseService;
import gw.fs.IDirectory;
import gw.lang.GosuShop;
import gw.lang.parser.CoercionUtil;
import gw.lang.parser.GlobalScope;
import gw.lang.parser.IAttributeSource;
import gw.lang.parser.IBlockClass;
import gw.lang.parser.ICoercer;
import gw.lang.parser.IGosuParser;
import gw.lang.parser.ILanguageLevel;
import gw.lang.parser.coercers.*;
import gw.lang.parser.exceptions.IncompatibleTypeException;
import gw.lang.parser.exceptions.ParseIssue;
import gw.lang.parser.expressions.IQueryExpression;
import gw.lang.parser.expressions.IQueryExpressionEvaluator;
import gw.lang.reflect.AbstractTypeSystemListener;
import gw.lang.reflect.IBlockType;
import gw.lang.reflect.IEntityAccess;
import gw.lang.reflect.IErrorType;
import gw.lang.reflect.IFeatureInfo;
import gw.lang.reflect.IFunctionType;
import gw.lang.reflect.IGosuClassLoadingObserver;
import gw.lang.reflect.IHasJavaClass;
import gw.lang.reflect.IMetaType;
import gw.lang.reflect.IPropertyInfo;
import gw.lang.reflect.IType;
import gw.lang.reflect.ITypeLoader;
import gw.lang.reflect.RefreshRequest;
import gw.lang.reflect.TypeSystem;
import gw.lang.reflect.features.FeatureReference;
import gw.lang.reflect.java.GosuTypes;
import gw.lang.reflect.java.IJavaType;
import gw.lang.reflect.java.JavaTypes;
import gw.util.IFeatureFilter;
import gw.util.ILogger;
import gw.util.SystemOutLogger;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 */
public class DefaultEntityAccess extends BaseService implements IEntityAccess
{
  public static final SystemOutLogger DEFAULT_LOGGER = new SystemOutLogger(SystemOutLogger.LoggingLevel.WARN);
  private static DefaultEntityAccess g_instance;
  private List<IGosuClassLoadingObserver> _classLoadingObservers;

  public static DefaultEntityAccess instance()
  {
    return g_instance == null ? g_instance = new DefaultEntityAccess() : g_instance;
  }

  private Map _scopes = new HashMap();

  /**
   */
  public DefaultEntityAccess()
  {
    _classLoadingObservers = Collections.synchronizedList(new ArrayList<IGosuClassLoadingObserver>());
  }

  public ITypeLoader getDefaultTypeLoader()
  {
    return TypeSystem.getDefaultTypeLoader();
  }

  /**
   * @return x
   */
  @Override
  public boolean isDomainInstance( Object value )
  {
    return false;
  }

  /**
   * @return x
   */
  @Override
  public boolean isEntityClass( IType cls )
  {
    return false;
  }

  @Override
  public boolean isViewEntityClass( IType type )
  {
    return false;
  }

  @Override
  public IType getPrimaryEntityClass( IType type )
  {
    return null;
  }

  /**
   * @return x
   */
  @Override
  public Object getEntityInstanceFrom( Object entity, IType classEntity )
  {
    return null;
  }

  /**
   * @return x
   */
  @Override
  public boolean areBeansEqual( Object bean1, Object bean2 )
  {
    return bean1.equals( bean2 );
  }

  /**
   * @param type
   * @param value
   * @return
   * @throws RuntimeException
   */
  @Override
  public boolean verifyValueForType( IType type, Object value ) throws RuntimeException
  {
    try
    {
      IType valueType = TypeSystem.getFromObject(value);
      CoercionUtil.verifyTypesComparable(type, valueType, false);
    }
    catch( ParseIssue pe )
    {
      throw new IncompatibleTypeException( "Value of type: " + TypeSystem.getFromObject(value).getName() +
                                           " is not compatible with symbol type: " + type.getName() );
    }
    return true;
  }

  /**
   * @return x
   */
  @Override
  public long getHashedEntityId( String strId, IType classEntity )
  {
    return -1;
  }

  @Override
  public ILogger getLogger()
  {
    return DEFAULT_LOGGER;
  }

  @Override
  public Date getCurrentTime()
  {
    return new Date();
  }

  @Override
  public IQueryExpressionEvaluator getQueryExpressionEvaluator( IQueryExpression queryExpression )
  {
    return null;
  }

  @Override
  public IFeatureFilter getQueryExpressionFeatureFilter()
  {
    return new IFeatureFilter() {
      @Override
      public boolean acceptFeature(IType beanType, IFeatureInfo fi) {
        return false;
      }
    };
  }

  @Override
  public ClassLoader getPluginClassLoader()
  {
    return DefaultEntityAccess.class.getClassLoader();
  }

  @Override
  public IAttributeSource getAttributeSource( GlobalScope scope )
  {
    IAttributeSource source = (IAttributeSource)_scopes.get( scope );
    if( source == null )
    {
      source = new ThreadLocalAttributeSource( scope );
      _scopes.put( scope, source );
    }

    return source;
  }

  public void clearAttributeScopes()
  {
    _scopes.clear();
  }

  private static class ThreadLocalAttributeSource extends AbstractTypeSystemListener implements IAttributeSource
  {
    private GlobalScope _scope;

    private ThreadLocal _values = new ThreadLocal();

    public ThreadLocalAttributeSource( GlobalScope scope )
    {
      _scope = scope;
      TypeSystem.addTypeLoaderListenerAsWeakRef(this);
    }

    public GlobalScope getScope()
    {
      return _scope;
    }

    @Override
    public boolean hasAttribute( String strAttr )
    {
      Map map = getMap();
      return map.containsKey( strAttr );
    }

    @Override
    public Object getAttribute( String strAttr )
    {
      Map map = getMap();
      return map.get(strAttr);
    }

    @Override
    public void setAttribute( String strAttr, Object value )
    {
      Map map = getMap();
      map.put( strAttr, value );
    }

    @Override
    public boolean equals( Object o )
    {
      if( this == o )
      {
        return true;
      }
      if( o == null || getClass() != o.getClass() )
      {
        return false;
      }

      final ThreadLocalAttributeSource that = (ThreadLocalAttributeSource)o;

      return _scope.equals( that._scope );
    }

    @Override
    public int hashCode()
    {
      return _scope.hashCode();
    }

    private Map getMap()
    {
      if( _values.get() == null )
      {
        _values.set( new HashMap() );
      }
      return (Map)_values.get();
    }

    @Override
    public void refreshedTypes(RefreshRequest request)
    {
    }

    @Override
    public void refreshed()
    {
    }
  }

  @Override
  public Object[] convertToExternalIfNecessary( Object[] args, Class[] argTypes, Class methodOwner )
  {
    return args;
  }

  @Override
  public ExtendedTypeDataFactory getExtendedTypeDataFactory(String typeName) {
    return null;
  }

  @Override
  public Object convertToInternalIfNecessary( Object obj, Class methodOwner )
  {
    return obj;
  }

  @Override
  public boolean isExternal( Class methodOwner )
  {
    return false;
  }

  @Override
  public StringBuilder getPluginRepositories()
  {
    return new StringBuilder();
  }

  @Override
  public String getWebServerPaths()
  {
    return "";
  }

  @Override
  public IType getKeyType()
  {
    return null;
  }

  @Override
  public IPropertyInfo getEntityIdProperty( IType rootType )
  {
    return null;
  }

  @Override
  public ILanguageLevel getLanguageLevel()
  {
    return new StandardLanguageLevel();
  }

  @Override
  public List<IGosuClassLoadingObserver> getGosuClassLoadingObservers() {
    return _classLoadingObservers;
  }

  @Override
  public List<IDirectory> getAdditionalSourceRoots() {
    return Collections.EMPTY_LIST;
  }

  @Override
  public void reloadedTypes(String[] types) {
    //nothing to do
  }

  @Override
  public String getLocalizedTypeName(IType type) {
    return type.getName();
  }

  @Override
  public String getLocalizedTypeInfoName(IType type) {
    String result;
    if (type instanceof IJavaType) {
      result = ((IJavaType) type).getBackingClassInfo().getDisplayName();
    } else {
      result = getLocalizedTypeName(type);
    }
    return result;
  }

  /**
   * Returns a coercer from values of rhsType to values of lhsType if one exists.
   * I tried to write a reasonable spec in the comments below that indicate exactly
   * what should coerce to what.
   *
   * @param lhsType the type to coerce to
   * @param rhsType the type to coerce from
   * @param runtime true if the coercion is happening at runtime rather than compile time
   *                (note: This param should go away as we store the coercions on the parsed elements, rather than calling into the
   *                coercion manager)
   *
   * @return a coercer from the lhsType to the rhsType, or null if no such coercer exists or is needed
   */
  public ICoercer getCoercerInternal( IType lhsType, IType rhsType, boolean runtime )
  {
    //=============================================================================
    //  Anything can be coerced to a string
    //=============================================================================
    if( JavaTypes.STRING() == lhsType && !(rhsType instanceof IErrorType) )
    {
      if( JavaTypes.pCHAR().equals( rhsType ) || JavaTypes.CHARACTER().equals( rhsType ) )
      {
        return NonWarningStringCoercer.instance();
      }
      else
      {
        return StringCoercer.instance();
      }
    }

    //=============================================================================
    //  All primitives and boxed types inter-coerce, except null to true primitives
    //=============================================================================
    if( lhsType.isPrimitive() && rhsType.equals( JavaTypes.pVOID() ) )
    {
      return null;
    }
    if( CoercionUtil.isPrimitiveOrBoxed(lhsType) && CoercionUtil.isPrimitiveOrBoxed(rhsType) )
    {
      if( TypeSystem.isBoxedTypeFor(lhsType, rhsType) ||
              TypeSystem.isBoxedTypeFor( rhsType, lhsType ) )
      {
        return getHighPriorityPrimitiveOrBoxedConverter( lhsType );
      }
      return getPrimitiveOrBoxedConverter( lhsType );
    }

    //=============================================================================
    //  Primitives coerce to things their boxed type is assignable to
    //=============================================================================
    if( rhsType.isPrimitive() )
    {
      if( lhsType.isAssignableFrom( TypeSystem.getBoxType( rhsType ) ) )
      {
        return getPrimitiveOrBoxedConverter( rhsType );
      }
    }

    //=============================================================================
    //  IMonitorLock supports java-style synchronization
    //=============================================================================
    if( !rhsType.isPrimitive() && GosuTypes.IMONITORLOCK_NAME.equals(lhsType.getName()) )
    {
      return IMonitorLockCoercer.instance();
    }

    //=============================================================================
    // Class<T> <- Meta<T' instanceof JavaType>
    //=============================================================================
    if( (JavaTypes.CLASS().equals( lhsType.getGenericType() ) &&
            (rhsType instanceof IMetaType &&
                    (((IMetaType)rhsType).getType() instanceof IHasJavaClass ||
                            ((IMetaType)rhsType).getType() instanceof IMetaType && ((IMetaType)((IMetaType)rhsType).getType()).getType() instanceof IHasJavaClass)))  )
    {
      if( !lhsType.isParameterizedType() ||
              lhsType.getTypeParameters()[0].isAssignableFrom( ((IMetaType)rhsType).getType() ) ||
              CoercionUtil.isStructurallyAssignable(lhsType.getTypeParameters()[0], rhsType) ||
              CoercionUtil.isStructurallyAssignable(lhsType.getTypeParameters()[0], ((IMetaType) rhsType).getType()) ||
              (((IMetaType)rhsType).getType().isPrimitive() && CoercionUtil.canCoerce(lhsType.getTypeParameters()[0], ((IMetaType) rhsType).getType())) )
      {
        return MetaTypeToClassCoercer.instance();
      }
    }

    //=============================================================================
    // Numeric type unification
    //=============================================================================
    if( TypeSystem.isNumericType( lhsType ) && TypeSystem.isNumericType( rhsType ) )
    {
      //=============================================================================
      // All numeric values can be down-coerced to the primitives and boxed types
      //=============================================================================
      if( lhsType.isPrimitive() || CoercionUtil.isBoxed(lhsType) )
      {
        return getPrimitiveOrBoxedConverter( lhsType );
      }

      //=============================================================================
      // All numeric values can be coerced to BigDecimal
      //=============================================================================
      if( lhsType.equals( JavaTypes.BIG_DECIMAL() ))
      {
        return BigDecimalCoercer.instance();
      }

      //=============================================================================
      // All numeric values can be coerced to BigInteger
      //=============================================================================
      if( lhsType.equals( JavaTypes.BIG_INTEGER() ))
      {
        if( CoercionUtil.hasPotentialLossOfPrecisionOrScale(lhsType, rhsType) )
        {
          return BigIntegerCoercer.instance();
        }
        else
        {
          return BigIntegerCoercer.instance();
        }
      }
    }

    //=============================================================================
    // JavaType interface <- compatible block
    //=============================================================================
    if( rhsType instanceof IFunctionType && lhsType.isInterface() )
    {
      IFunctionType rhsFunctionType = (IFunctionType)rhsType;
      IFunctionType lhsFunctionType = FunctionToInterfaceCoercer.getRepresentativeFunctionType(lhsType);
      if( lhsFunctionType != null )
      {
        if( lhsFunctionType.isAssignableFrom( rhsFunctionType ) )
        {
          return FunctionToInterfaceCoercer.instance();
        }
        else
        {
          if( lhsFunctionType.areParamsCompatible( rhsFunctionType ) )
          {
            ICoercer coercer = CoercionUtil.findCoercer(lhsFunctionType.getReturnType(), rhsFunctionType.getReturnType(), runtime);
            if( coercer != null )
            {
              return FunctionToInterfaceCoercer.instance();
            }
          }
        }
      }
    }

    //=============================================================================
    // JavaType interface <- block class
    //=============================================================================
    if( rhsType instanceof IBlockClass && lhsType.isInterface() )
    {
      // Note this condition is only ever called at runtime e.g., block cast to Object cast to Interface
      return FunctionToInterfaceCoercer.instance();
    }

    //=============================================================================
    // block <- compatible block
    //=============================================================================
    if (lhsType instanceof IFunctionType &&
        TypeSystem.get(FeatureReference.class).getParameterizedType(TypeSystem.get(Object.class), lhsType).isAssignableFrom(rhsType)) {
      return FeatureReferenceToBlockCoercer.instance();
    }

    //=============================================================================
    // Coerce synthetic block classes to function types
    //=============================================================================
    if( lhsType instanceof IFunctionType && rhsType instanceof IBlockClass)
    {
      if( lhsType.isAssignableFrom( ((IBlockClass)rhsType).getBlockType() ) )
      {
        return IdentityCoercer.instance();
      }
    }

    //=============================================================================
    // compatible block <- JavaType interface
    //=============================================================================
    if( lhsType instanceof IFunctionType && rhsType.isInterface() &&
            FunctionFromInterfaceCoercer.areTypesCompatible((IFunctionType) lhsType, rhsType) )
    {
      return FunctionFromInterfaceCoercer.instance();
    }

    //=============================================================================
    // Coerce block types that are coercable in return values and contravariant in arg types
    //=============================================================================
    if( lhsType instanceof IBlockType && rhsType instanceof IBlockType )
    {
      IBlockType lBlock = (IBlockType)lhsType;
      IBlockType rBlock = (IBlockType)rhsType;
      if( lBlock.areParamsCompatible( rBlock ) )
      {
        IType leftType = lBlock.getReturnType();
        IType rightType = rBlock.getReturnType();
        if( rightType != JavaTypes.pVOID() )
        {
          ICoercer iCoercer = CoercionUtil.findCoercer(leftType, rightType, runtime);
          if( iCoercer != null && !CoercionUtil.coercionRequiresWarningIfImplicit(leftType, rightType))
          {
            return BlockCoercer.instance();
          }
        }
      }
    }

    return null;
  }

  protected ICoercer getPrimitiveOrBoxedConverter( IType type )
  {
    if( type == JavaTypes.pBOOLEAN() )
    {
      return BasePrimitiveCoercer.BooleanPCoercer.get();
    }
    else if( type == JavaTypes.BOOLEAN() )
    {
      return BooleanCoercer.instance();
    }
    else if( type == JavaTypes.pBYTE() )
    {
      return BasePrimitiveCoercer.BytePCoercer.get();
    }
    else if( type == JavaTypes.BYTE() )
    {
      return ByteCoercer.instance();
    }
    else if( type == JavaTypes.pCHAR() )
    {
      return BasePrimitiveCoercer.CharPCoercer.get();
    }
    else if( type == JavaTypes.CHARACTER() )
    {
      return CharCoercer.instance();
    }
    else if( type == JavaTypes.pDOUBLE() )
    {
      return BasePrimitiveCoercer.DoublePCoercer.get();
    }
    else if( type == JavaTypes.DOUBLE() )
    {
      return DoubleCoercer.instance();
    }
    else if( type == JavaTypes.pFLOAT() )
    {
      return BasePrimitiveCoercer.FloatPCoercer.get();
    }
    else if( type == JavaTypes.FLOAT() )
    {
      return FloatCoercer.instance();
    }
    else if( type == JavaTypes.pINT() )
    {
      return BasePrimitiveCoercer.IntPCoercer.get();
    }
    else if( type == JavaTypes.INTEGER() )
    {
      return IntCoercer.instance();
    }
    else if( type == JavaTypes.pLONG() )
    {
      return BasePrimitiveCoercer.LongPCoercer.get();
    }
    else if( type == JavaTypes.LONG() )
    {
      return LongCoercer.instance();
    }
    else if( type == JavaTypes.pSHORT() )
    {
      return BasePrimitiveCoercer.ShortPCoercer.get();
    }
    else if( type == JavaTypes.SHORT() )
    {
      return ShortCoercer.instance();
    }
    else if( type == JavaTypes.pVOID() )
    {
      return IdentityCoercer.instance();
    }
    else
    {
      return null;
    }
  }

  protected ICoercer getHighPriorityPrimitiveOrBoxedConverter( IType type )
  {
    if( type == JavaTypes.pBOOLEAN() )
    {
      return BooleanPHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.BOOLEAN() )
    {
      return BooleanHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pBYTE() )
    {
      return BytePHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.BYTE() )
    {
      return ByteHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pCHAR() )
    {
      return CharPHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.CHARACTER() )
    {
      return CharHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pDOUBLE() )
    {
      return DoublePHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.DOUBLE() )
    {
      return DoubleHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pFLOAT() )
    {
      return FloatPHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.FLOAT() )
    {
      return FloatHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pINT() )
    {
      return IntPHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.INTEGER() )
    {
      return IntHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pLONG() )
    {
      return LongPHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.LONG() )
    {
      return LongHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pSHORT() )
    {
      return ShortPHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.SHORT() )
    {
      return ShortHighPriorityCoercer.instance();
    }
    else if( type == JavaTypes.pVOID() )
    {
      return IdentityCoercer.instance();
    }
    else
    {
      return null;
    }
  }

  public String makeStringFrom( Object obj )
  {
    return obj == null ? null : obj.toString();
  }

  @Override
  public boolean isDateTime( String str ) throws java.text.ParseException
  {
    return parseDateTime( str ) != null;
  }

  /**
   * Produce a date from a string using standard DateFormat parsing.
   */
  public Date parseDateTime( String str ) throws java.text.ParseException
  {
    if( str == null )
    {
      return null;
    }

    return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).parse(str);
  }

  /**
   * Convert a string to an array of specified type.
   * @param strValue the string to convert
   * @param intrType the array component type
   * @return the string converted to an array
   */
  public static Object makeArrayFromString( String strValue, IType intrType )
  {
    if( JavaTypes.pCHAR() == intrType )
    {
      return strValue.toCharArray();
    }

    if( JavaTypes.CHARACTER() == intrType )
    {
      Character[] characters = new Character[strValue.length()];
      for( int i = 0; i < characters.length; i++ )
      {
        characters[i] = strValue.charAt(i);
      }

      return characters;
    }

    if( JavaTypes.STRING() == intrType )
    {
      String[] strings = new String[strValue.length()];
      for( int i = 0; i < strings.length; i++ )
      {
        strings[i] = String.valueOf( strValue.charAt( i ) );
      }

      return strings;
    }

    throw GosuShop.createEvaluationException("The type, " + intrType.getName() + ", is not supported as a coercible component type to a String array.");
  }

  public Number parseNumber( String strValue )
  {
    try
    {
      return Double.parseDouble( strValue );
    }
    catch( Exception e )
    {
      // Nonparsable floating point numbers have a NaN value (a la JavaScript)
      return IGosuParser.NaN;
    }
  }
}
