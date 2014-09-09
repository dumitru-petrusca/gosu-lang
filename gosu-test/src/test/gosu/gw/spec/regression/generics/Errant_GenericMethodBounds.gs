class Errant_GenericMethodBounds {
  function upperBoundNumber<T extends Number>(t : T) : T { return null }

  function caller() {
    upperBoundNumber("string")   //## issuekeys: MSG_
  }
}
