/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.reflect;

import gw.config.IService;
import gw.fs.IDirectory;
import gw.internal.gosu.parser.ExtendedTypeDataFactory;
import gw.lang.UnstableAPI;
import gw.lang.parser.GlobalScope;
import gw.lang.parser.IAttributeSource;
import gw.lang.parser.ICoercer;
import gw.lang.parser.ILanguageLevel;
import gw.lang.parser.expressions.IQueryExpression;
import gw.lang.parser.expressions.IQueryExpressionEvaluator;
import gw.util.IFeatureFilter;
import gw.util.ILogger;

import java.util.Date;
import java.util.List;


@UnstableAPI
public interface IEntityAccess extends IService {

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
   * @return a coercer from the lhsType to the rhsType, or null if no such coercer exists or is needed
   */
  ICoercer getCoercerInternal(IType lhsType, IType rhsType, boolean runtime);

  String getLocalizedTypeName(IType type);

  String getLocalizedTypeInfoName(IType type);

  boolean isExternal(Class methodOwner);

  StringBuilder getPluginRepositories();

  String getWebServerPaths();

  List<IDirectory> getAdditionalSourceRoots();

  ClassLoader getPluginClassLoader();

  IType getPrimaryEntityClass(IType type);

  /**
   * Produce a date from a string using standard DateFormat parsing.
   */
  Date parseDateTime(String str) throws java.text.ParseException;

  boolean isDateTime(String str) throws java.text.ParseException;

  Number parseNumber(String strValue);

  String makeStringFrom(Object obj);

  IFeatureFilter getQueryExpressionFeatureFilter();

  boolean isViewEntityClass(IType type);

  boolean isDomainInstance(Object value);

  boolean isEntityClass(IType type);

  boolean verifyValueForType(IType type, Object value);

  ILogger getLogger();

  List<IGosuClassLoadingObserver> getGosuClassLoadingObservers();

  IPropertyInfo getEntityIdProperty(IType rootType);

  IType getKeyType();

  ILanguageLevel getLanguageLevel();


  // Runtime

  boolean areBeansEqual(Object bean1, Object bean2);

  IAttributeSource getAttributeSource(GlobalScope scope);

  IQueryExpressionEvaluator getQueryExpressionEvaluator(IQueryExpression queryExpression);

  Object getEntityInstanceFrom(Object entity, IType classDomain);

  long getHashedEntityId(String strId, IType classEntity);

  Date getCurrentTime();

  Object[] convertToExternalIfNecessary(Object[] args, Class[] argTypes, Class methodOwner);

  ExtendedTypeDataFactory getExtendedTypeDataFactory(String typeName);

  Object convertToInternalIfNecessary(Object obj, Class methodOwner);

  void reloadedTypes(String[] types);

}
