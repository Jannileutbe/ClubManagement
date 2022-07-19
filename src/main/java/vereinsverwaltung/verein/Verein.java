package vereinsverwaltung.verein;

import java.util.LinkedList;

import consoleinoutput.mybufferedreader.MyBufferedReader;
import vereinsverwaltung.mitglied.Mitglied;
import vereinsverwaltung.vorstand.VorstandComparator;
import vereinsverwaltung.bankverbindung.Bankverbindung;
import vereinsverwaltung.mitglied.ValueObjects.Mitgliedsnummer;
import vereinsverwaltung.vorstand.Vorstand;

public class Verein implements Comparable<Verein> {
  private VereinsName name;
  private VereinsBeschreibung beschreibung;
  private LinkedList<Bankverbindung> bankverbindungen;
  private LinkedList<Mitglied> mitgliederliste;
  private LinkedList<Vorstand> vorstandsrollen;

/* Vorstandrollenausgeben evtl.
  String kommissarischeRolle = "";
        if (this.getKommissarischeVorstandsrolle() != null) {
            kommissarischeRolle = this.getKommissarischeVorstandsrolle().getBezeichnung();
        }
        if (this.getVorstandsrolle() != null && this.getKommissarischeVorstandsrolle() != null) {
            System.out.print(BLUE + "| Vorstandrolle: " + vorstandsrolle + RESET + " | ");
            System.out.println(YELLOW + kommissarischeRolle + " |" + RESET);
        } else if (this.getVorstandsrolle() != null && this.getKommissarischeVorstandsrolle() == null) {
            System.out.println(BLUE + "| Vorstandrolle: " + vorstandsrolle + " |" + RESET);
        } else if (this.getVorstandsrolle() == null && this.getKommissarischeVorstandsrolle() != null) {
            System.out.println(YELLOW + "| " + kommissarischeRolle + " |" + RESET);
        }
  */

  public Verein(VereinsName name, VereinsBeschreibung beschreibung) {
    this.name = name;
    this.beschreibung = beschreibung;
    this.bankverbindungen = new LinkedList<Bankverbindung>();
    this.mitgliederliste = new LinkedList<Mitglied>();
    this.vorstandsrollen = new LinkedList<Vorstand>();
  }

  public Verein(VereinsName name, VereinsBeschreibung beschreibung, LinkedList<Mitglied> mitgliederliste) {
    this.name = name;
    this.beschreibung = beschreibung;
    this.mitgliederliste = mitgliederliste;
    this.bankverbindungen = new LinkedList<Bankverbindung>();
    this.vorstandsrollen = new LinkedList<Vorstand>();
  }

  public void addMitglied(Mitglied neuesMitglied) {
    boolean ok = false;
    while (!ok) {
      Mitgliedsnummer neuesMitgliedMitgliedsnummer = neuesMitglied.getMitgliedsnummer();
      if (!mitgliederliste.isEmpty()) {
        for (Mitglied mitglied : mitgliederliste) {
          if (mitglied.getMitgliedsnummer().equals(neuesMitgliedMitgliedsnummer)) {
            MyBufferedReader.printError("Diese Mitgliedsnummer ist bereits vergeben!");
            break;
          } else {
            ok = true;
          }
        }
      }
      ok = true;
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
    return "| Vereinsname: " + name + " | "
        + "Vereinsbeschreibung: " + beschreibung + " | "
        + "Mitgliederanzahl: " + mitgliederliste.size() + " |";
  }

  @Override
  public int compareTo(Verein verein) {
    return this.getName().getVereinsName().compareTo(verein.getName().getVereinsName());
  }
}
