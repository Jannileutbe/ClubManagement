/*
 *
 * jle.vereinsverwaltung.vereinesortieren.VereineMitgliederAnzahlAufwaerts
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

package jle.vereinsverwaltung.vereinesortieren;

import java.util.Comparator;

import jle.vereinsverwaltung.verein.Verein;

public class VereineMitgliederAnzahlAufwaerts implements Comparator<Verein> {

  @Override
  public int compare(Verein vereinEins, Verein vereinZwei) {
    return Integer.compare(vereinEins.getMitgliederliste().size(), vereinZwei.getMitgliederliste().size());
  }
}
