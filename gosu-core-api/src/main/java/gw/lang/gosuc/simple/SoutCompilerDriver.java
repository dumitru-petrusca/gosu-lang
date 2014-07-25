package gw.lang.gosuc.simple;

import java.io.File;

public class SoutCompilerDriver implements ICompilerDriver {
  private int nErrors;

  @Override
  public void sendCompileIssue(File file, int category, long offset, long line, long column, String message) {
    String text = "";
    if (category == WARNING) {
      text = "Warning: ";
    } else if (category == ERROR) {
      text = "Error: ";
      nErrors++;
    }
    System.out.println(text + message);
  }

  public boolean hasErrors() {
    return nErrors > 0;
  }
}
