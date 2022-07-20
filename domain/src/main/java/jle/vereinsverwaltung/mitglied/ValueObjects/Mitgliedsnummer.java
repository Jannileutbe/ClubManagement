package jle.vereinsverwaltung.mitglied.ValueObjects;

import java.util.regex.Pattern;

import jle.exceptions.InvalidMemberNumberException;

public class Mitgliedsnummer {

  private String mitgliedsnummer;
  private final String REGEX = "[\\dA-Z]{12}";

  //TODO auf einmaligkeit prüfen

  public Mitgliedsnummer(String memberNumber) {
    this.mitgliedsnummer = validateMemberNumber(memberNumber);
  }

  private String validateMemberNumber(String memberNumber) {
    if (Pattern.compile(REGEX).matcher(memberNumber).matches()) {
      return memberNumber;
    } else {
      throw new InvalidMemberNumberException();
    }
  }

  @Override
  public int hashCode() {
    return mitgliedsnummer.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Mitgliedsnummer) {
      Mitgliedsnummer mitgliedsnummer = (Mitgliedsnummer)obj;
      return this.getMitgliedsnummer().equalsIgnoreCase(mitgliedsnummer.getMitgliedsnummer());
    }
    return false;
  }

  @Override
  public String toString() {
    return mitgliedsnummer;
  }

  public String getMitgliedsnummer() {
    return mitgliedsnummer;
  }

}
