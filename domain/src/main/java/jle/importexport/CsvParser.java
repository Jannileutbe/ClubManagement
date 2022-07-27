/*
 *
 * jle.importexport.CsvParser
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

package jle.importexport;

import jle.vereinsverwaltung.verein.Verein;

public interface CsvParser {

  String parseToString(Verein verein);
}
