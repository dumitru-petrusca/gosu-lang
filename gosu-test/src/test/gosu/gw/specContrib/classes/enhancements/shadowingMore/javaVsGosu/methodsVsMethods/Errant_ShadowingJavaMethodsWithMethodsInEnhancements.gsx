package gw.specContrib.classes.enhancements.shadowingMore.javaVsGosu.methodsVsMethods

enhancement Errant_ShadowingJavaMethodsWithMethodsInEnhancements: Errant_JavaMethodsVsMethods {
  internal function methodInternal(): String {      //## issuekeys: THE FUNCTION 'METHODINTERNAL()' IS ALREADY DEFINED IN THE TYPE 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.JAVAINTERACTION.SHADOWING.JAVAVSGOSU.METHODSVSMETHODS.ERRANT_JAVAMETHODSVSMETHODS'. ENHANCEMENTS CANNOT OVERRIDE FUNCTIONS.
    return null;
  }

  private function methodPrivate(): String {
    return null;
  }

  protected function methodProtected(): String {      //## issuekeys: THE FUNCTION 'METHODPROTECTED()' IS ALREADY DEFINED IN THE TYPE 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.JAVAINTERACTION.SHADOWING.JAVAVSGOSU.METHODSVSMETHODS.ERRANT_JAVAMETHODSVSMETHODS'. ENHANCEMENTS CANNOT OVERRIDE FUNCTIONS.
    return null;
  }

  public function methodPublic(): String {      //## issuekeys: THE FUNCTION 'METHODPUBLIC()' IS ALREADY DEFINED IN THE TYPE 'GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.JAVAINTERACTION.SHADOWING.JAVAVSGOSU.METHODSVSMETHODS.ERRANT_JAVAMETHODSVSMETHODS'. ENHANCEMENTS CANNOT OVERRIDE FUNCTIONS.
    return null;
  }

}
