package gw.spec.regression.operators

uses gw.spec.regression.Type1
uses gw.spec.regression.SubType1

class Errant_BinaryOperators {
  var o: Object
  var s: String
  var t: Type1
  var sub: SubType1

  function f1() {
    var eq1 = 42 == 41
    var eq2 = 42 == true         //## issuekeys: MSG_
    var eq3 = 42 == o
    var eq4 = 42 == s
    var eq5 = 42 == t            //## issuekeys: MSG_
    var eq6 = t == sub
  }

  function f2() {
    var eq1 = 42 > 41
    var eq2 = 42 > true          //## issuekeys: MSG_
    var eq3 = 42 > o
    var eq4 = 42 > s
    var eq5 = 42 > t             //## issuekeys: MSG_
    var eq6 = t > sub            //## issuekeys: MSG_
  }

  function f3() {
    var eq1 = 42 << 41
    var eq2 = 42 << true           //## issuekeys: MSG_
    var eq3 = 42 << o              //## issuekeys: MSG_
    var eq4 = 42 << s              //## issuekeys: MSG_
    var eq5 = 42 << t              //## issuekeys: MSG_
    var eq6 = t << sub             //## issuekeys: MSG_
  }

  function f4() {
    var eq1 = 42 & 41
    var eq2 = 42 & true             //## issuekeys: MSG_
    var eq3 = 42 & o                //## issuekeys: MSG_
    var eq4 = 42 & s                //## issuekeys: MSG_
    var eq5 = 42 & t                //## issuekeys: MSG_
    var eq6 = t & sub               //## issuekeys: MSG_
  }

}