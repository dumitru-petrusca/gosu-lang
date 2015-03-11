package gw.specContrib.classes.property_Declarations.sameErasureCases

uses java.lang.Integer
uses java.util.ArrayList

class Errant_SameErasureFunctionsClass_2 {

  //Functions vs functions in Enhancement
  function foo1(a: ArrayList){}
  function foo2(a: ArrayList<Object>){}
  function foo3(a: ArrayList<Integer>){}
  function foo4(a: ArrayList<Integer>){}
  //type param in subclass is subtype of the type param here in superclass
  function foo5(a: ArrayList<java.lang.Number>){}
  function foo6(a: ArrayList<Integer>){}

  //Properties vs properties in Enhancement
  property set Property1(a : ArrayList) {}
  property set Property2(a : ArrayList<Object>) {}
  property set Property3(a : ArrayList<Integer>) {}
  property set Property4(a : ArrayList<Integer>) {}
  property set Property5(a : ArrayList<java.lang.Number>) {}
  property set Property6(a : ArrayList<Integer>) {}

  //Properties vs functions in Enhancement
  property set Property21(a : ArrayList) {}
  property set Property22(a : ArrayList<Object>) {}
  property set Property23(a : ArrayList<Integer>) {}
  property set Property24(a : ArrayList<Integer>) {}
  property set Property25(a : ArrayList<java.lang.Number>) {}
  property set Property26(a : ArrayList<Integer>) {}

  //Functions vs properties in Enhancement
  function setFoo21(a: ArrayList){}
  function setFoo22(a: ArrayList<Object>){}
  function setFoo23(a: ArrayList<Integer>){}
  function setFoo24(a: ArrayList<Integer>){}
  function setFoo25(a: ArrayList<java.lang.Number>){}
  function setFoo26(a: ArrayList<Integer>){}

  //As properties vs As properties in Enhancement
//Enhancements cannot have fields.

  //As properties vs properties in subclass
  var _property41 : ArrayList as  Property41
  var _property42 : ArrayList<Object> as  Property42
  var _property43 : ArrayList<Integer> as  Property43
  var _property44 : ArrayList<Integer> as  Property44
  var _property45 : ArrayList<java.lang.Number> as  Property45
  var _property46 : ArrayList<Integer> as  Property46

  //As properties vs functions in Enhancement
  var _property51 : ArrayList as  Property51
  var _property52 : ArrayList<Object> as  Property52
  var _property53 : ArrayList<Integer> as  Property53
  var _property54 : ArrayList<Integer> as  Property54
  var _property55 : ArrayList<java.lang.Number> as  Property55
  var _property56 : ArrayList<Integer> as  Property56

  //properties vs As properties in Enhancement
//Enhancements cannot have fields.


  //functions vs As properties in Enhancement
  //Enhancements cannot have fields.

}