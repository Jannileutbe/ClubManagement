package jle.vereinsverwaltung;

import jle.consoleinoutput.mybufferedreader.MyBufferedReader;
import jle.exceptions.ClubAlreadyExistsException;
import jle.vereinsverwaltung.verein.ClubTestDataCreator;

public class Start {

  private static final int STARTVARIABLE = 1;

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
