/*
 *
 * jle.vereinsverwaltung.vorstand.VorstandComparator
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

package jle.vereinsverwaltung.vorstand;

import java.util.Comparator;

public class VorstandComparator implements Comparator<Vorstand> {


  @Override
  public int compare(Vorstand executiveOne, Vorstand executiveTwo) {
    return Integer.compare(executiveOne.getVorstandsrolle().ordinal(), executiveTwo.getVorstandsrolle().ordinal());
  }
}
