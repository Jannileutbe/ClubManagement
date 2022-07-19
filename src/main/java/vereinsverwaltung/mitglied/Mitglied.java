package vereinsverwaltung.mitglied;

import java.util.LinkedList;

import vereinsverwaltung.bankverbindung.Bankverbindung;
import vereinsverwaltung.mitglied.ValueObjects.Adresse;
import vereinsverwaltung.mitglied.ValueObjects.Geburtsdatum;
import vereinsverwaltung.mitglied.ValueObjects.Mitgliedsnummer;
import vereinsverwaltung.mitglied.ValueObjects.Nachname;
import vereinsverwaltung.mitglied.ValueObjects.Telefonnummer;
import vereinsverwaltung.mitglied.ValueObjects.Vorname;
import vereinsverwaltung.verein.Verein;

public class Mitglied implements Comparable<Mitglied> {
    private Mitgliedsnummer mitgliedsnummer;
    private Vorname vorname;
    private Nachname nachname;
    private Geburtsdatum geburtsdatum;
    private LinkedList<Adresse> adressen;
    private LinkedList<Telefonnummer> telefonnummern;
    private Bankverbindung bankverbindung;
    private Verein verein;

    public Mitglied(Mitgliedsnummer mitgliedsnummer, Vorname vorname, Nachname nachname, Geburtsdatum geburtsdatum, Adresse adressen, Telefonnummer telefonnummern, Verein verein) {
        this.mitgliedsnummer = mitgliedsnummer;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.adressen = new LinkedList<Adresse>();
        this.adressen.add(adressen);
        this.telefonnummern = new LinkedList<Telefonnummer>();
        this.telefonnummern.add(telefonnummern);
        this.verein = verein;
    }

    public Mitglied(Mitgliedsnummer mitgliedsnummer, Vorname vorname, Nachname nachname, Geburtsdatum geburtsdatum, LinkedList<Adresse> adressen, LinkedList<Telefonnummer> telefonnummern) {
        this.mitgliedsnummer = mitgliedsnummer;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.adressen = adressen;
        this.telefonnummern = telefonnummern;
    }

    @Override
    public String toString() {
        return "| Mitgliedsnummer: " + mitgliedsnummer + " | "
                + "Name: " + nachname + ", " + vorname + " | "
                + "Geburtsdatum: " + geburtsdatum + " | "
                + "Adresse: " + adressen + " | "
                + "Telefon: " + telefonnummern + " |";
    }

    public Mitgliedsnummer getMitgliedsnummer() {
        return mitgliedsnummer;
    }

    public void setMitgliedsnummer(Mitgliedsnummer mitgliedsnummer) {
        this.mitgliedsnummer = mitgliedsnummer;
    }

    public Vorname getVorname() {
        return vorname;
    }

    public void setVorname(Vorname vorname) {
        this.vorname = vorname;
    }

    public Nachname getNachname() {
        return nachname;
    }

    public void setNachname(Nachname nachname) {
        this.nachname = nachname;
    }

    public Geburtsdatum getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Geburtsdatum geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public LinkedList<Adresse> getAdressen() {
        return adressen;
    }

    public LinkedList<Telefonnummer> getTelefonnummern() {
        return telefonnummern;
    }

    public Bankverbindung getBankverbindung() {
        return bankverbindung;
    }

    public void setBankverbindung(Bankverbindung bankverbindung) {
        this.bankverbindung = bankverbindung;
    }

    public Verein getVerein() {
        return verein;
    }

    public void setVerein(Verein verein) {
        this.verein = verein;
    }

    @Override
    public int compareTo(Mitglied mitglied) {
        if (this.getNachname().getNachname().compareTo(mitglied.getNachname().getNachname()) == 0) {
            return this.getVorname().getVorname().compareTo(mitglied.getVorname().getVorname());
        }
        return this.getNachname().getNachname().compareTo(mitglied.getNachname().getNachname());
    }
}
