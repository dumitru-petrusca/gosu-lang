package gw.specContrib.expressions.cast.generics

class Errant_GenericsAssignCastabilityInterfaces3 {
  interface Super<T> {
  }

  interface Sub<T> extends Super<T> {
  }

  interface Parent {
  }

  interface Child extends Parent {
  }

  var superParent: Super<Parent>
  var superChild: Super<Child>

  var subParent: Sub<Parent>
  var subChild: Sub<Child>

  class Cl<P extends Parent, C extends Child, T extends P> {

    var superP: Super<P>
    var subP: Sub<P>
    var superC: Super<C>
    var subC: Sub<C>
    var superT: Super<T>
    var subT: Sub<T>

    function testAssignability() {
      //Set#1
      var superP111: Super<Parent> = superP
      var superP112: Super<Parent> = subP
      var superP113: Super<Parent> = superC
      var superP114: Super<Parent> = subC
      var superP115: Super<Parent> = superT
      var superP116: Super<Parent> = subT

      var subP111: Sub<Parent> = superP                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>'
      var subP112: Sub<Parent> = subP
      var subP113: Sub<Parent> = superC                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<C>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>'
      var subP114: Sub<Parent> = subC
      var subP115: Sub<Parent> = superT                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>'
      var subP116: Sub<Parent> = subT

      var superC111: Super<Child> = superP                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var superC112: Super<Child> = subP                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<P>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var superC113: Super<Child> = superC
      var superC114: Super<Child> = subC
      var superC115: Super<Child> = superT                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var superC116: Super<Child> = subT                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<T>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'

      var subC111: Sub<Child> = superP                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var subC112: Sub<Child> = subP                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<P>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var subC113: Sub<Child> = superC                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<C>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var subC114: Sub<Child> = subC
      var subC115: Sub<Child> = superT                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'
      var subC116: Sub<Child> = subT                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<T>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>'

      //Set#2
      var p111: Super<P> = superParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>'
      var p112: Super<P> = subParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>'
      var p113: Sub<P> = superParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<P>'
      var p114: Sub<P> = subParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<P>'

      var p211: Super<P> = superChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>'
      var p212: Super<P> = subChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<P>'
      var p213: Sub<P> = superChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<P>'
      var p214: Sub<P> = subChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<P>'

      var c111: Super<C> = superParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<C>'
      var c112: Super<C> = subParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<C>'
      var c113: Sub<C> = superParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<C>'
      var c114: Sub<C> = subParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<C>'

      var c211: Super<C> = superChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<C>'
      var c212: Super<C> = subChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<C>'
      var c213: Sub<C> = superChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<C>'
      var c214: Sub<C> = subChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<C>'

      var t111: Super<T> = superParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>'
      var t112: Super<T> = subParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>'
      var t113: Sub<T> = superParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<T>'
      var t114: Sub<T> = subParent                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.PARENT>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<T>'

      var t211: Super<T> = superChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>'
      var t212: Super<T> = subChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<T>'
      var t213: Sub<T> = superChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUPER<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<T>'
      var t214: Sub<T> = subChild                  //## issuekeys: INCOMPATIBLE TYPES. FOUND: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.CHILD>', REQUIRED: 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.GENERICS.GENERICSASSIGNMENT.INTERFACES.ERRANT_GENERICSASSIGNCASTABILITYINTERFACES3.SUB<T>'


    }
    function testCastability() {
      //Set#1
      var superP111 = superP as Super<Parent>
      var superP112 = subP as Super<Parent>
      var superP113 = superC as Super<Parent>
      var superP114 = subC as Super<Parent>
      var superP115 = superT as Super<Parent>
      var superP116 = subT as Super<Parent>

      var subP111 = superP as Sub<Parent>
      var subP112 = subP as Sub<Parent>
      var subP113 = superC as Sub<Parent>
      var subP114 = subC as Sub<Parent>
      var subP115 = superT as Sub<Parent>
      var subP116 = subT as Sub<Parent>

      var superC111 = superP as Super<Child>
      var superC112 = subP as Super<Child>
      var superC113 = superC as Super<Child>
      var superC114 = subC as Super<Child>
      var superC115 = superT as Super<Child>
      //IDE-1731 - Issue in OS Gosu
      var superC116 = subT as Super<Child>


      var subC111 = superP as Sub<Child>
      var subC112 = subP as Sub<Child>
      var subC113 = superC as Sub<Child>
      var subC114 = subC as Sub<Child>
      var subC115 = superT as Sub<Child>
      var subC116 = subT as Sub<Child>

      //Set#2
      var p111 = superParent as Super<P>
      var p112 = subParent as Super<P>
      var p113= superParent as Sub<P>
      var p114= subParent as Sub<P>

      var p211 = superChild as Super<P>
      var p212 = subChild as Super<P>
      var p213 = superChild as Sub<P>
      var p214 = subChild as Sub<P>

      var c111 = superParent as Super<C>
      var c112 = subParent as Super<C>
      var c113 = superParent as Sub<C>
      var c114 = subParent as Sub<C>

      var c211 = superChild as Super<C>
      var c212 = subChild as Super<C>
      var c213 = superChild as Sub<C>
      var c214 = subChild as Sub<C>

      var t111 = superParent as Super<T>
      var t112 = subParent as Super<T>
      var t113 = superParent as Sub<T>
      var t114 = subParent as Sub<T>

      var t211 = superChild as Super<T>
      var t212 = subChild as Super<T>
      var t213 = superChild as Sub<T>
      var t214 = subChild as Sub<T>

    }
  }

}