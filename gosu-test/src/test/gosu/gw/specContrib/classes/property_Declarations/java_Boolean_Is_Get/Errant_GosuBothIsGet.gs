package gw.specContrib.classes.property_Declarations.java_Boolean_Is_Get

/**
 * Same class has both 'property get MyProp()' and 'function isMyProp()'
 */
class Errant_GosuBothIsGet {
  //IDE-1889 - OS Gosu does not show error. Parser does. Waiting for decision
  property get BothGetIs111(): boolean {
    return false
  }

  //IDE-1889 - OS Gosu does not show error. Parser does. Waiting for decision
  function isBothGetIs111(): boolean {
    return false
  }

}