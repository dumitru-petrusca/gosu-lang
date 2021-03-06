package gw.specContrib.typeinference

uses java.util.Date

class Errant_IfTypeNarrowing {
  interface I1 {
    function foo1()
  }

  interface I2 {
    function foo2()
  }

  function test() {
    var x: Object = "neat"

    if (x typeis String) {
      print(x.charAt(0))
    } else if (x typeis Date) {
      print(x.Time)
    }

    // IDE-2031
    if (x typeis I1) {
      if (x typeis I2) {
        x.foo1()
        x.foo2()
      }
    }

    if (x typeis I1 && x typeis I2) {
      x.foo1()
      x.foo2()
    }

    var i1: I1
    if (i1 typeis I2) {
      i1.foo1()
      i1.foo2()
    }
  }
}