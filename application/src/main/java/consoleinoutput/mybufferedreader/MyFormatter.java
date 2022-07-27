/*
 *
 * consoleinoutput.mybufferedreader.MyFormatter
 *
 *
 * This document contains trade secret data which is the property of
 * OpenKnowledge GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from open knowledge GmbH.
 *
 * Copyright (C) {YEAR} open knowledge GmbH / Oldenburg / Germany
 *
 */

package consoleinoutput.mybufferedreader;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {

  public String format(LogRecord rec) {
    return rec.getMessage();
  }

  @Override
  public synchronized String formatMessage(LogRecord record) {
    return record.getMessage();
  }
}
