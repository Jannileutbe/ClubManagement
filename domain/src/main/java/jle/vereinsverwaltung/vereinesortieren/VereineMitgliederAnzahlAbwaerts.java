package jle.vereinsverwaltung.vereinesortieren;

import java.util.Comparator;

import jle.vereinsverwaltung.verein.Verein;

public class VereineMitgliederAnzahlAbwaerts implements Comparator<Verein> {

  @Override
  public int compare(Verein vereinEins, Verein vereinZwei) {
    return Integer.compare(vereinZwei.getMitgliederliste().size(), vereinEins.getMitgliederliste().size());
  }
}
