package gw.spec.regression.operators

uses gw.BaseVerifyErrantTest

class ShiftAssignmentOperatorTest extends BaseVerifyErrantTest {
  function testShiftAssignmentOperators() {
    var i: int = -8
    i <<= 2
    assertEquals(i, -32)
    i = -8
    i >>= 2
    assertEquals(i, -2)
    i = -8
    i >>>= 2
    assertEquals(i, 2147483644)
  }
}