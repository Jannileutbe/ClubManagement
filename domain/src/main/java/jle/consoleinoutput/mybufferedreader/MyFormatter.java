package jle.consoleinoutput.mybufferedreader;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

  public String format(LogRecord rec) {
    String ausgabe = rec.getMessage();
    return ausgabe;
  }

  @Override
  public synchronized String formatMessage(LogRecord record) {
    return record.getMessage();
  }
}
