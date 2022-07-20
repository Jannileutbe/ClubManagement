package jle.importAndExport;

import jle.vereinsverwaltung.verein.Verein;

public interface CSVParser {

  String parseToString(Verein verein);
}
