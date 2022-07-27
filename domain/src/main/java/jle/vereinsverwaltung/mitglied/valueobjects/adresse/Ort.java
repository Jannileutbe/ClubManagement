/*
 *
 * jle.vereinsverwaltung.mitglied.valueobjects.adresse.Ort
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
