package jle.vereinsverwaltung.mitglied.ValueObjects.adresse;

import java.util.regex.Pattern;

import jle.exceptions.InvalidAddressLineException;

public class Adresszeile {

  private String adressezeile;

  private final String REGEX = "[\\p{L}\\p{N}./ -]{1,80}";

  public Adresszeile(String adressezeile, boolean isOptional) {
    if (isOptional) {
      this.adressezeile = validateNormalizeOptionalAddressLine(adressezeile);
    } else {
      this.adressezeile = validateNormalizeAddressLine(adressezeile);
    }
  }

  private String validateNormalizeAddressLine(String addressLine) {
    if (Pattern.compile(REGEX).matcher(addressLine).matches()) {
      return addressLine;
    } else {
      throw new InvalidAddressLineException();
    }
  }

  private String validateNormalizeOptionalAddressLine(String addressLine) {
    if (addressLine.equals("")) {
      return "";
    } else {
      if (Pattern.compile(REGEX).matcher(addressLine).matches()) {
        return addressLine;
      } else {
        throw new InvalidAddressLineException();
      }
    }
  }

  @Override
  public int hashCode() {
    return adressezeile.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Adresszeile) {
      Adresszeile adresszeile = (Adresszeile)obj;
      return this.getAdressezeile().equalsIgnoreCase(adresszeile.getAdressezeile());
    }
    return false;
  }

  @Override
  public String toString() {
    return adressezeile;
  }

  public String getAdressezeile() {
    return adressezeile;
  }
}
