package gw.specContrib.classes.method_Scoring.collections_And_Generics

uses java.lang.Integer
uses java.lang.Long
uses java.util.ArrayList
uses java.util.Collection

class Errant_Generics_MethodScoring_6 {
  class A{}
  class B{}

  class P{}
  interface Q extends Collection<Integer> {}

  var pp : P
  var qq : Q

  var aListInt : ArrayList<Integer>

  //6. param extends Collection<of something> cases
  function foo6<T>(a: T) : A {return null}
  function foo6<T extends Collection<Integer>>(a: T) : B {return null}

  function caller() {
    var a1111 : A =  foo6({1, 2, 3})            //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.B', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.A'
    var a1112 : A =  foo6(aListInt)            //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.B', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.A'
    var a1113 : A =  foo6(pp)
    var a1114 : A =  foo6(qq)            //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.B', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.A'

    var b1111 : B =  foo6({1, 2, 3})
    var b1112 : B =  foo6(aListInt)
    var b1113 : B =  foo6(pp)            //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.A', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.METHODSCORINGOVERLOADING.GENERICS_METHODSCORING.GENERICS_METHODSCORING_6.B'
    var b1114 : B =  foo6(qq)

    var c1111 =  foo6({1, 2, 3})
    var c1112 =  foo6(aListInt)
    var c1113 =  foo6(pp)
    var c1114 =  foo6(qq)
  }
}