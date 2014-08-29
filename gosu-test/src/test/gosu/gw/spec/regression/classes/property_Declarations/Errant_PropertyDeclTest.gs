package gw.spec.regression.classes.property_Declarations

uses java.lang.Integer

class Errant_PropertyDeclTest {

  property get Prop1() :  String {   //## issuekeys: MSG_PROPERTIES_MUST_AGREE_ON_TYPE
    return null
  }

  property set Prop1(int : Integer)  {  //## issuekeys: MSG_PROPERTIES_MUST_AGREE_ON_TYPE
  }

}
