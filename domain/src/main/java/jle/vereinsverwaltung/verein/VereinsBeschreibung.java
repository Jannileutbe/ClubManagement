package jle.vereinsverwaltung.verein;

public class VereinsBeschreibung {

  private final String vereinsBeschreibungString;

  public VereinsBeschreibung(String vereinsBeschreibungString) {
    this.vereinsBeschreibungString = vereinsBeschreibungString;
  }

  @Override
  public int hashCode() {
    return vereinsBeschreibungString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof VereinsBeschreibung) {
      VereinsBeschreibung vereinsBeschreibung = (VereinsBeschreibung)obj;
      if (this.getVereinsBeschreibungString().equalsIgnoreCase(vereinsBeschreibung.getVereinsBeschreibungString())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return vereinsBeschreibungString;
  }

  public String getVereinsBeschreibungString() {
    return vereinsBeschreibungString;
  }

}
