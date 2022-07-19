package vereinsverwaltung.mitglied.ValueObjects;

import java.util.regex.Pattern;

import exceptions.InvalidPhonenumberException;

public class Telefonnummer {

  private String telefonnummer;

  private final String REGEX = "[\\d()./ -]{3,15}";

  public Telefonnummer(String telefonnummer) {
    this.telefonnummer = validateNormalizePhoneNumber(telefonnummer);
  }

  private String validateNormalizePhoneNumber(String phonenumber) {
    if (Pattern.compile(REGEX).matcher(phonenumber).matches()) {
      return phonenumber.replaceAll("[^0-9]", "");
    } else {
      throw new InvalidPhonenumberException();
    }
  }

  @Override
  public int hashCode() {
    return telefonnummer.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof Telefonnummer) {
      Telefonnummer telefonnummer = (Telefonnummer)obj;
      if (this.getTelefonnummer().equalsIgnoreCase(telefonnummer.getTelefonnummer())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return telefonnummer;
  }

  public String getTelefonnummer() {
    return telefonnummer;
  }

}
