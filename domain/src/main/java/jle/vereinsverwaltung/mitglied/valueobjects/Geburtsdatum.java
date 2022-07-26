package jle.vereinsverwaltung.mitglied.valueobjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jle.exceptions.InvalidBirthdayException;

public class Geburtsdatum {

  private final String geburtsdatumString;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  public Geburtsdatum(String geburtsdatumString) {
    this.geburtsdatumString = validateBirthday(geburtsdatumString);
  }

  private String validateBirthday(String geburtstag) {
    if (isValidBirthday(geburtstag)) {
      return geburtstag;
    } else {
      throw new InvalidBirthdayException();
    }
  }

  private boolean isValidBirthday(String geburtstag) {
    dateFormat.setLenient(false);
    try {
      Date date = dateFormat.parse(geburtstag.trim());
      return date.before(new Date());
      //TODO umbauen auf java.time.api
    } catch (ParseException e) {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return geburtsdatumString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof Geburtsdatum) {
      Geburtsdatum geburtsdatum = (Geburtsdatum)obj;
      if (this.getGeburtsdatumString().equalsIgnoreCase(geburtsdatum.getGeburtsdatumString())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return geburtsdatumString;
  }

  public String getGeburtsdatumString() {
    return geburtsdatumString;
  }

}
