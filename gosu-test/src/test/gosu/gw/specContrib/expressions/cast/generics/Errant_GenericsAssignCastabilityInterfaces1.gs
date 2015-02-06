package gw.specContrib.expressions.cast.generics

uses java.io.Serializable
uses java.lang.Cloneable
uses java.lang.Integer

class Errant_GenericsAssignCastabilityInterfaces1 {
  interface A{}
  interface B{}

  var a : A
  var b : B

  class Box<T> {}

  var boxA: Box<A>
  var boxB: Box<B>

  function testAssignability() {
    var a111: Box<A> = boxB            //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.BOX<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.B>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.BOX<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.A>'
    var b111: Box<B> = boxA            //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.BOX<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.A>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.BOX<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES.B>'
  }

  function testCastability() {
    var x111 = boxA as Box<B>
    var x112 = boxB as Box<A>

    //In-built types
    var p111 = boxA as Box<Cloneable>
    var p112 = boxA as Box<Serializable>
    var p113 = boxA as Box<java.lang.Number>

    var q111 = boxB as Box<Cloneable>
    var q112 = boxB as Box<Serializable>
    var q113 = boxB as Box<java.lang.Number>



  }

}