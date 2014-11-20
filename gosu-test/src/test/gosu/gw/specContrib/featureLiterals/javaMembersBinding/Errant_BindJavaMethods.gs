package gw.specContrib.featureLiterals.javaMembersBinding

class Errant_BindJavaMethods {
  //methods
  //IDE-1375 - Should show errors where the number/type of arguments is correct in definition
  //Issue in both OS Gosu and Parser
  var internalFunction111 = JavaClass#javaInternalFun(String, int)
  //The following case 'internalFunction112' may be a valid case. Waiting for response for first comment in IDE-1375
  var internalFunction112 = JavaClass#javaInternalFun(JavaClass, String, int) //## issuekeys:
  var internalFunction113 = JavaClass#javaInternalFun(JavaClass)  //## issuekeys:
  var internalFunction114 = JavaClass#javaInternalFun(JavaClass, String)  //## issuekeys:
  var internalFunction115 = JavaClass#javaInternalFun(String)             //## issuekeys:
  var internalFunction116 = JavaClass#javaInternalFun(int, int, int)      //## issuekeys:
  var internalFunction117 = JavaClass#javaInternalFun()                   //## issuekeys:
  var internalFunction118 = JavaClass#javaInternalFun      //## issuekeys: CANNOT RESOLVE SYMBOL 'JAVAINTERNALFUN'

  var privateFunction111 = JavaClass#javaPrivateFun(String, int)
  var privateFunction112 = JavaClass#javaPrivateFun(JavaClass, String, int)  //## issuekeys:
  var privateFunction113 = JavaClass#javaPrivateFun(JavaClass)  //## issuekeys:
  var privateFunction114 = JavaClass#javaPrivateFun(JavaClass, String) //## issuekeys:
  var privateFunction115 = JavaClass#javaPrivateFun(String)         //## issuekeys:
  var privateFunction116 = JavaClass#javaPrivateFun(int, int, int)    //## issuekeys:
  var privateFunction117 = JavaClass#javaPrivateFun()   //## issuekeys:
  var privateFunction118 = JavaClass#javaPrivateFun      //## issuekeys: CANNOT RESOLVE SYMBOL 'JAVAPRIVATEFUN'

  var protectedFunction111 = JavaClass#javaProtectedFun(String, int)
  var protectedFunction112 = JavaClass#javaProtectedFun(JavaClass, String, int)   //## issuekeys:
  var protectedFunction113 = JavaClass#javaProtectedFun(JavaClass)    //## issuekeys:
  var protectedFunction114 = JavaClass#javaProtectedFun(JavaClass, String)    //## issuekeys:
  var protectedFunction115 = JavaClass#javaProtectedFun(String)   //## issuekeys:
  var protectedFunction116 = JavaClass#javaProtectedFun(int, int, int)    //## issuekeys:
  var protectedFunction117 = JavaClass#javaProtectedFun()   //## issuekeys:
  var protectedFunction118 = JavaClass#javaProtectedFun      //## issuekeys: CANNOT RESOLVE SYMBOL 'JAVAPROTECTEDFUN'

  var publicFunction111 = JavaClass#javaPublicFun(String, int)
  var publicFunction112 = JavaClass#javaPublicFun(JavaClass, String, int)//## issuekeys:
  var publicFunction113 = JavaClass#javaPublicFun(JavaClass)//## issuekeys:
  var publicFunction114 = JavaClass#javaPublicFun(JavaClass, String)//## issuekeys:
  var publicFunction115 = JavaClass#javaPublicFun(String)//## issuekeys:
  var publicFunction116 = JavaClass#javaPublicFun(int, int, int)//## issuekeys:
  var publicFunction117 = JavaClass#javaPublicFun()//## issuekeys:
  var publicFunction118 = JavaClass#javaPublicFun      //## issuekeys: CANNOT RESOLVE SYMBOL 'JAVAPUBLICFUN'


  function invokeJavaFunctions() {
    var jInstance: JavaClass
    internalFunction111.invoke(jInstance, "Ed", 42)
    internalFunction111.invoke(jInstance, "Ed")      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING)'
    internalFunction111.invoke(jInstance)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS)'
    internalFunction111.invoke("Ed", 42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(JAVA.LANG.STRING, INT)'
    internalFunction111.invoke(42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(INT)'

    privateFunction111.invoke(jInstance, "Ed", 42)
    privateFunction111.invoke(jInstance, "Ed")      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING)'
    privateFunction111.invoke(jInstance)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS)'
    privateFunction111.invoke("Ed", 42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(JAVA.LANG.STRING, INT)'
    privateFunction111.invoke(42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(INT)'

    protectedFunction111.invoke(jInstance, "Ed", 42)
    protectedFunction111.invoke(jInstance, "Ed")      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING)'
    protectedFunction111.invoke(jInstance)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS)'
    protectedFunction111.invoke("Ed", 42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(JAVA.LANG.STRING, INT)'
    protectedFunction111.invoke(42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(INT)'

    publicFunction111.invoke(jInstance, "Ed", 42)
    publicFunction111.invoke(jInstance, "Ed")      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING)'
    publicFunction111.invoke(jInstance)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS)'
    publicFunction111.invoke("Ed", 42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(JAVA.LANG.STRING, INT)'
    publicFunction111.invoke(42)      //## issuekeys: 'INVOKE(GW.SPECCONTRIB.AAA.PARSERVSOPENSOURCE.FEATURELITERALS.FEATURELITERALSMAIN.JAVAFEATURELITERALS.JAVACLASS, JAVA.LANG.STRING, INT)' IN '' CANNOT BE APPLIED TO '(INT)'

  }
}