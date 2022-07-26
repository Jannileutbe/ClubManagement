package jle.vereinsverwaltung.verein;

import consoleinoutput.mybufferedreader.MyBufferedReader;

public class VereinsService {

  public Verein createVerein(String nameRules, String descriptionRules) {
    VereinsName name = new VereinsName(MyBufferedReader.readInString(nameRules));
    VereinsBeschreibung beschreibung = new VereinsBeschreibung(MyBufferedReader.readInString(descriptionRules));
    return new Verein(name, beschreibung);
  }
}
