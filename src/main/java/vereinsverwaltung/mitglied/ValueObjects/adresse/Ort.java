package vereinsverwaltung.mitglied.ValueObjects.adresse;

import java.util.regex.Pattern;
import exceptions.InvalidCityException;

public class Ort {

  private String ort;

  private final String REGEX = "[\\p{L}./ -]{1,50}";

  public Ort(String city) {
    this.ort = validateNormalizeCity(city);
  }

  public String validateNormalizeCity(String city) {
    if (Pattern.compile(REGEX).matcher(city).matches()) {
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
      return this.getOrt().equalsIgnoreCase(ort.getOrt());
    }
    return false;
  }

  @Override
  public String toString() {
    return ort;
  }

  public String getOrt() {
    return ort;
  }
}
