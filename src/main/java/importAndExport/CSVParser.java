package importAndExport;

import vereinsverwaltung.verein.Verein;

public interface CSVParser {

  String parseToString(Verein verein);
}
