package gw.spec.regression.typeinference

uses junit.framework.TestCase
uses java.util.Date

class SwitchTypeNarrowing extends TestCase {

  function test() {
    var x: Object = "neat"

    switch (typeof(x)) {
      case String:
        print(x.charAt(0))
        break
      case Date:
        print(x.Time)
        break
    }
  }

}