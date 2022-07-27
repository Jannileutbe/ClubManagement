/*
 *
 * jle.vereinsverwaltung.mitglied.valueobjects.Mitgliedsnummer
 *
 *
 * This document contains trade secret data which is the property of
 * OpenKnowledge GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from open knowledge GmbH.
 *
 * Copyright (C) {YEAR} open knowledge GmbH / Oldenburg / Germany
 *
 */

package jle.vereinsverwaltung.mitglied.valueobjects;

import java.util.regex.Pattern;

import jle.exceptions.InvalidMemberNumberException;

public class Mitgliedsnummer {

  private final String mitgliedsnummerString;
  private final String regex = "[\\dA-Z]{12}";

  //TODO auf einmaligkeit prüfen

  public Mitgliedsnummer(String memberNumber) {
    this.mitgliedsnummerString = validateMemberNumber(memberNumber);
  }

  private String validateMemberNumber(String memberNumber) {
    if (Pattern.compile(regex).matcher(memberNumber).matches()) {
      return memberNumber;
    } else {
      throw new InvalidMemberNumberException();
    }
  }

  @Override
  public int hashCode() {
    return mitgliedsnummerString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Mitgliedsnummer) {
      Mitgliedsnummer mitgliedsnummer = (Mitgliedsnummer)obj;
      return this.getMitgliedsnummerString().equalsIgnoreCase(mitgliedsnummer.getMitgliedsnummerString());
    }
    return false;
  }

  @Override
  public String toString() {
    return mitgliedsnummerString;
  }

  public String getMitgliedsnummerString() {
    return mitgliedsnummerString;
  }

}
