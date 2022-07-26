package jle.vereinsverwaltung.verein;

import java.util.LinkedList;

import jle.vereinsverwaltung.bankverbindung.Bankverbindung;
import jle.vereinsverwaltung.mitglied.Mitglied;
import jle.vereinsverwaltung.mitglied.valueobjects.Mitgliedsnummer;
import jle.vereinsverwaltung.vorstand.Vorstand;
import jle.vereinsverwaltung.vorstand.VorstandComparator;

public class Verein implements Comparable<Verein> {

  private VereinsName name;
  private VereinsBeschreibung beschreibung;
  private final LinkedList<Bankverbindung> bankverbindungen;
  private final LinkedList<Mitglied> mitgliederliste;
  private final LinkedList<Vorstand> vorstandsrollen;

  public Verein(VereinsName name, VereinsBeschreibung beschreibung) {
    this.name = name;
    this.beschreibung = beschreibung;
    this.bankverbindungen = new LinkedList<>();
    this.mitgliederliste = new LinkedList<>();
    this.vorstandsrollen = new LinkedList<>();
  }

  public Verein(VereinsName name, VereinsBeschreibung beschreibung, LinkedList<Mitglied> mitgliederliste) {
    this.name = name;
    this.beschreibung = beschreibung;
    this.mitgliederliste = mitgliederliste;
    this.bankverbindungen = new LinkedList<>();
    this.vorstandsrollen = new LinkedList<>();
  }

  public void addMitglied(Mitglied neuesMitglied) {
      Mitgliedsnummer neuesMitgliedMitgliedsnummer = neuesMitglied.getMitgliedsnummer();
      if (!mitgliederliste.isEmpty()) {
        for (Mitglied mitglied : mitgliederliste) {
          if (mitglied.getMitgliedsnummer().equals(neuesMitgliedMitgliedsnummer)) {
            //TODO Number already exists exception
            throw new IllegalArgumentException();
          }
        }
      }
    mitgliederliste.add(neuesMitglied);
  }

  public VereinsName getName() {
    return name;
  }

  public VereinsBeschreibung getBeschreibung() {
    return beschreibung;
  }

  public LinkedList<Bankverbindung> getBankverbindungen() {
    return bankverbindungen;
  }

  public LinkedList<Mitglied> getMitgliederliste() {
    return mitgliederliste;
  }

  public void setName(VereinsName name) {
    this.name = name;
  }

  public void setBeschreibung(VereinsBeschreibung beschreibung) {
    this.beschreibung = beschreibung;
  }

  public LinkedList<Vorstand> getVorstandsrollen() {
    vorstandsrollen.sort(new VorstandComparator());
    return vorstandsrollen;
  }

  @Override
  public String toString() {
    String separator = " | ";
    return "| Vereinsname: " + name + separator
        + "Vereinsbeschreibung: " + beschreibung + separator
        + "Mitgliederanzahl: " + mitgliederliste.size() + " |";
  }

  @Override
  public int compareTo(Verein verein) {
    return this.getName().getVereinsNameString().compareTo(verein.getName().getVereinsNameString());
  }
}
