/*
 *
 * jle.vereinsverwaltung.mitglied.valueobjects.Telefonnummer
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

import jle.exceptions.InvalidPhonenumberException;

public class Telefonnummer {

  private final String telefonnummerString;

  private final String regex = "[\\d()./ -]{3,15}";

  public Telefonnummer(String telefonnummerString) {
    this.telefonnummerString = validateNormalizePhoneNumber(telefonnummerString);
  }

  private String validateNormalizePhoneNumber(String phonenumber) {
    if (Pattern.compile(regex).matcher(phonenumber).matches()) {
      return phonenumber.replaceAll("[^0-9]", "");
    } else {
      throw new InvalidPhonenumberException();
    }
  }

  @Override
  public int hashCode() {
    return telefonnummerString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof Telefonnummer) {
      Telefonnummer telefonnummer = (Telefonnummer)obj;
      if (this.getTelefonnummerString().equalsIgnoreCase(telefonnummer.getTelefonnummerString())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return telefonnummerString;
  }

  public String getTelefonnummerString() {
    return telefonnummerString;
  }

}
