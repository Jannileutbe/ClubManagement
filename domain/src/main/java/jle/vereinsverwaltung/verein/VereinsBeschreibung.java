/*
 *
 * jle.vereinsverwaltung.verein.VereinsBeschreibung
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

package jle.vereinsverwaltung.verein;

public class VereinsBeschreibung {

  private final String vereinsBeschreibungString;

  public VereinsBeschreibung(String vereinsBeschreibungString) {
    this.vereinsBeschreibungString = vereinsBeschreibungString;
  }

  @Override
  public int hashCode() {
    return vereinsBeschreibungString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof VereinsBeschreibung) {
      VereinsBeschreibung vereinsBeschreibung = (VereinsBeschreibung)obj;
      if (this.getVereinsBeschreibungString().equalsIgnoreCase(vereinsBeschreibung.getVereinsBeschreibungString())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return vereinsBeschreibungString;
  }

  public String getVereinsBeschreibungString() {
    return vereinsBeschreibungString;
  }

}
