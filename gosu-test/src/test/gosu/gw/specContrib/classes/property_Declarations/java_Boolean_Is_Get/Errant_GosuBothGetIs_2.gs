package gw.specContrib.classes.property_Declarations.java_Boolean_Is_Get

class Errant_GosuBothGetIs_2 implements JavaBothGetIs_2 {

  override property get BothGetIs111(): boolean {
    return false
  }

  //IDE-1888
  override function isBothGetIs111(): boolean {      //## issuekeys: DOES NOT OVERRIDE ANYTHING
    return false
  }
}