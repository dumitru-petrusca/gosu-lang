package gw.specContrib.classes.property_Declarations.sameErasureCases

uses java.lang.*
uses java.util.ArrayList

class Errant_SameErasureFunctionsSuperClass_1 {

  //Functions vs functions in subclass
  function foo1(a: ArrayList){}
  function foo2(a: ArrayList<Object>){}
  function foo3(a: ArrayList<Integer>){}
  function foo4(a: ArrayList<Integer>){}
  //type param in subclass is subtype of the type param here in superclass
  function foo5(a: ArrayList<java.lang.Number>){}
  function foo6(a: ArrayList<Integer>){}

  //Properties vs properties in subclass
  property set Property1(a : ArrayList) {}
  property set Property2(a : ArrayList<Object>) {}
  property set Property3(a : ArrayList<Integer>) {}
  property set Property4(a : ArrayList<Integer>) {}
  property set Property5(a : ArrayList<java.lang.Number>) {}
  property set Property6(a : ArrayList<Integer>) {}

  //Properties vs functions in subclass
  property set Property21(a : ArrayList) {}
  property set Property22(a : ArrayList<Object>) {}
  property set Property23(a : ArrayList<Integer>) {}
  property set Property24(a : ArrayList<Integer>) {}
  property set Property25(a : ArrayList<java.lang.Number>) {}
  property set Property26(a : ArrayList<Integer>) {}

  //Functions vs properties in subclass
  function setFoo21(a: ArrayList){}
  function setFoo22(a: ArrayList<Object>){}
  function setFoo23(a: ArrayList<Integer>){}
  function setFoo24(a: ArrayList<Integer>){}
  function setFoo25(a: ArrayList<java.lang.Number>){}
  function setFoo26(a: ArrayList<Integer>){}

  //As properties vs As properties in subclass
  var _property311 : ArrayList as  Property311
  var _property312 : ArrayList<Object> as  Property312
  var _property313 : ArrayList<Integer> as  Property313
  var _property314 : ArrayList<Integer> as  Property314
  var _property315 : ArrayList<java.lang.Number> as  Property315
  var _property316 : ArrayList<Integer> as  Property316

  //As properties vs properties in subclass
  var _property41 : ArrayList as  Property41
  var _property42 : ArrayList<Object> as  Property42
  var _property43 : ArrayList<Integer> as  Property43
  var _property44 : ArrayList<Integer> as  Property44
  var _property45 : ArrayList<java.lang.Number> as  Property45
  var _property46 : ArrayList<Integer> as  Property46

  //As properties vs functions in subclass
  var _property51 : ArrayList as  Property51
  var _property52 : ArrayList<Object> as  Property52
  var _property53 : ArrayList<Integer> as  Property53
  var _property54 : ArrayList<Integer> as  Property54
  var _property55 : ArrayList<java.lang.Number> as  Property55
  var _property56 : ArrayList<Integer> as  Property56

  //properties vs As properties in subclass
  property set Property111(a : ArrayList) {}
  property set Property112(a : ArrayList<Object>) {}
  property set Property113(a : ArrayList<Integer>) {}
  property set Property114(a : ArrayList<Integer>) {}
  property set Property115(a : ArrayList<java.lang.Number>) {}
  property set Property116(a : ArrayList<Integer>) {}


  //functions vs As properties in subclass
  function setProperty211(a: ArrayList){}
  function setProperty212(a: ArrayList<Object>){}
  function setProperty213(a: ArrayList<Integer>){}
  function setProperty214(a: ArrayList<Integer>){}
  function setProperty215(a: ArrayList<java.lang.Number>){}
  function setProperty216(a: ArrayList<Integer>){}
}