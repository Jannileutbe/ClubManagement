/*
 *
 * jle.vereinsverwaltung.mitglied.valueobjects.adresse.Postleitzahl
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

package jle.vereinsverwaltung.mitglied.valueobjects.adresse;

import java.util.regex.Pattern;

import jle.exceptions.InvalidPostalCodeException;

public class Postleitzahl {

  private final String postleitzahlString;

  private final String regex = "[\\p{N}]{5}";

  public Postleitzahl(String postalCode) {
    this.postleitzahlString = validateNormalizePostalCode(postalCode);
  }

  private String validateNormalizePostalCode(String postalCode) {
    if (Pattern.compile(regex).matcher(postalCode).matches()) {
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
      return this.getPostleitzahlString().equalsIgnoreCase(postleitzahl.getPostleitzahlString());
    }
    return false;
  }

  @Override
  public String toString() {
    return postleitzahlString;
  }

  public String getPostleitzahlString() {
    return postleitzahlString;
  }
}
