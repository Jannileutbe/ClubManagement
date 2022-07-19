package vereinsverwaltung.vereineSortieren;

import vereinsverwaltung.verein.Verein;

import java.util.Comparator;

public class VereineMitgliederAnzahlAbwaerts implements Comparator<Verein> {
    @Override
    public int compare(Verein vereinEins, Verein vereinZwei) {
        return Integer.valueOf(vereinZwei.getMitgliederliste().size()).compareTo(vereinEins.getMitgliederliste().size());
    }
}
