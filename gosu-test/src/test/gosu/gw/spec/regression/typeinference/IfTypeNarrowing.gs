package gw.spec.regression.typeinference

uses java.util.Date

class IfTypeNarrowing {

  function test() {
    var x: Object = "neat"

    if (x typeis String) {
      print(x.charAt(0))
    } else if (x typeis Date) {
      print(x.Time)
    }
  }

}