package gw.specContrib.expressions.cast.generics

class Errant_GenericsAssignabilitySiblings {
  class Super<T>{}
  class Sub<T> extends Super<T> {}

  class Parent {}
  class Child extends Parent {}
  class Child2 extends Parent {}

  var superParent: Super<Parent>
  var superChild: Super<Child>

  var subParent: Sub<Parent>
  var subChild: Sub<Child>

  var superChild2: Super<Child2>
  var subChild2: Sub<Child2>

  function testAssignability() {

    //IDE-1719 Parser Issue
    var a111: Super<Child2> = superParent      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
    //IDE-1726 Parser Issue
    var a112: Super<Child2> = superChild      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
    //Covered in? IDE-1719 Parser Issue
    var a113: Super<Child2> = subParent      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
    //IDE-1726 Parser Issue
    var a114: Super<Child2> = subChild      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
//    var a115: Super<Child2> = superChild2
    var a116: Super<Child2> = subChild2

    var a211: Sub<Child2> = superParent      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
    var a212: Sub<Child2> = superChild      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
    //IDE-1719 Parser Issue
    var a213: Sub<Child2> = subParent      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
     //IDE-1726 Parser Issue
    var a214: Sub<Child2> = subChild      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
    var a215: Sub<Child2> = superChild2      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>'
//    var a216: Sub<Child2> = subChild2

    var a311: Super<Parent> = superChild2
    var a312: Super<Parent> = subChild2
    var a313: Sub<Parent> = superChild2      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.PARENT>'
    var a314: Sub<Parent> = subChild2

    //IDE-1726 Parser Issue
    var a411: Super<Child> = superChild2      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
    var a412: Super<Child> = subChild2      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
    var a413: Sub<Child> = superChild2      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
    var a414: Sub<Child> = subChild2      //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
  }

  function testCastability() {
    var c111 = superChild2 as Super<Parent>
    //IDE-1727 Parser Issue
    var c112 = superChild2 as Super<Child>      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>' TO 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
//    var c113 = superChild2 as Super<Child2>
    var c114 = superChild2 as Sub<Parent>
    //IDE-1727 Parser Issue
    var c115 = superChild2 as Sub<Child>      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>' TO 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
    var c116 = superChild2 as Sub<Child2>

    var c211 = subChild2 as Super<Parent>
    //IDE-1727 Parser Issue
    var c212 = subChild2 as Super<Child>      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>' TO 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
    var c213 = subChild2 as Super<Child2>
    var c214 = subChild2 as Sub<Parent>
    //IDE-1727 Parser Issue
    var c215 = subChild2 as Sub<Child>      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD2>' TO 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.TOBEPUSHED.ERRANT_GENERICSASSIGNABILITYSIBLINGS.CHILD>'
//    var c216 = subChild2 as Sub<Child2>
  }
}