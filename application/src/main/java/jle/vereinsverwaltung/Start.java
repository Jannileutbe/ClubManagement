/*
 *
 * jle.vereinsverwaltung.Start
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

package jle.vereinsverwaltung;

import consoleinoutput.mybufferedreader.MyBufferedReader;
import jle.exceptions.ClubAlreadyExistsException;

public class Start {

  private static final int STARTVARIABLE = 0;

  public static void main(String[] args) {
    Vereinsverwaltung vereinsverwaltung = new Vereinsverwaltung();
    if (STARTVARIABLE == 1) {
      ClubTestDataCreator.createTestData(vereinsverwaltung);
    }
    while (true) {
      try {
        vereinsverwaltung.vereinsListeBearbeiten();
      } catch (ClubAlreadyExistsException e) {
        MyBufferedReader.printError(vereinsverwaltung.getSelectedBundle().getString("clubAlreadyExistsException"));
        MyBufferedReader.readInString();
      }
    }
  }
}
