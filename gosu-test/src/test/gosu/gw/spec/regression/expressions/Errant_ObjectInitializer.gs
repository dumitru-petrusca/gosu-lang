package gw.spec.regression.expressions

uses junit.framework.TestCase
uses java.lang.Integer
uses java.lang.System

class Errant_ObjectInitializer extends TestCase {

  function foo() {
    var a = new Integer[]{
        1,
        ""                              //## issuekeys: MSG_
    }

    var b = new List<Integer>(){
        "a",                            //## issuekeys: MSG_
        23,
        1.1                             //## issuekeys: MSG_
    }

    var xx = new A(){
        System.out.println("")          //## issuekeys: MSG_
    }
  }

  class A {
  }

}