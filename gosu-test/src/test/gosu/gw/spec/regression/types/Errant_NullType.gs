package gw.spec.regression.types

uses java.lang.Integer
uses java.util.HashMap

class Errant_NullType {

  var myVar1 = 10 + null      //## issuekeys: MSG_MSG_TYPE_MISMATCH

}