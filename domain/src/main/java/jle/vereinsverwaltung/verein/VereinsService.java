/*
 *
 * jle.vereinsverwaltung.verein.VereinsService
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

public class VereinsService {

  public Verein createVerein(VereinsName name, VereinsBeschreibung beschreibung) {
    return new Verein(name, beschreibung);
  }

}
