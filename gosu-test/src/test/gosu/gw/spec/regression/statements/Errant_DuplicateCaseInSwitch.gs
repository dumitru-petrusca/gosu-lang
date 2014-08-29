package gw.spec.regression.statements

uses junit.framework.TestCase
uses java.util.Date

class Errant_DuplicateCaseInSwitch extends TestCase {

  function test() {
    var x: Object = "neat"

    switch (typeof(x)) {
      case String:
      case String:           //## issuekeys: MSG_DUPLICATE_CASE_EXPRESSION
        break
      case Type1:
        break;
    }

    switch (23) {
      case 2:
        break;
      case 1 + 1:       //## issuekeys: MSG_DUPLICATE_CASE_EXPRESSION
        break;
    }
  }

}