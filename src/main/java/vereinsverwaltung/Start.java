package vereinsverwaltung;

import java.util.Arrays;
import java.util.LinkedList;

import consoleinoutput.mybufferedreader.MyBufferedReader;
import exceptions.ClubAlreadyExistsException;
import vereinsverwaltung.mitglied.Mitglied;
import vereinsverwaltung.mitglied.ValueObjects.Adresse;
import vereinsverwaltung.mitglied.ValueObjects.Geburtsdatum;
import vereinsverwaltung.mitglied.ValueObjects.Mitgliedsnummer;
import vereinsverwaltung.mitglied.ValueObjects.Nachname;
import vereinsverwaltung.mitglied.ValueObjects.Telefonnummer;
import vereinsverwaltung.mitglied.ValueObjects.Vorname;
import vereinsverwaltung.mitglied.ValueObjects.adresse.Adresszeile;
import vereinsverwaltung.mitglied.ValueObjects.adresse.Ort;
import vereinsverwaltung.mitglied.ValueObjects.adresse.Postleitzahl;
import vereinsverwaltung.verein.Verein;
import vereinsverwaltung.verein.VereinsBeschreibung;
import vereinsverwaltung.verein.VereinsName;
import vereinsverwaltung.vorstand.Vorstand;
import vereinsverwaltung.vorstand.Vorstandsrollen;

public class Start {

  private static final int startvariable = 1;

  public static void main(String[] args) {
    Vereinsverwaltung vereinsverwaltung = new Vereinsverwaltung();
    if (startvariable == 1) {
      setStartingVariables(vereinsverwaltung);
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

  private static void setStartingVariables(Vereinsverwaltung vereinsverwaltung) {
    LinkedList<Verein> vereinsListe = vereinsverwaltung.getVereinsListe();
    Verein otb = new Verein(new VereinsName("Oldenburger Turnerbund"), new VereinsBeschreibung("Wir tragen rot!"));
    otb.getMitgliederliste().add(new Mitglied(
        new Mitgliedsnummer("000000000001"),
        new Vorname("Nicole"),
        new Nachname("Oberfrau"),
        new Geburtsdatum("23-04-2002"),
        new LinkedList<Adresse>(Arrays.asList(new Adresse(new Adresszeile("Irgendwie 1", false),
            new Adresszeile("", true), new Postleitzahl("26132"), new Ort("Oldenburg")))),
        new LinkedList<Telefonnummer>(Arrays.asList(new Telefonnummer("0441928465"), new Telefonnummer("017489023472")))));
    otb.getMitgliederliste().add(new Mitglied(
        new Mitgliedsnummer("000000000002"),
        new Vorname("Jonas"),
        new Nachname("Obermann"),
        new Geburtsdatum("30-06-1996"),
        new LinkedList<Adresse>(Arrays.asList(new Adresse(new Adresszeile("Irgendwo 2", false),
            new Adresszeile("", true), new Postleitzahl("26122"), new Ort("Oldenburg")))),
        new LinkedList<Telefonnummer>(Arrays.asList(new Telefonnummer("0441398465"), new Telefonnummer("01741783957")))));
    otb.getMitgliederliste().add(new Mitglied(
        new Mitgliedsnummer("000000000003"),
        new Vorname("Marjan"),
        new Nachname("Wokeman"),
        new Geburtsdatum("12-09-1995"),
        new LinkedList<Adresse>(Arrays.asList(new Adresse(new Adresszeile("Irgendwann 3", false),
            new Adresszeile("", true), new Postleitzahl("26123"), new Ort("Oldenburg")))),
        new LinkedList<Telefonnummer>(Arrays.asList(new Telefonnummer("0441176243"), new Telefonnummer("0174035867223")))));
    Vorstand ersterVorstandOtb = new Vorstand(otb.getMitgliederliste().get(0), Vorstandsrollen.ERSTER_VORSITZENDER);
    Vorstand zweiterVorstandOtb = new Vorstand(otb.getMitgliederliste().get(0), Vorstandsrollen.ZWEITER_VORSITZENDER);
    otb.getVorstandsrollen().add(ersterVorstandOtb);
    otb.getVorstandsrollen().add(zweiterVorstandOtb);

    Verein vfb = new Verein(new VereinsName("VFB Oldenburg"), new VereinsBeschreibung("Wir spielen Fußball!"));
    vfb.getMitgliederliste().add(new Mitglied(
        new Mitgliedsnummer("000000000001"),
        new Vorname("Steffen"),
        new Nachname("Piepen"),
        new Geburtsdatum("12-11-1991"),
        new LinkedList<Adresse>(Arrays.asList(new Adresse(new Adresszeile("Irgendwie 1", false),
            new Adresszeile("", true), new Postleitzahl("26132"), new Ort("Oldenburg")))),
        new LinkedList<Telefonnummer>(Arrays.asList(new Telefonnummer("0441928465"), new Telefonnummer("017489023472")))));
    vfb.getMitgliederliste().add(new Mitglied(
        new Mitgliedsnummer("000000000002"),
        new Vorname("Jan"),
        new Nachname("Wuschel"),
        new Geburtsdatum("11-02-1998"),
        new LinkedList<Adresse>(Arrays.asList(new Adresse(new Adresszeile("Irgendwo 2", false),
            new Adresszeile("", true), new Postleitzahl("26122"), new Ort("Oldenburg")))),
        new LinkedList<Telefonnummer>(Arrays.asList(new Telefonnummer("0441398465"), new Telefonnummer("01741783957")))));
    vfb.getMitgliederliste().add(new Mitglied(
        new Mitgliedsnummer("000000000003"),
        new Vorname("Ahmad"),
        new Nachname("Smoker"),
        new Geburtsdatum("08-10-1998"),
        new LinkedList<Adresse>(Arrays.asList(new Adresse(new Adresszeile("Irgendwann 3", false),
            new Adresszeile("", true), new Postleitzahl("26123"), new Ort("Oldenburg")))),
        new LinkedList<Telefonnummer>(Arrays.asList(new Telefonnummer("0441176243"), new Telefonnummer("0174035867223")))));
    Vorstand ersterVorstand = new Vorstand(otb.getMitgliederliste().get(0), Vorstandsrollen.ERSTER_VORSITZENDER);
    Vorstand zweiterVorstand = new Vorstand(otb.getMitgliederliste().get(0), Vorstandsrollen.ZWEITER_VORSITZENDER);
    otb.getVorstandsrollen().add(ersterVorstand);
    otb.getVorstandsrollen().add(zweiterVorstand);

    vereinsListe.add(otb);
    vereinsListe.add(vfb);
  }
}
