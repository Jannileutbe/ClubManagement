package jle.vereinsverwaltung.vereinesortieren;

import java.util.Comparator;

import jle.vereinsverwaltung.verein.Verein;

public class VereineMitgliederAnzahlAufwaerts implements Comparator<Verein> {

  @Override
  public int compare(Verein vereinEins, Verein vereinZwei) {
    return Integer.compare(vereinEins.getMitgliederliste().size(), vereinZwei.getMitgliederliste().size());
  }
}
