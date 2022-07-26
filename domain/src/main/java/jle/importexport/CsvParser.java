package jle.importexport;

import jle.vereinsverwaltung.verein.Verein;

public interface CsvParser {

  String parseToString(Verein verein);
}
