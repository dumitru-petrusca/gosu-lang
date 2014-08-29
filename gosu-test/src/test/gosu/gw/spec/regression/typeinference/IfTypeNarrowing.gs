package gw.spec.regression.typeinference

uses junit.framework.TestCase
uses java.util.Date

class IfTypeNarrowing extends TestCase {

  function test() {
    var x: Object = "neat"

    if (x typeis String) {
      print(x.charAt(0))
    } else if (x typeis Date) {
      print(x.Time)
    }
  }

}