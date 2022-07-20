package jle.vereinsverwaltung.vereineSortieren;

import jle.vereinsverwaltung.verein.Verein;
import java.util.Comparator;

public class VereineMitgliederAnzahlAufwaerts implements Comparator<Verein> {
    @Override
    public int compare(Verein vereinEins, Verein vereinZwei) {
        return Integer.valueOf(vereinEins.getMitgliederliste().size()).compareTo(vereinZwei.getMitgliederliste().size());
    }
}
