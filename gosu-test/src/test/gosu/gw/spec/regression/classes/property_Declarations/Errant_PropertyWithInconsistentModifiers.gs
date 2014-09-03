package gw.spec.regression.classes.property_Declarations

uses java.lang.Integer

class Errant_PropertyWithInconsistentModifiers {

  var _name: String as Name   //## issuekeys: MSG_PROPERTIES_MUST_AGREE_ON_STATIC_MODIFIERS

  property get Name(): String {      //## issuekeys: MSG_PROPERTIES_MUST_AGREE_ON_STATIC_MODIFIERS
    return "hello"
  }

  static property set Name(name: String) {        //## issuekeys: MSG_PROPERTIES_MUST_AGREE_ON_STATIC_MODIFIERS
  }

}
