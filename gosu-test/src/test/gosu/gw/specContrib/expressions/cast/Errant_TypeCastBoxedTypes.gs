package gw.specContrib.expressions.cast

uses java.lang.*
uses java.math.BigDecimal
uses java.math.BigInteger

class Errant_TypeCastBoxedTypes {
  function testBoolean(b: Boolean) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.BOOLEAN' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.BOOLEAN' TO 'JAVA.MATH.BIGINTEGER'
    var b315 = b as BigDecimal      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.BOOLEAN' TO 'JAVA.MATH.BIGDECIMAL'
  }
  function testCharacter(b: Character) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.CHARACTER' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
  function testByte(b: Byte) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.BYTE' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
  function testShort(b: Short) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.SHORT' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
  function testInteger(b: Integer) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.INTEGER' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
  function testFloat(b: Float) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.FLOAT' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
  function testLong(b: Long) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.LONG' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
  function testDouble(b: Double) {
    var b111 = b as char
    var b112 = b as byte
    var b113 = b as short
    var b114 = b as int
    var b115 = b as float
    var b116 = b as long
    var b117 = b as double
    var b118 = b as boolean

    var b211 = b as Character
    var b212 = b as Byte
    var b213 = b as Short
    var b214 = b as Integer
    var b215 = b as Float
    var b216 = b as Long
    var b217 = b as Double
    var b218 = b as Boolean

    var b311 = b as String
    var b312 = b as DateTime      //## issuekeys: INCONVERTIBLE TYPES; CANNOT CAST 'JAVA.LANG.DOUBLE' TO 'JAVA.UTIL.DATE'
    var b313 = b as Object
    var b314 = b as BigInteger
    var b315 = b as BigDecimal
  }
}