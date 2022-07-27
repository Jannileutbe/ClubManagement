/*
 *
 * jle.vereinsverwaltung.verein.VereinsName
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

public class VereinsName {

  private final String vereinsNameString;

  public VereinsName(String vereinsNameString) {
    this.vereinsNameString = vereinsNameString;
  }

  @Override
  public int hashCode() {

    return vereinsNameString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof VereinsName) {
      VereinsName vereinsName = (VereinsName)obj;
      if (this.getVereinsNameString().equalsIgnoreCase(vereinsName.getVereinsNameString())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return vereinsNameString;
  }

  public String getVereinsNameString() {
    return vereinsNameString;
  }
}
