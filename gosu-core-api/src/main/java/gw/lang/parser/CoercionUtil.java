/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.parser;

import gw.config.CommonServices;
import gw.lang.GosuShop;
import gw.lang.IDimension;
import gw.lang.parser.coercers.IdentityCoercer;
import gw.lang.parser.coercers.IntCoercer;
import gw.lang.parser.coercers.IntHighPriorityCoercer;
import gw.lang.parser.coercers.IntPHighPriorityCoercer;
import gw.lang.parser.coercers.LongCoercer;
import gw.lang.parser.coercers.LongHighPriorityCoercer;
import gw.lang.parser.coercers.LongPHighPriorityCoercer;
import gw.lang.parser.coercers.MetaTypeToClassCoercer;
import gw.lang.parser.coercers.NonWarningStringCoercer;
import gw.lang.parser.coercers.RuntimeCoercer;
import gw.lang.parser.coercers.StandardCoercer;
import gw.lang.parser.coercers.TypeVariableCoercer;
import gw.lang.parser.coercers.*;
import gw.lang.parser.exceptions.ParseException;
import gw.lang.parser.resources.Res;
import gw.lang.reflect.*;
import gw.lang.reflect.*;
import gw.lang.reflect.features.FeatureReference;
import gw.lang.reflect.gs.IGosuArrayClass;
import gw.lang.reflect.gs.IGosuArrayClassInstance;
import gw.lang.reflect.gs.IGosuClass;
import gw.lang.reflect.gs.IGosuEnhancement;
import gw.lang.reflect.gs.IGosuObject;
import gw.lang.reflect.java.IJavaArrayType;
import gw.lang.reflect.java.IJavaClassInfo;
import gw.lang.reflect.java.IJavaType;
import gw.lang.reflect.java.JavaTypes;
import gw.util.GosuExceptionUtil;
import gw.util.Pair;
import gw.util.concurrent.Cache;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dpetrusca
 */
public class CoercionUtil {
  private static final DecimalFormat BIG_DECIMAL_FORMAT = new DecimalFormat();
  static {
    BIG_DECIMAL_FORMAT.setParseBigDecimal( true );
  }
  public static final Object NO_DICE = new Object();

  // LRUish cache of coercers
  public static final TypeSystemAwareCache<Pair<IType, IType>, ICoercer> _coercerCache =
          TypeSystemAwareCache.make( "Coercer Cache", 1000,
                  new Cache.MissHandler<Pair<IType, IType>, ICoercer>()
                  {
                    public final ICoercer load( Pair<IType, IType> key )
                    {
                      ICoercer coercer = findCoercerImpl( key.getFirst(), key.getSecond(), false );
                      if( coercer == null )
                      {
                        return NullSentinalCoercer.instance();
                      }
                      else
                      {
                        return coercer;
                      }
                    }
                  } )/*.logEveryNSeconds( 10, new SystemOutLogger( SystemOutLogger.LoggingLevel.INFO ) )*/;

  private static Object coerce( IType intrType, IType runtimeType, Object value )
  {
    ICoercer coercer = findCoercer(intrType, runtimeType, true);
    if( coercer != null )
    {
      return coercer.coerceValue( intrType, value );
    }
    return null;
  }

  public static boolean hasPotentialLossOfPrecisionOrScale( IType lhsType, IType rhsType )
  {
    if( lhsType == JavaTypes.pBYTE() || lhsType == JavaTypes.BYTE() )
    {
      return rhsType != JavaTypes.pBYTE() && rhsType != JavaTypes.BYTE();
    }
    else if( lhsType == JavaTypes.pCHAR() || lhsType == JavaTypes.CHARACTER() )
    {
      return rhsType != JavaTypes.pCHAR() && rhsType != JavaTypes.CHARACTER();
    }
    else if( lhsType == JavaTypes.pDOUBLE() || lhsType == JavaTypes.DOUBLE() )
    {
      return rhsType != JavaTypes.DOUBLE() && !rhsType.isPrimitive() &&
          (JavaTypes.BIG_DECIMAL().isAssignableFrom( rhsType ) || JavaTypes.BIG_INTEGER().isAssignableFrom( rhsType ));
    }
    else if( lhsType == JavaTypes.pFLOAT() || lhsType == JavaTypes.FLOAT() )
    {
      return rhsType == JavaTypes.pDOUBLE() || rhsType == JavaTypes.DOUBLE() ||
          JavaTypes.BIG_DECIMAL().isAssignableFrom( rhsType ) || JavaTypes.BIG_INTEGER().isAssignableFrom( rhsType );
    }
    else if( lhsType == JavaTypes.pINT() || lhsType == JavaTypes.INTEGER() )
    {
      return rhsType != JavaTypes.pINT() && rhsType != JavaTypes.INTEGER() &&
          rhsType != JavaTypes.pSHORT() && rhsType != JavaTypes.SHORT() &&
          rhsType != JavaTypes.pBYTE() && rhsType != JavaTypes.BYTE() &&
          rhsType != JavaTypes.pCHAR() && rhsType != JavaTypes.CHARACTER();
    }
    else if( lhsType == JavaTypes.pLONG() || lhsType == JavaTypes.LONG() )
    {
      return rhsType != JavaTypes.pLONG() && rhsType != JavaTypes.LONG() &&
          rhsType != JavaTypes.pINT() && rhsType != JavaTypes.INTEGER() &&
          rhsType != JavaTypes.pSHORT() && rhsType != JavaTypes.SHORT() &&
          rhsType != JavaTypes.pBYTE() && rhsType != JavaTypes.BYTE() &&
          rhsType != JavaTypes.pCHAR() && rhsType != JavaTypes.CHARACTER();
    }
    else if( lhsType == JavaTypes.pSHORT() || lhsType == JavaTypes.SHORT() )
    {
      return rhsType != JavaTypes.pSHORT() && rhsType != JavaTypes.SHORT() &&
          rhsType != JavaTypes.pBYTE() && rhsType != JavaTypes.BYTE();
    }
    else if( JavaTypes.BIG_INTEGER().isAssignableFrom( lhsType ) )
    {
      return rhsType != JavaTypes.BIG_INTEGER() && hasPotentialLossOfPrecisionOrScale( JavaTypes.LONG(), rhsType );
    }
    return false;
  }

  public static final ICoercer findCoercer( IType lhsType, IType rhsType, boolean runtime )
  {
    if( runtime )
    {
      return findCoercerImpl( lhsType, rhsType, runtime );
    }
    else
    {
      @SuppressWarnings({"unchecked"})
      ICoercer iCoercer = _coercerCache.get( new Pair( lhsType, rhsType ) );
      if( iCoercer == NullSentinalCoercer.instance() )
      {
        return null;
      }
      else
      {
        return iCoercer;
      }
    }
  }

  private static ICoercer findCoercerImpl( IType lhsType, IType rhsType, boolean runtime )
  {
    ICoercer coercer = CommonServices.getEntityAccess().getCoercerInternal(lhsType, rhsType, runtime);
    if( coercer != null )
    {
      return coercer;
    }

    //Look at interfaces on rhsType for coercions
    IType[] interfaces = rhsType.getInterfaces();
    for ( IType anInterface1 : interfaces ) {
      coercer = findCoercer(lhsType, anInterface1, runtime);
      if (coercer != null) {
        return coercer;
      }
    }

    //Recurse to the superclass of rhsType for coercions
    if( rhsType.getSupertype() == null || isPrimitiveOrBoxed( lhsType ) )
    {
      return null;
    }
    else
    {
      return findCoercer( lhsType, rhsType.getSupertype(), runtime );
    }
  }

  /**
   * @param lhsType type to coerce to
   * @param rhsType type to coerce from
   *
   * @return true if the given coercion should generate a warning
   */
  public static boolean coercionRequiresWarningIfImplicit( IType lhsType, IType rhsType )
  {
    //=============================================================================
    //  Anything can be coerced to a string
    //=============================================================================
    if( JavaTypes.STRING() == lhsType )
    {
      return false;
    }
    if( lhsType.equals( rhsType ) )
    {
      return false;
    }
    if( lhsType.isAssignableFrom( rhsType ) )
    {
      return false;
    }
    if ( rhsType.isPrimitive() && lhsType.isAssignableFrom( TypeSystem.getBoxType(rhsType) ) )
    {
      return false;
    }

    //==================================================================================
    // null/void confusion (see http://jira/jira/browse/PL-12766)
    //==================================================================================
    if( JavaTypes.pVOID().equals(rhsType) )
    {
      return false;
    }

    //==================================================================================
    // Error type handling
    //==================================================================================
    if( lhsType instanceof IErrorType)
    {
      return false;
    }
    if( rhsType instanceof IErrorType )
    {
      return false;
    }

    //==================================================================================
    // IPlaceholderType type handling
    //==================================================================================
    if( (lhsType instanceof IPlaceholder && ((IPlaceholder)lhsType).isPlaceholder()) ||
            (rhsType instanceof IPlaceholder && ((IPlaceholder)rhsType).isPlaceholder()) )
    {
      return false;
    }

    //==================================================================================
    //Covariant arrays (java semantics, which are wrong)
    //
    // (Five years later, let me totally disagree with my former self.  Java array semantics are not only right,
    //  we've decided to adopt them for generics.  Worse is better.)
    //==================================================================================
    if( lhsType.isArray() && rhsType.isArray() )
    {
      if( lhsType.getComponentType().isAssignableFrom( rhsType.getComponentType() ) )
      {
        return false;
      }
    }

    //==================================================================================
    // Coercion
    //==================================================================================
    if( TypeSystem.isNumericType( lhsType ) &&
            TypeSystem.isNumericType( rhsType ) )
    {
      return hasPotentialLossOfPrecisionOrScale( lhsType, rhsType );
    }
    else
    {
      if( TypeSystem.isBoxedTypeFor( lhsType, rhsType ) ||
              TypeSystem.isBoxedTypeFor( rhsType, lhsType ) )
      {
        return false;
      }
      else
      {
        ICoercer iCoercer = findCoercer(lhsType, rhsType, false);
        return iCoercer != null && iCoercer.isExplicitCoercion();
      }
    }
  }

  /**
   * Verifies that the right hand type can be converted or coerced to the left hand type.  If bBiDirectional is true,
   * it will verify that either converts to another
   */
  public static IType verifyTypesComparable( IType lhsType, IType rhsType, boolean bBiDirectional ) throws ParseException
  {
    IType lhsT;
    IType rhsT;
    if(bBiDirectional)
    {
      // Bi-Directional indicates comparison as opposed to assignability, therefore for comparison
      // we need to test comparability between type variables' bounds
      lhsT = TypeSystem.getDefaultParameterizedTypeWithTypeVars(lhsType);
      rhsT = TypeSystem.getDefaultParameterizedTypeWithTypeVars(rhsType);
    }
    else
    {
      lhsT = lhsType;
      rhsT = rhsType;
    }

    //==================================================================================
    // Upcasting
    //==================================================================================
    if( lhsT == rhsT )
    {
      return lhsType;
    }
    if( lhsT.equals( rhsT ) )
    {
      return lhsType;
    }
    if( lhsT.isAssignableFrom( rhsT ) )
    {
      return lhsType;
    }

    //==================================================================================
    // null/void confusion (see http://jira/jira/browse/PL-12766)
    //==================================================================================
    if( JavaTypes.pVOID().equals( rhsT ) && !lhsT.isPrimitive() )
    {
      return lhsType;
    }
    if( JavaTypes.pVOID().equals( lhsT ) && !rhsT.isPrimitive() )
    {
      return rhsType;
    }

    //==================================================================================
    // Error type handling
    //==================================================================================
    if( lhsT instanceof IErrorType)
    {
      return lhsType;
    }
    if( rhsT instanceof IErrorType )
    {
      return rhsType;
    }

    //==================================================================================
    // IPlaceholderType type handling
    //==================================================================================
    if( (lhsT instanceof IPlaceholder && ((IPlaceholder)lhsT).isPlaceholder()) ||
            (rhsT instanceof IPlaceholder && ((IPlaceholder)rhsT).isPlaceholder()) )
    {
      return lhsType;
    }

    //==================================================================================
    //Covariant arrays
    //==================================================================================
    if( lhsT.isArray() && rhsT.isArray() )
    {
      // Note an array of primitives and an array of non-primitives are never assignable
      if( lhsT.getComponentType().isPrimitive() == rhsT.getComponentType().isPrimitive() &&
              lhsT.getComponentType().isAssignableFrom( rhsT.getComponentType() ) )
      {
        return lhsType;
      }
    }

    //==================================================================================
    // Downcasting
    //==================================================================================
    if(bBiDirectional)
    {
      if( rhsT.isAssignableFrom( lhsT ) )
      {
        return lhsType;
      }
      if( lhsT.isArray() && rhsT.isArray() )
      {
        if( rhsT.getComponentType().isAssignableFrom( lhsT.getComponentType() ) )
        {
          return lhsType;
        }
      }
    }

    //==================================================================================
    // Structurally suitable (static duck typing)
    //==================================================================================
    if( isStructurallyAssignable( lhsT, rhsT ) )
    {
      return lhsType;
    }

    //==================================================================================
    // Coercion
    //==================================================================================
    if( canCoerce( lhsT, rhsT ) )
    {
      return lhsType;
    }

    if(bBiDirectional)
    {
      if( canCoerce( rhsT, lhsT ) )
      {
        return rhsType;
      }
    }

    String strLhs = TypeSystem.getNameWithQualifiedTypeVariables(lhsType);
    String strRhs = TypeSystem.getNameWithQualifiedTypeVariables(rhsType);
    throw new ParseException(null, lhsType, Res.MSG_TYPE_MISMATCH, strLhs, strRhs );
  }

  public static boolean isStructurallyAssignable( IType toType, IType fromType )
  {
    if( !(toType instanceof IGosuClass && ((IGosuClass)toType).isStructure()) )
    {
      return false;
    }
    return isStructurallyAssignable_Laxed( toType, fromType );
  }
  public static boolean isStructurallyAssignable_Laxed( IType toType, IType fromType )
  {
//    if( fromType instanceof IMetaType && ((IMetaType)fromType).getType() instanceof IGosuClass && ((IGosuClass)((IMetaType)fromType).getType()).isStructure() )
//    {
//      // So that:
//      // foo( MyStaticCharAtType )
//      // function foo( t: Type<CharAt> ) {
//      //   var c = (t as CharAt).charAt( 0 )
//      //   ...
//      // }
//      //
//      fromType = ((IMetaType)fromType).getType();
//    }
//    else if( JavaTypes.CLASS().isAssignableFrom( fromType ) && fromType.getTypeParameters()[0] instanceof IGosuClass && ((IGosuClass)((IMetaType)fromType).getType()).isStructure() )
//    {
//      // So that:
//      // foo( MyStaticCharAtType )
//      // function foo( t: Class<CharAt> ) {
//      //   var c = (t as CharAt).charAt( 0 )
//      //   ...
//      // }
//      //
//      fromType = fromType.getTypeParameters()[0];
//    }

    ITypeInfo fromTypeInfo = fromType.getTypeInfo();
    MethodList fromMethods = fromTypeInfo instanceof IRelativeTypeInfo
            ? ((IRelativeTypeInfo)fromTypeInfo).getMethods( toType )
            : fromTypeInfo.getMethods();
    ITypeInfo toTypeInfo = toType.getTypeInfo();
    MethodList toMethods = toTypeInfo instanceof IRelativeTypeInfo
            ? ((IRelativeTypeInfo)toTypeInfo).getMethods( fromType )
            : toTypeInfo.getMethods();
    for( IMethodInfo toMi : toMethods )
    {
      if( isObjectMethod( toMi ) ) {
        continue;
      }
      if( toMi.getOwnersType() instanceof IGosuEnhancement) {
        continue;
      }
      IMethodInfo fromMi = fromMethods.findAssignableMethod( toMi, fromType instanceof IMetaType && (!(((IMetaType)fromType).getType() instanceof IGosuClass) || !((IGosuClass)((IMetaType)fromType).getType()).isStructure()) );
      if( fromMi == null ) {
        if( toMi.getDisplayName().startsWith( "@" ) ) {
          // Find matching property/field
          IPropertyInfo fromPi = fromTypeInfo.getProperty( toMi.getDisplayName().substring( 1 ) );
          if( fromPi != null ) {
            if( toMi.getParameters().length == 0 ) {
              boolean bAssignable = toMi.getReturnType().equals( fromPi.getFeatureType() ) ||
                                    arePrimitiveTypesAssignable( toMi.getReturnType(), fromPi.getFeatureType() );
              if( bAssignable ) {
                continue;
              }
            }
            else {
              boolean bAssignable = fromPi.isWritable( toType ) &&
                                    (fromPi.getFeatureType().equals( toMi.getParameters()[0].getFeatureType() ) ||
                                    arePrimitiveTypesAssignable( fromPi.getFeatureType(), toMi.getParameters()[0].getFeatureType() ));
              if( bAssignable ) {
                continue;
              }
            }
          }
        }
        return false;
      }
    }
    return true;
  }

  public static boolean canCoerce( IType lhsType, IType rhsType )
  {
    return findCoercer(lhsType, rhsType, false) != null;
  }

//  public static boolean arePrimitiveTypesAssignable( IType toType, IType fromType ) {
//    if( toType == null || fromType == null || !toType.isPrimitive() || !fromType.isPrimitive() ) {
//      return false;
//    }
//
//    if( toType == JavaTypes.pDOUBLE() ) {
//      return fromType == JavaTypes.pFLOAT() ||
//             fromType == JavaTypes.pLONG() ||
//             fromType == JavaTypes.pINT() ||
//             fromType == JavaTypes.pCHAR() ||
//             fromType == JavaTypes.pSHORT() ||
//             fromType == JavaTypes.pBYTE();
//    }
//    if( toType == JavaTypes.pFLOAT() ) {
//      return fromType == JavaTypes.pINT() ||
//             fromType == JavaTypes.pCHAR() ||
//             fromType == JavaTypes.pSHORT() ||
//             fromType == JavaTypes.pBYTE();
//    }
//    if( toType == JavaTypes.pLONG() ) {
//      return fromType == JavaTypes.pINT() ||
//             fromType == JavaTypes.pCHAR() ||
//             fromType == JavaTypes.pSHORT() ||
//             fromType == JavaTypes.pBYTE();
//    }
//    if( toType == JavaTypes.pINT() ) {
//      return fromType == JavaTypes.pSHORT() ||
//             fromType == JavaTypes.pCHAR() ||
//             fromType == JavaTypes.pBYTE();
//    }
//    if( toType == JavaTypes.pCHAR() ) {
//      return fromType == JavaTypes.pSHORT() ||
//             fromType == JavaTypes.pCHAR() ||
//             fromType == JavaTypes.pBYTE();
//    }
//    if( toType == JavaTypes.pSHORT() ) {
//      return fromType == JavaTypes.pBYTE();
//    }
//
//    return false;
//  }

  public static boolean arePrimitiveTypesAssignable( IType toType, IType fromType ) {
    if( toType == null || fromType == null || !toType.isPrimitive() || !fromType.isPrimitive() ) {
      return false;
    }

    if( toType == JavaTypes.pDOUBLE() ) {
      return fromType == JavaTypes.pFLOAT() ||
          fromType == JavaTypes.pLONG() ||
          fromType == JavaTypes.pINT() ||
          fromType == JavaTypes.pCHAR() ||
          fromType == JavaTypes.pSHORT() ||
          fromType == JavaTypes.pBYTE();
    }
    if( toType == JavaTypes.pFLOAT() ) {
      return fromType == JavaTypes.pINT() ||
          fromType == JavaTypes.pCHAR() ||
          fromType == JavaTypes.pSHORT() ||
          fromType == JavaTypes.pBYTE();
    }
    if( toType == JavaTypes.pLONG() ) {
      return fromType == JavaTypes.pINT() ||
          fromType == JavaTypes.pCHAR() ||
          fromType == JavaTypes.pSHORT() ||
          fromType == JavaTypes.pBYTE();
    }
    if( toType == JavaTypes.pINT() ) {
      return fromType == JavaTypes.pSHORT() ||
          fromType == JavaTypes.pCHAR() ||
          fromType == JavaTypes.pBYTE();
    }
    if( toType == JavaTypes.pCHAR() ) {
      return fromType == JavaTypes.pSHORT() ||
          fromType == JavaTypes.pCHAR() ||
          fromType == JavaTypes.pBYTE();
    }
    if( toType == JavaTypes.pSHORT() ) {
      return fromType == JavaTypes.pBYTE();
    }

    return false;
  }

  public static boolean isObjectMethod( IMethodInfo mi )
  {
    IGosuClass gosuObjectType = GosuShop.getGosuClassFrom(JavaTypes.IGOSU_OBJECT());
    if( mi.getOwnersType() == gosuObjectType || mi.getDisplayName().equals("@itype") )
    {
      // A IGosuObject method
      return true;
    }

    IParameterInfo[] params = mi.getParameters();
    IType[] paramTypes = new IType[params.length];
    for( int i = 0; i < params.length; i++ )
    {
      paramTypes[i] = params[i].getFeatureType();
    }
    IRelativeTypeInfo ti = (IRelativeTypeInfo)JavaTypes.OBJECT().getTypeInfo();
    IMethodInfo objMethod = ti.getMethod( JavaTypes.OBJECT(), mi.getDisplayName(), paramTypes );
    return objMethod != null;
  }

  /**
   * Determine and return a statically valid coercer from the rhsType to the lhsType.  Returns
   * null if no coercion is necessary.
   *
   * @param typeToCoerceTo the type to coerce to
   * @param typeToCoerceFrom the type to coerce from
   */
  public static ICoercer resolveCoercerStatically( IType typeToCoerceTo, IType typeToCoerceFrom )
  {
    if( typeToCoerceTo == null || typeToCoerceFrom == null )
    {
      return null;
    }
    else if( typeToCoerceTo == typeToCoerceFrom )
    {
      return null;
    }
    else if( typeToCoerceTo.equals( typeToCoerceFrom ) )
    {
      return null;
    }
    else if( typeToCoerceTo instanceof IErrorType || typeToCoerceFrom instanceof IErrorType )
    {
      return null;
    }
    else if( typeToCoerceTo instanceof ITypeVariableArrayType)
    {
      return RuntimeCoercer.instance();
    }
    else if( typeToCoerceTo instanceof ITypeVariableType)
    {
      return TypeVariableCoercer.instance();
    }
    else if( typeToCoerceTo.isAssignableFrom( typeToCoerceFrom ) )
    {
      return null;
    }
    else
    {
      ICoercer coercerInternal = findCoercerImpl( typeToCoerceTo, typeToCoerceFrom, false );
      if( coercerInternal == null )
      {
        if( typeToCoerceFrom.isAssignableFrom( typeToCoerceTo ) && !JavaTypes.pVOID().equals(typeToCoerceTo) )
        {
          if( areJavaClassesAndAreNotAssignable( typeToCoerceTo, typeToCoerceFrom ) )
          {
            return RuntimeCoercer.instance();
          }
          return identityOrRuntime( typeToCoerceTo, typeToCoerceFrom );
        }
        else if( (typeToCoerceFrom.isInterface() || typeToCoerceTo.isInterface()) &&
                !typeToCoerceFrom.isPrimitive() && !typeToCoerceTo.isPrimitive() )
        {
          return identityOrRuntime( typeToCoerceTo, typeToCoerceFrom );
        }
      }
      return coercerInternal;
    }
  }

  private static boolean areJavaClassesAndAreNotAssignable( IType typeToCoerceTo, IType typeToCoerceFrom )
  {
    if( typeToCoerceFrom instanceof IJavaType && typeToCoerceTo instanceof IJavaType )
    {
      if( !((IJavaType)typeToCoerceFrom).getBackingClassInfo().isAssignableFrom( ((IJavaType)typeToCoerceTo).getBackingClassInfo() ) )
      {
        return true;
      }
    }
    return false;
  }

  private static ICoercer identityOrRuntime( IType typeToCoerceTo, IType typeToCoerceFrom )
  {
    if( TypeSystem.isBytecodeType( typeToCoerceFrom ) &&
            TypeSystem.isBytecodeType( typeToCoerceTo ) )
    {
      return IdentityCoercer.instance(); // (perf) class-to-class downcast can use checkcast bytecode
    }
    return RuntimeCoercer.instance();
  }

  private static class NullSentinalCoercer extends StandardCoercer
  {
    private static final NullSentinalCoercer INSTANCE = new NullSentinalCoercer();
    @Override
    public Object coerceValue( IType typeToCoerceTo, Object value )
    {
      throw new IllegalStateException( "This is the null sentinal coercer, and is used only to " +
              "represent a miss in the coercer cache.  It should never " +
              "be returned for actual use" );
    }
    public static NullSentinalCoercer instance()
    {
      return INSTANCE;
    }
  }

  public static boolean isPrimitiveOrBoxed( IType lhsType )
  {
    return lhsType.isPrimitive() || isBoxed( lhsType );
  }

  public static boolean isBoxed( IType lhsType )
  {
    return lhsType == JavaTypes.BOOLEAN()
            || lhsType == JavaTypes.BYTE()
            || lhsType == JavaTypes.CHARACTER()
            || lhsType == JavaTypes.DOUBLE()
            || lhsType == JavaTypes.FLOAT()
            || lhsType == JavaTypes.INTEGER()
            || lhsType == JavaTypes.LONG()
            || lhsType == JavaTypes.SHORT();
  }

  public static Object convertNullAsPrimitive( IType intrType, boolean isForBoxing )
  {
    if( intrType == null )
    {
      return null;
    }

    if( !intrType.isPrimitive() )
    {
      throw GosuShop.createEvaluationException(intrType.getName() + " is not a primitive type.");
    }

    if( intrType == JavaTypes.pBYTE() )
    {
      return (byte) 0;
    }
    if( intrType == JavaTypes.pCHAR() )
    {
      return '\0';
    }
    if( intrType == JavaTypes.pDOUBLE() )
    {
      return isForBoxing ? IGosuParser.NaN : (double) 0;
    }
    if( intrType == JavaTypes.pFLOAT() )
    {
      return isForBoxing ? Float.NaN : (float) 0;
    }
    if( intrType == JavaTypes.pINT() )
    {
      return 0;
    }
    if( intrType == JavaTypes.pLONG() )
    {
      return (long) 0;
    }
    if( intrType == JavaTypes.pSHORT() )
    {
      return (short) 0;
    }
    if( intrType == JavaTypes.pBOOLEAN() )
    {
      return Boolean.FALSE;
    }
    if( intrType == JavaTypes.pVOID() )
    {
      return null;
    }
    throw GosuShop.createEvaluationException( "Unexpected primitive type: " + intrType.getName() );
  }

  public static BigDecimal makeBigDecimalFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof IDimension)
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof BigDecimal )
    {
      return (BigDecimal)obj;
    }

    if( obj instanceof String )
    {
      //!!! temporary hack: interpret empty String double values or dashes as zeros.
      //!!! Ben?
      String strValue = (String)obj;
      if( strValue.length() == 0 || strValue.equals( "-" ) )
      {
        return IGosuParser.BIGD_ZERO;
      }
      else // hack to support an insane currency coercion we used to support
      {    // I'm sure this will be as temporary as the hack above, put in years ago
        return new BigDecimal( CommonServices.getEntityAccess().parseNumber( strValue ).toString() );
      }
//      try
//      {
//        return (BigDecimal)BIG_DECIMAL_FORMAT.parse( obj.toString() );
//      }
//      catch( java.text.ParseException e )
//      {
//        throw GosuExceptionUtil.convertToRuntimeException(e);
//      }
    }

    if( obj instanceof Integer )
    {
      return BigDecimal.valueOf( (Integer)obj );
    }
    else if( obj instanceof BigInteger)
    {
      return new BigDecimal( (BigInteger)obj );
    }
    else if( obj instanceof Long )
    {
      return BigDecimal.valueOf( (Long)obj );
    }
    else if( obj instanceof Short )
    {
      return BigDecimal.valueOf( (Short)obj );
    }
    else if( obj instanceof Byte )
    {
      return BigDecimal.valueOf((Byte) obj);
    }
    else if( obj instanceof Character )
    {
      return BigDecimal.valueOf((Character) obj);
    }
    else if (obj instanceof Float)
    {
      // Convert a float directly to a BigDecimal via the String value; don't
      // up-convert it to a double first, since converting a double can be lossy
      return new BigDecimal( obj.toString());
    }
    else if( obj instanceof Number )
    {
      // Make a double from any type of number that we haven't yet dealt with
      Double d = makeDoubleFrom( obj );
      return new BigDecimal( d.toString() );
    }
    else if( obj instanceof Boolean )
    {
      return (Boolean) obj ? BigDecimal.ONE : BigDecimal.ZERO;
    }
    else if( obj instanceof Date)
    {
      return new BigDecimal( ((Date)obj).getTime() );
    }
    else if(CoercionUtil.findCoercer(JavaTypes.NUMBER(), TypeSystem.getFromObject(obj), false) != null)
    {
      Number num = (Number)convertValue( obj, JavaTypes.NUMBER() );
      return makeBigDecimalFrom( num );
    }
    else
    {
      // As a last resort, convert it to a double and then convert that to a big decimal
      Double d = makeDoubleFrom( obj );
      return new BigDecimal( d.toString() );
    }
  }

  public static BigInteger makeBigIntegerFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof BigInteger )
    {
      return (BigInteger)obj;
    }

    if( obj instanceof IDimension )
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof String )
    {
      String strValue = (String)obj;
      return makeBigIntegerFrom( CommonServices.getEntityAccess().parseNumber(strValue) );
    }

    BigDecimal d = makeBigDecimalFrom( obj );
    return d.toBigInteger();
  }

  public static double makePrimitiveDoubleFrom( Object obj )
  {
    if( obj == null )
    {
      return Double.NaN;
    }
    else
    {
      return makeDoubleFrom(obj);
    }
  }

  public static Float makeFloatFrom( Object obj )
  {
    if( obj == null )
    {
      return Float.NaN;
    }

    if( obj instanceof IDimension )
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof Number )
    {
      return ((Number)obj).floatValue();
    }
    else if( obj instanceof Boolean )
    {
      return (Boolean) obj ? 1f : 0f;
    }
    else if( obj instanceof Date )
    {
      return (float) ((Date) obj).getTime();
    }
    else if( obj instanceof Character )
    {
      return (float) ((Character) obj).charValue();
    }
    else
    {
      try
      {
        return Float.parseFloat( obj.toString() );
      }
      catch( Throwable t )
      {
        // Nonparsable floating point numbers have a NaN value (a la JavaScript)
        return Float.NaN;
      }
    }
  }

  public static float makePrimitiveFloatFrom( Object obj )
  {
    if( obj == null )
    {
      return Float.NaN;
    }
    else
    {
      return makeFloatFrom(obj);
    }
  }

  public static Double makeDoubleFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof IDimension)
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof Double )
    {
      return (Double)obj;
    }

    double d;

    if( obj instanceof Number )
    {
      d = ((Number)obj).doubleValue();
    }
    else if( obj instanceof Boolean )
    {
      return (Boolean) obj ? IGosuParser.ONE : IGosuParser.ZERO;
    }
    else if( obj instanceof Date )
    {
      return (double)((Date)obj).getTime();
    }
    else if( obj instanceof Character )
    {
      return (double) ((Character) obj).charValue();
    }
    else if(CoercionUtil.findCoercer(JavaTypes.NUMBER(), TypeSystem.getFromObject(obj), false) != null)
    {
      Number num = (Number)convertValue( obj, JavaTypes.NUMBER() );
      return num.doubleValue();
    }
    else
    {
      String strValue = obj.toString();
      return makeDoubleFrom( CommonServices.getEntityAccess().parseNumber( strValue ) );
    }

    if( d >= 0D && d <= 9D )
    {
      int i = (int)d;

      if( ((double)i == d) && (i >= 0 && i <= 9) )
      {
        return IGosuParser.DOUBLE_DIGITS[i];
      }
    }

    return d;
  }

  public static Integer makeIntegerFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof IDimension )
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof Integer )
    {
      return (Integer)obj;
    }

    if( obj instanceof Number )
    {
      return ( (Number) obj ).intValue();
    }
    else if( obj instanceof Boolean )
    {
      return (Boolean) obj
              ? IGosuParser.ONE.intValue()
              : IGosuParser.ZERO.intValue();
    }
    else if( obj instanceof Date )
    {
      return (int) ((Date) obj).getTime();
    }
    else if( obj instanceof Character )
    {
      return (int) ((Character) obj).charValue();
    }
    else if(CoercionUtil.findCoercer(JavaTypes.NUMBER(), TypeSystem.getFromObject(obj), false) != null)
    {
      Number num = (Number)convertValue( obj, JavaTypes.NUMBER() );
      return num.intValue();
    }
    else
    {
      String strValue = obj.toString();
      return makeIntegerFrom(CommonServices.getEntityAccess().parseNumber(strValue));
    }
  }

  public static Long makeLongFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof IDimension )
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof Long )
    {
      return (Long)obj;
    }

    if( obj instanceof Number )
    {
      return ((Number)obj).longValue();
    }
    else if( obj instanceof Boolean )
    {
      return (Boolean) obj
              ? IGosuParser.ONE.longValue()
              : IGosuParser.ZERO.longValue();
    }
    else if( obj instanceof Date )
    {
      return ((Date)obj).getTime();
    }
    else if( obj instanceof Character )
    {
      return (long)((Character)obj).charValue();
    }
    else if(CoercionUtil.findCoercer(JavaTypes.NUMBER(), TypeSystem.getFromObject(obj), false) != null)
    {
      Number num = (Number)convertValue(obj, JavaTypes.NUMBER());
      return num.longValue();
    }
    else
    {
      String strValue = obj.toString();
      return makeLongFrom( CommonServices.getEntityAccess().parseNumber(strValue) );
    }
  }

  public static Boolean makeBooleanFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof IDimension )
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof Boolean )
    {
      return (Boolean)obj;
    }

    if( obj instanceof String )
    {
      return Boolean.valueOf((String) obj);
    }

    if( obj instanceof Number )
    {
      return ((Number)obj).doubleValue() == 0D ? Boolean.FALSE : Boolean.TRUE;
    }

    return Boolean.valueOf( obj.toString() );
  }

  /**
   * @return A Boolean for an arbitrary object.
   */
  public static boolean makePrimitiveBooleanFrom( Object obj )
  {
    //noinspection SimplifiableIfStatement
    if( obj == null )
    {
      return false;
    }
    else
    {
      return Boolean.TRUE.equals( makeBooleanFrom( obj ) );
    }
  }

  /**
   * Given a value and a target Class, return a compatible value via the target Class.
   */
  public static Object convertValue(Object value, IType intrType)
  {
    //==================================================================================
    // Null handling
    //==================================================================================
    if( intrType == null )
    {
      return null;
    }

    //## todo: This is a horrible hack
    //## todo: The only known case where this is necessary is when we have an array of parameterized java type e.g., List<String>[]
    intrType = getBoundingTypeOfTypeVariable( intrType );

    if( value == null )
    {
      return intrType.isPrimitive() ? convertNullAsPrimitive(intrType, true) : null;
    }

    IType runtimeType = TypeSystem.getFromObject( value );

    //==================================================================================
    // IPlaceholder type handling
    //==================================================================================
    if( (intrType instanceof IPlaceholder && ((IPlaceholder)intrType).isPlaceholder()) ||
            (runtimeType instanceof IPlaceholder && ((IPlaceholder)runtimeType).isPlaceholder()) )
    {
      return value;
    }

    //==================================================================================
    // Runtime polymorphism (with java array semantics)
    //==================================================================================
    if( intrType == runtimeType )
    {
      return value;
    }
    if( intrType.equals( runtimeType ) )
    {
      return value;
    }
    if( intrType.isAssignableFrom( runtimeType ) )
    {
      value = extractObjectArray( intrType, value );
      return value;
    }
    if( intrType.isArray() && runtimeType.isArray() )
    {
      if( intrType.getComponentType().isAssignableFrom( runtimeType.getComponentType() ) )
      {
        value = extractObjectArray( intrType, value );
        return value;
      }
      else if( intrType instanceof IGosuArrayClass &&
              value instanceof IGosuObject[] )
      {
        return value;
      }
    }
    // Proxy coercion. The proxy class generated for Java classes is not a super type of the Gosu class.
    // The following check allows coercion of the Gosu class to the Gosu proxy class needed for the super call.
    if( intrType instanceof IJavaType &&
            IGosuClass.ProxyUtil.isProxy( intrType ) &&
            runtimeType instanceof IGosuClass &&
            intrType.getSupertype() != null &&
            intrType.getSupertype().isAssignableFrom( runtimeType ) )
    {
      return value;
    }

    // Check Java world types
    //noinspection deprecation
    if( intrType instanceof IJavaType)
    {
      IJavaClassInfo valueClassInfo = TypeSystem.getJavaClassInfo(value.getClass());
      if (valueClassInfo != null && ((IJavaType)intrType).getBackingClassInfo().isAssignableFrom(valueClassInfo)) {
        return value;
      }
    }

    //==================================================================================
    // Coercion
    //==================================================================================
    Object convertedValue = coerce(intrType, runtimeType, value);
    if( convertedValue != null )
    {
      return convertedValue;
    }
    else
    {
      //If the null arose from an actual coercion, return it
      if( canCoerce( intrType, runtimeType ) )
      {
        return convertedValue;
      }
      else
      {
        //otherwise, return the value itself uncoerced (See comment above)
        if( !runtimeType.isArray() )
        {
          return NO_DICE;
        }
        return value;
      }
    }
  }

  private static IType getBoundingTypeOfTypeVariable( IType intrType )
  {
    int i = 0;
    while( intrType instanceof ITypeVariableArrayType)
    {
      i++;
      intrType = intrType.getComponentType();
    }
    if( intrType instanceof ITypeVariableType)
    {
      intrType = ((ITypeVariableType)intrType).getBoundingType();
      while( i-- > 0 )
      {
        intrType = intrType.getArrayType();
      }
    }
    return intrType;
  }

  private static Object extractObjectArray( IType intrType, Object value )
  {
    if( intrType.isArray() &&
            intrType instanceof IJavaArrayType &&
            value instanceof IGosuArrayClassInstance)
    {
      value = ((IGosuArrayClassInstance)value).getObjectArray();
    }
    return value;
  }

  /**
   * Returns a new Date instance representing the object.
   */
  public static Date makeDateFrom( Object obj )
  {
    if( obj == null )
    {
      return null;
    }

    if( obj instanceof IDimension )
    {
      obj = ((IDimension)obj).toNumber();
    }

    if( obj instanceof Date )
    {
      return (Date)obj;
    }

    if( obj instanceof Number )
    {
      return new Date( ((Number)obj).longValue() );
    }

    if( obj instanceof Calendar)
    {
      return ((Calendar)obj).getTime();
    }

    if( !(obj instanceof String) )
    {
      obj = obj.toString();
    }

    try
    {
      return CommonServices.getEntityAccess().parseDateTime((String) obj);
    }
    catch( Exception e )
    {
      //e.printStackTrace();
    }

    return null;
  }

}
