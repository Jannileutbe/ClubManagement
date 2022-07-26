package jle.vereinsverwaltung.mitglied.valueobjects.adresse;

import java.util.regex.Pattern;

import jle.exceptions.InvalidCityException;

public class Ort {

  private final String ortString;

  private final String regex = "[\\p{L}./ -]{1,50}";

  public Ort(String city) {
    this.ortString = validateNormalizeCity(city);
  }

  public String validateNormalizeCity(String city) {
    if (Pattern.compile(regex).matcher(city).matches()) {
      return city;
    } else {
      throw new InvalidCityException();
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Ort) {
      Ort ort = (Ort)obj;
      return this.getOrtString().equalsIgnoreCase(ort.getOrtString());
    }
    return false;
  }

  @Override
  public String toString() {
    return ortString;
  }

  public String getOrtString() {
    return ortString;
  }
}
