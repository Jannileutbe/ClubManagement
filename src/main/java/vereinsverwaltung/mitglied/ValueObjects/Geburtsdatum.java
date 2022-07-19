package vereinsverwaltung.mitglied.ValueObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.InvalidBirthdayException;

public class Geburtsdatum {

  private String geburtsdatum;
  private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  public Geburtsdatum(String geburtsdatum) {
    this.geburtsdatum = validateBirthday(geburtsdatum);
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
    return geburtsdatum.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof Geburtsdatum) {
      Geburtsdatum geburtsdatum = (Geburtsdatum)obj;
      if (this.getGeburtsdatum().toLowerCase().equals(geburtsdatum.getGeburtsdatum().toLowerCase())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return geburtsdatum;
  }

  public String getGeburtsdatum() {
    return geburtsdatum;
  }

}
