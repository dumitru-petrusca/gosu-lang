package gw.specContrib.classes.property_Declarations

class Errant_SameNameDifferentCase {

  //IDE-1857 - Parser issue. Parser shows error. OS Gosu does not.
  property get myProperty() : String {return null}
  property get MyProperty() : String {return null}

  //The following test is for when both function and property with same case but small case
  //IDE-1814 - Implementation difference. OS Gosu does not show error since the errors are shown at functions below.
  property get smallCaseProperty2() : String { return null }
  property set smallCaseProperty2(s: String) {}

  //IDE-1814 - Should be an error here
  function getsmallCaseProperty2() : String { return null }      //## issuekeys: THE METHOD 'GETSMALLCASEPROPERTY2()' CONFLICTS WITH THE IMPLICIT METHOD GENERATED BY THE PROPERTY 'SMALLCASEPROPERTY2'
  function setsmallCaseProperty2(s : String){}      //## issuekeys: THE METHOD 'SETSMALLCASEPROPERTY2(STRING)' CONFLICTS WITH THE IMPLICIT METHOD GENERATED BY THE PROPERTY 'SMALLCASEPROPERTY2'

}