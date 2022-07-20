package jle.vereinsverwaltung.mitglied.ValueObjects;

import jle.vereinsverwaltung.mitglied.ValueObjects.adresse.Adresszeile;
import jle.vereinsverwaltung.mitglied.ValueObjects.adresse.Ort;
import jle.vereinsverwaltung.mitglied.ValueObjects.adresse.Postleitzahl;

public class Adresse {

  private Adresszeile adresszeileEins;
  private Adresszeile adresszeileZwei;
  private Postleitzahl postleitzahl;
  private Ort ort;

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
    return this.getAdresszeileEins().equals(checkAdresse.getAdresszeileEins()) && this.getPostleitzahl().equals(checkAdresse.getPostleitzahl());
  }

  @Override
  public String toString() {
    String returnString = this.getAdresszeileEins().toString() + ", ";
    if (!this.getAdresszeileZwei().getAdressezeile().equals("")) {
      returnString += this.getAdresszeileZwei().toString() + ", ";
    }
    returnString += this.getPostleitzahl().toString() + ", ";
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
