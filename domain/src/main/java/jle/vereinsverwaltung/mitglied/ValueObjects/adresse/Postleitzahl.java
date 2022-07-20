package jle.vereinsverwaltung.mitglied.ValueObjects.adresse;

import java.util.regex.Pattern;

import jle.exceptions.InvalidPostalCodeException;

public class Postleitzahl {

  private String postleitzahl;

  private final String REGEX = "[\\p{N}]{5}";

  public Postleitzahl(String postalCode) {
    this.postleitzahl = validateNormalizePostalCode(postalCode);
  }

  private String validateNormalizePostalCode(String postalCode) {
    if (Pattern.compile(REGEX).matcher(postalCode).matches()) {
      return postalCode;
    } else {
      throw new InvalidPostalCodeException();
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Postleitzahl) {
      Postleitzahl postleitzahl = (Postleitzahl)obj;
      return this.getPostleitzahl().equalsIgnoreCase(postleitzahl.getPostleitzahl());
    }
    return false;
  }

  @Override
  public String toString() {
    return postleitzahl;
  }

  public String getPostleitzahl() {
    return postleitzahl;
  }
}
