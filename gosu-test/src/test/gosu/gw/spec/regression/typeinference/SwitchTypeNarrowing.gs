package gw.spec.regression.typeinference

uses junit.framework.TestCase

class SwitchTypeNarrowing extends TestCase {
  private enum MyEnum  { ONE, TWO }

  function test() {
    var x: Object = "neat"
    var f = x typeis String

    switch (typeof( ((x)) )) {  // crazy parentheses are valid 
      case MyEnum:
        if (f) {
          switch (x) {
            case ONE:
              return
            case TWO:
              throw new RuntimeException()
          }
        } else {
          break
        }
      case String:
        x.contains("ne")
    }
  }
}
