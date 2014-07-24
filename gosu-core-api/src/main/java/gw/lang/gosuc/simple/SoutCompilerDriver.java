package gw.lang.gosuc.simple;

import java.io.File;

public class SoutCompilerDriver implements ICompilerDriver {

  @Override
  public void sendCompileIssue(File file, int category, long offset, long line, long column, String message) {
    System.out.println((category == WARNING ? "Warning: " : "Error: ") + message);
  }

}
