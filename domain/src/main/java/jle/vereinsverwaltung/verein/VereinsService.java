package jle.vereinsverwaltung.verein;

public class VereinsService {

  public Verein createVerein(VereinsName name, VereinsBeschreibung beschreibung) {
    return new Verein(name, beschreibung);
  }

}
