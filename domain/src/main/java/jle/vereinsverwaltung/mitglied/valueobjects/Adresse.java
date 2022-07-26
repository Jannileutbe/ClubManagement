package jle.vereinsverwaltung.mitglied.valueobjects;

import jle.vereinsverwaltung.mitglied.valueobjects.adresse.Adresszeile;
import jle.vereinsverwaltung.mitglied.valueobjects.adresse.Ort;
import jle.vereinsverwaltung.mitglied.valueobjects.adresse.Postleitzahl;

public class Adresse {

  private final Adresszeile adresszeileEins;
  private final Adresszeile adresszeileZwei;
  private final Postleitzahl postleitzahl;
  private final Ort ort;

  public Adresse(Adresszeile adresszeileEins, Adresszeile adresszeileZwei, Postleitzahl postleitzahl, Ort ort) {
    this.adresszeileEins = adresszeileEins;
    this.adresszeileZwei = adresszeileZwei;
    this.postleitzahl = postleitzahl;
    this.ort = ort;
  }

  public Adresse(Adresszeile adresszeileEins, Postleitzahl postleitzahl, Ort ort) {
    this.adresszeileEins = adresszeileEins;
    this.adresszeileZwei = new Adresszeile("", true);
    this.postleitzahl = postleitzahl;
    this.ort = ort;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Adresse)) {
      return false;
    }
    Adresse checkAdresse = (Adresse)obj;
    return this.getAdresszeileEins().equals(checkAdresse.getAdresszeileEins())
        && this.getPostleitzahl().equals(checkAdresse.getPostleitzahl());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    String comma = ", ";
    String returnString = this.getAdresszeileEins().toString() + comma;
    if (!this.getAdresszeileZwei().getAdressezeile().equals("")) {
      returnString += this.getAdresszeileZwei().toString() + comma;
    }
    returnString += this.getPostleitzahl().toString() + comma;
    returnString += this.getOrt().toString();
    return returnString;
  }

  public Adresszeile getAdresszeileEins() {
    return adresszeileEins;
  }

  public Adresszeile getAdresszeileZwei() {
    return adresszeileZwei;
  }

  public Postleitzahl getPostleitzahl() {
    return postleitzahl;
  }

  public Ort getOrt() {
    return ort;
  }
}
