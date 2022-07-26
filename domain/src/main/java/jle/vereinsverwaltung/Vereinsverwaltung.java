package jle.vereinsverwaltung;

import static consoleinoutput.inputoutput.ConsoleColors.BLUE;
import static consoleinoutput.inputoutput.ConsoleColors.RED;
import static consoleinoutput.inputoutput.ConsoleColors.RESET;
import static consoleinoutput.inputoutput.ConsoleColors.YELLOW;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

import consoleinoutput.mybufferedreader.MyBufferedReader;
import jle.exceptions.AddressAlreadyExistsException;
import jle.exceptions.ClubAlreadyExistsException;
import jle.exceptions.InvalidAddressLineException;
import jle.exceptions.InvalidBirthdayException;
import jle.exceptions.InvalidCityException;
import jle.exceptions.InvalidIbanException;
import jle.exceptions.InvalidPhonenumberException;
import jle.exceptions.InvalidPostalCodeException;
import consoleinoutput.exceptions.NotaNumberException;
import consoleinoutput.exceptions.NumberOutOfRangeException;
import jle.exceptions.PhoneNumberAlreadyExistsExeption;
import jle.importexport.CsvService;
import jle.vereinsverwaltung.bankverbindung.Bankverbindung;
import jle.vereinsverwaltung.bankverbindung.Iban;
import jle.vereinsverwaltung.mitglied.Mitglied;
import jle.vereinsverwaltung.mitglied.valueobjects.Adresse;
import jle.vereinsverwaltung.mitglied.valueobjects.Geburtsdatum;
import jle.vereinsverwaltung.mitglied.valueobjects.Mitgliedsnummer;
import jle.vereinsverwaltung.mitglied.valueobjects.Nachname;
import jle.vereinsverwaltung.mitglied.valueobjects.Telefonnummer;
import jle.vereinsverwaltung.mitglied.valueobjects.Vorname;
import jle.vereinsverwaltung.mitglied.valueobjects.adresse.Adresszeile;
import jle.vereinsverwaltung.mitglied.valueobjects.adresse.Ort;
import jle.vereinsverwaltung.mitglied.valueobjects.adresse.Postleitzahl;
import jle.vereinsverwaltung.verein.Verein;
import jle.vereinsverwaltung.verein.VereinsBeschreibung;
import jle.vereinsverwaltung.verein.VereinsName;
import jle.vereinsverwaltung.verein.VereinsParser;
import jle.vereinsverwaltung.verein.VereinsService;
import jle.vereinsverwaltung.vereinesortieren.VereineMitgliederAnzahlAbwaerts;
import jle.vereinsverwaltung.vereinesortieren.VereineMitgliederAnzahlAufwaerts;
import jle.vereinsverwaltung.vorstand.Vorstand;
import jle.vereinsverwaltung.vorstand.Vorstandsrollen;

public class Vereinsverwaltung {

  private static final int THREE = 3;
  private static final int FOUR = 4;
  private static final int FIVE = 5;
  private static final int SIX = 6;
  private static final int SEVEN = 7;
  private static final int EIGHT = 8;

  //<editor-fold desc="Message Properties because Checkstyle">

  private final String messages = "Messages";
  private final String faultyEntry = "faultyEntry";
  private final String notaNumberException = "notANumberException";
  private final String numberOutOfRangeException = "numberOutOfRangeException";
  private final String dontLeaveFieldEmpty = "dontLeaveFieldEmpty";
  private final String noExistingClubMembers = "noExistingClubMembers";
  private final String memberNumberAlreadyUsed = "memberNumberAlreadyUsed";
  private final String enterIban = "enterIban";
  private final String colon = ": ";
  private final String chooseClubMember = "chooseClubMember";
  private final String closeBraket = ")";
  private final String newAddressWithCurrent = "newAddressWithCurrent";
  private final String newPhoneNumber = "newPhoneNumber";
  private final String noClubMembersYet = "noClubMembersYet";
  private final String accountAlreadyExists = "accountAlreadyExists";
  private final String counterOne = "1: ";
  private final String counterTwo = "2: ";
  private final String counterThree = "3: ";
  private final String counterFour = "4: ";
  private final String letterv = "v";
  private final String fileNotFoundException = "fileNotFoundException";
  private final String ioException = "ioException";
  private final String exportImportFile = "domain/target/csvExport";

  //</editor-fold>

  private final VereinsService vereinsService;
  private final LinkedList<Verein> vereinsListe;
  private final ResourceBundle germanBundle = ResourceBundle.getBundle(messages, new Locale("de"));
  private final ResourceBundle englishBundle = ResourceBundle.getBundle(messages, new Locale("en"));
  private ResourceBundle selectedBundle;


  public Vereinsverwaltung() {
    this.vereinsListe = new LinkedList<>();
    this.vereinsService = new VereinsService();
    this.selectedBundle = germanBundle;
  }

  public ResourceBundle getSelectedBundle() {
    return selectedBundle;
  }

  public LinkedList<Verein> getVereinsListe() {
    return vereinsListe;
  }

  private boolean thisClubAlreadyExists(Verein neuerVerein) {
    boolean thisClubAlreadyExists = false;
    for (Verein verein : vereinsListe) {
      if (verein.getName().equals(neuerVerein.getName())) {
        thisClubAlreadyExists = true;
        break;
      }
    }
    return thisClubAlreadyExists;
  }

  public void vereinsListeBearbeiten() throws ClubAlreadyExistsException {
    MyBufferedReader.println(selectedBundle.getString("addOrEditClubOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    String noClubExistsException = "noClubExistsException";
    switch (eingabe) {
      case 1:
        Verein neuerVerein = vereinsService.createVerein(
            selectedBundle.getString("clubNameRules"), selectedBundle.getString("clubDescriptionRules"));
        if (thisClubAlreadyExists(neuerVerein)) {
          throw new ClubAlreadyExistsException();
        } else {
          vereinsListe.add(neuerVerein);
        }
        break;
      case 2:
        if (getVereinsListe().isEmpty()) {
          MyBufferedReader.printError(selectedBundle.getString(noClubExistsException));
        } else {
          vereinBearbeiten();
        }
        break;
      case THREE:
        if (getVereinsListe().isEmpty()) {
          MyBufferedReader.printError(selectedBundle.getString(noClubExistsException));
        } else {
          vereineAusgeben();
        }
        break;
      case FOUR:
        if (getVereinsListe().isEmpty()) {
          MyBufferedReader.printError(selectedBundle.getString(noClubExistsException));
        } else {
          vereinEntfernen();
        }
        break;
      case FIVE:
        vereineExportieren();
        break;
      case SIX:
        vereinslisteImportieren();
        break;
      case SEVEN:
        spracheWechseln();
        break;
      case EIGHT:
        System.exit(0);
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
    }
    //vereinAnlegenOderBearbeiten();
  }
  //<editor-fold desc="CASE 1 der Vereinslisten-Bearbeitung:">

  //Ein Verein wird der Vereins-Liste hinzugefügt

  // Die Methode prüft, ob es bereits einen Verein mit dem gleichen Namen gibt
  public boolean vereinSchonVorhanden(VereinsName vereinsName) {
    for (Verein verein : vereinsListe) {
      if (verein.getName().equals(vereinsName)) {
        return true;
      }
    }
    return false;
  }
  //</editor-fold>

  //<editor-fold desc="CASE 2 der Vereinslisten-Bearbeitung:">

  // Bearbeitung eines Vereins
  private void vereinBearbeiten() {
    Verein ausgewaehlterVerein = vereinAuswaehlen();
    String editClubOptions = "editClubOptions";
    MyBufferedReader.println(selectedBundle.getString(editClubOptions));
    // Der Switch wird so lange durchlaufen wie der Programmteil nicht aktiv durch eine Eingabe verlassen wird
    while (true) {
      int eingabe = MyBufferedReader.forceReadInInt();
      switch (eingabe) {
        case 1:
          vereinUmbenennen(ausgewaehlterVerein);
          break;
        case 2:
          beschreibungAendern(ausgewaehlterVerein);
          break;
        case THREE:
          mitgliederVerwalten(ausgewaehlterVerein);
          break;
        case FOUR:
          bankverbindungenBearbeiten(ausgewaehlterVerein);
          break;
        case FIVE:
          MyBufferedReader.println(selectedBundle.getString("onlyOnePersonPerExecutiveRole"), YELLOW);
          MyBufferedReader.println(selectedBundle.getString("whichPersonForExecutiveRole"));
          Mitglied ausgewaehltesMitglied = mitgliedAuswaehlen(ausgewaehlterVerein);
          vorstandsrolleZuweisen(ausgewaehltesMitglied);
          break;
        case SIX:
          vorstandsrollenAnzeigen(ausgewaehlterVerein);
          break;
        case SEVEN:
          return;
        default:
          MyBufferedReader.printInvalidInput();
          break;
      }
      // Am Ende jedes Cases muss einmal Enter gedrückt werden, um zur Bearbeitungsübersicht zurückzugelangen
      MyBufferedReader.println(selectedBundle.getString("backToEditingView"));
      MyBufferedReader.readInString();
      MyBufferedReader.println(selectedBundle.getString("selectedClubPrefix") + ausgewaehlterVerein.getName());
      MyBufferedReader.println(selectedBundle.getString(editClubOptions));
    }
  }

  //<editor-fold desc="Hilfsfunktionen vereinBearbeitet">

  // Gibt alle Vereine nummeriert aus
  private Verein vereinAuswaehlen() {
    Verein ausgewaehlterVerein;
    Collections.sort(vereinsListe);
    MyBufferedReader.println(selectedBundle.getString("chooseClub"));
    int i = 1;
    for (Verein verein : vereinsListe) {
      MyBufferedReader.println(i + ". " + verein.getName());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(vereinsListe.size());
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
      }
    }
    ausgewaehlterVerein = vereinsListe.get(userInput - 1);
    return ausgewaehlterVerein;
  }
  //</editor-fold>

  private void vereinUmbenennen(Verein ausgewaehlterVerein) {
    MyBufferedReader.println(selectedBundle.getString("selectedClubNamePrefix") + ausgewaehlterVerein.getName()
        + selectedBundle.getString("enterNewClubName"));
    VereinsName neuerName = new VereinsName(MyBufferedReader.readInString());
    if (vereinSchonVorhanden(neuerName)) {
      MyBufferedReader.printError(selectedBundle.getString("clubAlreadyExistsException"));
    } else if (!neuerName.getVereinsNameString().equals("")) {
      ausgewaehlterVerein.setName(neuerName);
    } else {
      MyBufferedReader.println(RED + selectedBundle.getString(dontLeaveFieldEmpty) + RESET);
    }
  }

  public void beschreibungAendern(Verein ausgewaehlterVerein) {
    // Die alte Beschreibung des Vereins wird ausgegeben und die neue wird dem Verein zugewiesen
    MyBufferedReader.println(selectedBundle.getString("clubDescriptionPrefix") + ausgewaehlterVerein.getBeschreibung()
        + selectedBundle.getString("enterNewClubDescription"));
    VereinsBeschreibung neueBeschreibung = new VereinsBeschreibung(MyBufferedReader.readInString());
    if (!neueBeschreibung.getVereinsBeschreibungString().equals("")) {
      ausgewaehlterVerein.setBeschreibung(neueBeschreibung);
    } else {
      MyBufferedReader.println(RED + selectedBundle.getString(dontLeaveFieldEmpty) + RESET);
    }
  }

  private void mitgliederVerwalten(Verein ausgewaehlterVerein) {
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(FOUR, selectedBundle.getString("editClubMemberListOptions"));
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(notaNumberException);
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(numberOutOfRangeException);
      }
    }
    Mitglied ausgewaehltesMitglied;
    switch (userInput) {
      case 1:
        Mitglied neuesMitglied = neuesMitgliedErstellen(ausgewaehlterVerein);
        ausgewaehlterVerein.addMitglied(neuesMitglied);
        break;
      case 2:
        if (ausgewaehlterVerein.getMitgliederliste().size() > 0) {
          ausgewaehltesMitglied = mitgliedAuswaehlen(ausgewaehlterVerein);
          mitgliedBearbeiten(ausgewaehltesMitglied, ausgewaehlterVerein);
        } else {
          MyBufferedReader.printError(selectedBundle.getString(noExistingClubMembers));
        }
        break;
      case THREE:
        mitgliederAusgeben(ausgewaehlterVerein);
        break;
      case FOUR:
        if (ausgewaehlterVerein.getMitgliederliste().size() > 0) {
          ausgewaehltesMitglied = mitgliedAuswaehlen(ausgewaehlterVerein);
          ausgewaehlterVerein.getMitgliederliste().remove(ausgewaehltesMitglied);
          for (int i = 0; i < ausgewaehlterVerein.getBankverbindungen().size(); i++) {
            if (ausgewaehlterVerein.getBankverbindungen().get(i).getKontoinhaber().equals(ausgewaehltesMitglied)) {
              MyBufferedReader.printError(selectedBundle.getString("memberIsClubBankAccountOwner"));
              break;
            }
          }
          for (Vorstand vorstand : ausgewaehlterVerein.getVorstandsrollen()) {
            if (vorstand.getMitglied().equals(ausgewaehltesMitglied)) {
              MyBufferedReader.printError(selectedBundle.getString("memberHasExecutiveRole"));
              String output = vorstand.toString();
              MyBufferedReader.println(output, BLUE);
              break;
            }
          }
          ausgewaehlterVerein.getMitgliederliste().remove(ausgewaehltesMitglied);
          MyBufferedReader.println(selectedBundle.getString("memberDeleted"));
        } else {
          MyBufferedReader.printError(selectedBundle.getString(noExistingClubMembers));
        }
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
    }
  }

  private boolean mitgliedSchonVorhanden(Mitgliedsnummer neueMitgliedsnummer, Verein ausgewaehlterVerein) {
    for (int i = 0; i < ausgewaehlterVerein.getMitgliederliste().size(); i++) {
      Mitgliedsnummer vorhandeneMitgliedsnummer = ausgewaehlterVerein.getMitgliederliste().get(i).getMitgliedsnummer();
      if (neueMitgliedsnummer.equals(vorhandeneMitgliedsnummer)) {
        return true;
      }
    }
    return false;
  }

  private Mitglied neuesMitgliedErstellen(Verein ausgewaehlterVerein) {
    MyBufferedReader.println(selectedBundle.getString("enterEveryFieldSeparately"));
    MyBufferedReader.println(selectedBundle.getString("memberNumberPrefix"));
    Mitgliedsnummer mitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
    // Es wird geprüft, ob die Mitgliedsnummers schon vergeben ist
    while (mitgliedSchonVorhanden(mitgliedsnummer, ausgewaehlterVerein)) {
      MyBufferedReader.println(RED + selectedBundle.getString(memberNumberAlreadyUsed));
      mitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
    }
    MyBufferedReader.println(selectedBundle.getString("forenamePrefix"));
    Vorname vorname = new Vorname(MyBufferedReader.readInString());
    MyBufferedReader.println(selectedBundle.getString("surnamePrefix"));
    Nachname nachname = new Nachname(MyBufferedReader.readInString());
    MyBufferedReader.println(selectedBundle.getString("birthdayPrefix"));
    Geburtsdatum geburtsdatum = entervalidBirthday();
    MyBufferedReader.println(selectedBundle.getString("addressPrefix"));
    Adresse adresse = enterValidAddress();
    MyBufferedReader.println(selectedBundle.getString("phoneNumberPrefix"));
    Telefonnummer telefonnummer = enterValidPhoneNumber();
    Mitglied neuesMitglied = new Mitglied(mitgliedsnummer, vorname, nachname, geburtsdatum, adresse, telefonnummer, ausgewaehlterVerein);
    MyBufferedReader.println(selectedBundle.getString("wantToAddBankDetails"));
    String eingabe = MyBufferedReader.readInString();
    if (eingabe.equalsIgnoreCase("j")) {
      gibMitgliedBankverbindung(neuesMitglied);
    }
    return neuesMitglied;
  }

  private Geburtsdatum entervalidBirthday() {
    while (true) {
      try {
        return new Geburtsdatum(MyBufferedReader.readInString());
      } catch (InvalidBirthdayException e) {
        MyBufferedReader.printError("This is not a valid birthday!");
      }
    }
  }

  private void gibMitgliedBankverbindung(Mitglied neuesMitglied) {
    MyBufferedReader.println(selectedBundle.getString(enterIban));
    Iban iban = enterValidIban();
    neuesMitglied.setBankverbindung(new Bankverbindung(neuesMitglied, iban));
  }

  // Methode genutzt in CASE 2 der Vereinslisten-Bearbeitung
  // Die Methode gibt eine nummerierte Liste der Mitglieder aus und gibt nach einer Eingabe ein Mitglied zurück
  private Mitglied mitgliedAuswaehlen(Verein ausgewaehlterVerein) {
    // Durchnummerierte Ausgabe der Mitglieder
    int nummerDesMiglieds = 1;
    for (Mitglied mitglied : ausgewaehlterVerein.getMitgliederliste()) {
      MyBufferedReader.println(nummerDesMiglieds + colon + mitglied.toString());
      nummerDesMiglieds++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehlterVerein.getMitgliederliste().size(),
            selectedBundle.getString(chooseClubMember));
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
      }
    }
    return ausgewaehlterVerein.getMitgliederliste().get(userInput - 1);
  }

  // Methode genutzt in CASE 2 der Vereinslisten-Bearbeitung
  // Die Methode bearbeitet ein Mitglied, indem neue Value-Objects zugewiesen werden
  private void mitgliedBearbeiten(Mitglied ausgewaehltesMitglied, Verein ausgewaehlterVerein) {
    MyBufferedReader.println(selectedBundle.getString("editClubMemberOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.println(selectedBundle.getString("newMemberNumber") + ausgewaehltesMitglied.getMitgliedsnummer() + closeBraket);
        MyBufferedReader.println(RED + selectedBundle.getString("noDoubleMemberNumber") + RESET);
        Mitgliedsnummer neueMitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
        while (mitgliedSchonVorhanden(neueMitgliedsnummer, ausgewaehlterVerein)) {
          MyBufferedReader.println(RED + selectedBundle.getString(memberNumberAlreadyUsed) + RESET);
          neueMitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
        }
        ausgewaehltesMitglied.setMitgliedsnummer(neueMitgliedsnummer);
        break;
      case 2:
        MyBufferedReader.println(selectedBundle.getString("newForename") + ausgewaehltesMitglied.getVorname() + closeBraket);
        Vorname neuerVorname = new Vorname(MyBufferedReader.readInString());
        ausgewaehltesMitglied.setVorname(neuerVorname);
        break;
      case THREE:
        MyBufferedReader.println(selectedBundle.getString("newSurname") + ausgewaehltesMitglied.getNachname() + closeBraket);
        Nachname neuerNachname = new Nachname(MyBufferedReader.readInString());
        ausgewaehltesMitglied.setNachname(neuerNachname);
        break;
      case FOUR:
        MyBufferedReader.println(selectedBundle.getString("newBirthday") + ausgewaehltesMitglied.getGeburtsdatum() + closeBraket);
        Geburtsdatum neuesGeburtsdatum = entervalidBirthday();
        ausgewaehltesMitglied.setGeburtsdatum(neuesGeburtsdatum);
        break;
      case FIVE:
        adressenBearbeiten(ausgewaehltesMitglied);
        break;
      case SIX:
        telefonnummernBearbeiten(ausgewaehltesMitglied);
        break;
      default:
        MyBufferedReader.printInvalidInput();
        break;
    }
  }

  private void mitgliederAusgeben(Verein ausgewaehlterVerein) {
    if (ausgewaehlterVerein.getMitgliederliste().size() == 0) {
      MyBufferedReader.printError(selectedBundle.getString(noExistingClubMembers));
    } else {
      MyBufferedReader.println(selectedBundle.getString("chooseMemberListOutput"));
      int eingabe = MyBufferedReader.forceReadInInt();
      switch (eingabe) {
        case 1:
          Collections.sort(ausgewaehlterVerein.getMitgliederliste());
          for (Mitglied mitglied : ausgewaehlterVerein.getMitgliederliste()) {
            MyBufferedReader.println(mitglied.toString());
          }
          break;
        case 2:
          Collections.reverse(ausgewaehlterVerein.getMitgliederliste());
          for (Mitglied mitglied : ausgewaehlterVerein.getMitgliederliste()) {
            MyBufferedReader.println(mitglied.toString());
          }
          break;
        default:
          MyBufferedReader.printInvalidInput();
      }
    }
  }

  //<editor-fold desc="Methoden zum bearbeiten der Adressen">
  // Methode leitet die Bearbeitung der Adressen ein
  private void adressenBearbeiten(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("currentAddressesPrefix") + ausgewaehltesMitglied.getAdressen());
    MyBufferedReader.println(selectedBundle.getString("editAddressesOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.println(selectedBundle.getString(newAddressWithCurrent) + ausgewaehltesMitglied.getAdressen() + closeBraket);
        Adresse neueAdresse;
        while (true) {
          try {
            neueAdresse = uniqueAddress(ausgewaehltesMitglied, enterValidAddress());
            break;
          } catch (AddressAlreadyExistsException e) {
            MyBufferedReader.printError("This address already exists for this Member");
          }
        }
        ausgewaehltesMitglied.getAdressen().add(neueAdresse);
        break;
      case 2:
        eineAdresseAendern(ausgewaehltesMitglied);
        break;
      case THREE:
        eineAdresseLoeschen(ausgewaehltesMitglied);
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
        break;
    }
  }

  //Methode zum ändern einer Adresse
  public void eineAdresseAendern(Mitglied ausgewaehltesMitglied) {
    if (ausgewaehltesMitglied.getAdressen().size() == 1) {
      MyBufferedReader.println(selectedBundle.getString(newAddressWithCurrent) + ausgewaehltesMitglied.getAdressen() + closeBraket);
      Adresse neueAdresse = enterValidAddress();
      ausgewaehltesMitglied.getAdressen().remove(0);
      ausgewaehltesMitglied.getAdressen().add(neueAdresse);
    } else {
      MyBufferedReader.println(selectedBundle.getString("chooseAddress"));
      int i = 1;
      for (Adresse adresse : ausgewaehltesMitglied.getAdressen()) {
        MyBufferedReader.println(i + colon + adresse.toString());
        i++;
      }
      int userInput;
      while (true) {
        try {
          userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getAdressen().size(), selectedBundle.getString(chooseClubMember));
          break;
        } catch (NotaNumberException e) {
          MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
        } catch (NumberOutOfRangeException e) {
          MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
        }
      }
      ausgewaehltesMitglied.getAdressen().add((userInput - 1), uniqueAddress(ausgewaehltesMitglied, enterValidAddress()));
      ausgewaehltesMitglied.getAdressen().remove(userInput);
    }
  }

  private void eineAdresseLoeschen(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("deleteAddress"));
    int i = 1;
    for (Adresse adresse : ausgewaehltesMitglied.getAdressen()) {
      MyBufferedReader.println(i + colon + adresse.toString());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getAdressen().size());
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
      }
    }
    ausgewaehltesMitglied.getAdressen().remove(userInput);
  }

  private Adresse enterValidAddress() {
    Adresszeile addressLineOne;
    Adresszeile addressLineTwo;
    Postleitzahl postalCode;
    Ort cityName;
    while (true) {
      try {
        addressLineOne = new Adresszeile(MyBufferedReader.readInString("Bitte gib die erste Adresszeile ein (Pflicht):"), false);
        break;
      } catch (InvalidAddressLineException e) {
        MyBufferedReader.printError("Not a valid address line one");
      }
    }
    while (true) {
      try {
        addressLineTwo = new Adresszeile(MyBufferedReader.readInString("Bitte gib die zweite Adresszeile ein (Optional):"), true);
        break;
      } catch (InvalidAddressLineException e) {
        MyBufferedReader.printError("Not a valid address line two");
      }
    }
    while (true) {
      try {
        postalCode = new Postleitzahl(MyBufferedReader.readInString("Bitte gib die Postleitzahl ein:"));
        break;
      } catch (InvalidPostalCodeException e) {
        MyBufferedReader.printError("Not a valid postal code");
      }
    }
    while (true) {
      try {
        cityName = new Ort(MyBufferedReader.readInString("Bitte gib den Stadtnamen ein:"));
        break;
      } catch (InvalidCityException e) {
        MyBufferedReader.printError("Not a valid city code");
      }
    }
    if (addressLineTwo.getAdressezeile() == null) {
      return new Adresse(addressLineOne, postalCode, cityName);
    } else {
      return new Adresse(addressLineOne, addressLineTwo, postalCode, cityName);
    }
  }

  private Adresse uniqueAddress(Mitglied mitglied, Adresse adresse) {
    for (Adresse existingAddress : mitglied.getAdressen()) {
      if (existingAddress.equals(adresse)) {
        throw new AddressAlreadyExistsException();
      }
    }
    return adresse;
  }
  //</editor-fold>

  //<editor-fold desc="Methoden zum bearbeiten der Telefonnummern">

  private void telefonnummernBearbeiten(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("currentPhoneNumber") + ausgewaehltesMitglied.getTelefonnummern()
        + selectedBundle.getString("editPhoneNumberOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.println(selectedBundle.getString(newPhoneNumber) + ausgewaehltesMitglied.getTelefonnummern() + closeBraket);
        Telefonnummer neueTelefonnummer;
        try {
          neueTelefonnummer = uniquePhoneNumber(ausgewaehltesMitglied, enterValidPhoneNumber());
        } catch (PhoneNumberAlreadyExistsExeption e) {
          MyBufferedReader.printError("Phone number already exists!");
          break;
        }
        ausgewaehltesMitglied.getTelefonnummern().add(neueTelefonnummer);
        break;
      case 2:
        eineTelefonnummerAendern(ausgewaehltesMitglied);
        break;
      case THREE:
        eineTelefonnummerLoeschen(ausgewaehltesMitglied);
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
        break;
    }
  }

  // Ändert eine Telefonnummer
  private void eineTelefonnummerAendern(Mitglied ausgewaehltesMitglied) {
    if (ausgewaehltesMitglied.getTelefonnummern().size() == 1) {
      MyBufferedReader.println(selectedBundle.getString(newPhoneNumber) + ausgewaehltesMitglied.getTelefonnummern() + closeBraket);
      Telefonnummer neueTelefonnummer = enterValidPhoneNumber();
      ausgewaehltesMitglied.getTelefonnummern().remove(0);
      ausgewaehltesMitglied.getTelefonnummern().add(neueTelefonnummer);
    } else {
      MyBufferedReader.println(selectedBundle.getString("chooseEditPhoneNumber"));
      int i = 1;
      for (Telefonnummer telefonnummer : ausgewaehltesMitglied.getTelefonnummern()) {
        MyBufferedReader.println(i + colon + telefonnummer.getTelefonnummerString());
        i++;
      }
      int userInput = -1;
      boolean ok = false;
      while (!ok) {
        try {
          userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getTelefonnummern().size());
          ok = true;
        } catch (NotaNumberException e) {
          MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
        } catch (NumberOutOfRangeException e) {
          MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
        }
      }
      MyBufferedReader.println("Please enter the changed phone number.");
      ausgewaehltesMitglied.getTelefonnummern().remove(userInput - 1);
      ausgewaehltesMitglied.getTelefonnummern().add((userInput - 1), uniquePhoneNumber(ausgewaehltesMitglied, enterValidPhoneNumber()));
    }
  }

  // Löscht eine Telefonnummer
  private void eineTelefonnummerLoeschen(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("chooseDeletePhoneNumber"));
    int i = 1;
    for (Telefonnummer telefonnummer : ausgewaehltesMitglied.getTelefonnummern()) {
      MyBufferedReader.println(i + colon + telefonnummer.getTelefonnummerString());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getTelefonnummern().size() - 1);
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
      }
    }
    ausgewaehltesMitglied.getTelefonnummern().remove(userInput);
  }

  private Telefonnummer enterValidPhoneNumber() {
    while (true) {
      try {
        return new Telefonnummer(MyBufferedReader.readInString());
      } catch (InvalidPhonenumberException e) {
        MyBufferedReader.printError("Not a valid phone number!");
      }
    }
  }

  private Telefonnummer uniquePhoneNumber(Mitglied mitglied, Telefonnummer telefonnummer) throws PhoneNumberAlreadyExistsExeption {
    for (Telefonnummer existingPhoneNumber : mitglied.getTelefonnummern()) {
      if (existingPhoneNumber.equals(telefonnummer)) {
        throw new PhoneNumberAlreadyExistsExeption();
      }
    }
    return telefonnummer;
  }
  //</editor-fold>

  //<editor-fold desc="Methoden zum Bearbeiten der Bankverbindungen">
  // Diese Methode leitet das Bearbeiten der Bankverbindungen ein
  public void bankverbindungenBearbeiten(Verein ausgewaehlterVerein) {
    if (ausgewaehlterVerein.getMitgliederliste().size() == 0) {
      MyBufferedReader.printError(selectedBundle.getString(noClubMembersYet));
    } else {
      MyBufferedReader.println(selectedBundle.getString("bankDetailsOptions"));
      int eingabe = MyBufferedReader.forceReadInInt();
      switch (eingabe) {
        case 1:
          addBankverbindung(ausgewaehlterVerein);
          break;
        case 2:
          eineBankverbindungBearbeiten(ausgewaehlterVerein);
          break;
        case THREE:
          bankverbindungEntfernen();
          break;
        default:
          MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
          break;
      }
    }
  }

  // Methode fügt einem Verein eine Bankverbindung hinzu
  private void addBankverbindung(Verein ausgewaehlterVerein) {
    MyBufferedReader.println(selectedBundle.getString("bankDetailsInfo"));
    MyBufferedReader.println(RED, selectedBundle.getString("bankDetailsRule"));
    MyBufferedReader.println(selectedBundle.getString("chooseAccountOwner"));
    Mitglied ausgewaehltesMitglied = mitgliedAuswaehlen(ausgewaehlterVerein);
    MyBufferedReader.println(selectedBundle.getString(enterIban));
    Iban ibanDesKontos = enterValidIban();
    if (ausgewaehlterVerein.getBankverbindungen().size() > 0) {
      while (bankverbindungSchonVorhanden(ibanDesKontos, ausgewaehlterVerein)) {
        MyBufferedReader.println(RED + selectedBundle.getString(accountAlreadyExists) + RESET);
        ibanDesKontos = new Iban(MyBufferedReader.readInString());
      }
    }
    ausgewaehlterVerein.getBankverbindungen().add(new Bankverbindung(ausgewaehltesMitglied, ibanDesKontos));
    MyBufferedReader.println(selectedBundle.getString("bankDetailsAdded"));
  }

  private Iban enterValidIban() {
    while (true) {
      try {
        return new Iban(MyBufferedReader.readInString());
      } catch (InvalidIbanException e) {
        MyBufferedReader.printError("The Iban you entered isn't correct");
      }
    }
  }

  private void eineBankverbindungBearbeiten(Verein ausgewaehlterVerein) {
    Bankverbindung ausgewaehlteBankverbindung = bankverbindungAuswaehlen(ausgewaehlterVerein);
    MyBufferedReader.println(selectedBundle.getString("whatShouldBeEdited"));
    MyBufferedReader.println(counterOne + selectedBundle.getString("accountOwnerPrefix") + ausgewaehlteBankverbindung.getKontoinhaber());
    MyBufferedReader.println(counterTwo + selectedBundle.getString("ibanPrefix") + ausgewaehlteBankverbindung.getIban());
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.println(selectedBundle.getString("newAccountOwner"));
        Mitglied neuerKontoinhaber = mitgliedAuswaehlen(ausgewaehlterVerein);
        ausgewaehlteBankverbindung.setKontoinhaber(neuerKontoinhaber);
        MyBufferedReader.println(selectedBundle.getString("accountOwnerChangeSuccessful"));
        break;
      case 2:
        MyBufferedReader.println(selectedBundle.getString("newIban"));
        Iban neueIban = enterValidIban();
        while (bankverbindungSchonVorhanden(neueIban, ausgewaehlterVerein)) {
          MyBufferedReader.println(RED + selectedBundle.getString(accountAlreadyExists));
          neueIban = new Iban(MyBufferedReader.readInString());
        }
        ausgewaehlteBankverbindung.setIban(neueIban);
        MyBufferedReader.println(selectedBundle.getString("accountIbanChangeSuccessful"));
        break;
      case 0:
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
    }
  }

  private Bankverbindung bankverbindungAuswaehlen(Verein ausgewaehlterVerein) {
    String semicolonWithLinebreak = ":\n";
    MyBufferedReader.println(selectedBundle.getString("currentAccountInfoOfClub") + ausgewaehlterVerein.getName() + semicolonWithLinebreak);
    for (int i = 0; i < ausgewaehlterVerein.getBankverbindungen().size(); i++) {
      MyBufferedReader.println(selectedBundle.getString("bankAccountPrefix") + (i + 1) + semicolonWithLinebreak
          + ausgewaehlterVerein.getBankverbindungen().get(i).toString());
    }
    MyBufferedReader.println(selectedBundle.getString("chooseAccount"));
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehlterVerein.getBankverbindungen().size());
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
      }
    }

    return ausgewaehlterVerein.getBankverbindungen().get(userInput);
  }

  // Wird bei der Bankverbindungserstellung und der Bearbeitung genutzt um zu prüfen, ob die Iban bereits existiert
  private boolean bankverbindungSchonVorhanden(Iban neueIban, Verein ausgewaehlterVerein) {
    boolean istgleich = false;
    for (int i = 0; i < ausgewaehlterVerein.getBankverbindungen().size(); i++) {
      Iban vorhandeneIban = ausgewaehlterVerein.getBankverbindungen().get(i).getIban();
      if (neueIban.equals(vorhandeneIban)) {
        istgleich = true;
        break;
      }
    }
    return istgleich;
  }

  private void bankverbindungEntfernen() {

  }
  //</editor-fold>


  //<editor-fold desc="Methoden zum Bearbeiten der Vorstandsrollen">

  // Start der Vorstandsrollen-Zuweisung

  private void vorstandsrolleZuweisen(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("executiveRoleCompleteOrSubstitutional"));
    String vollstaendigOderKommissarisch = MyBufferedReader.readInString();
    if (vollstaendigOderKommissarisch.equalsIgnoreCase(letterv) || vollstaendigOderKommissarisch.equalsIgnoreCase("k")) {
      vorstandAuswaehlen(ausgewaehltesMitglied, vollstaendigOderKommissarisch.toLowerCase());
    } else {
      MyBufferedReader.printError(selectedBundle.getString(faultyEntry));
    }

  }

  // Weißt die Vorstandrollen zu

  private void vorstandAuswaehlen(Mitglied ausgewaehltesMitglied, String vollstaendigOderKommissarisch) {
    if (vollstaendigOderKommissarisch.equals(letterv)) {
      assignExecutiveRole(ausgewaehltesMitglied);
    } else {
      assignSubstitutionalRole(ausgewaehltesMitglied);
    }
  }

  private void assignExecutiveRole(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("chooseExecutiveRole"));
    MyBufferedReader.println(counterOne + Vorstandsrollen.ERSTER_VORSITZENDER);
    MyBufferedReader.println(counterTwo + Vorstandsrollen.ZWEITER_VORSITZENDER);
    MyBufferedReader.println(counterThree + Vorstandsrollen.SCHRIFTFUEHRER);
    MyBufferedReader.println(counterFour + Vorstandsrollen.KASSENWART);
    Vorstand newRole;
    int userInput = MyBufferedReader.forceReadInInt();
    switch (userInput) {
      case 1:
        if (ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.ERSTER_VORSITZENDER);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.ERSTER_VORSITZENDER);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case 2:
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.ZWEITER_VORSITZENDER);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.ZWEITER_VORSITZENDER);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case THREE:
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.SCHRIFTFUEHRER);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.SCHRIFTFUEHRER);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case FOUR:
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.KASSENWART);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.KASSENWART);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      default:
        MyBufferedReader.printInvalidInput();
    }
  }

  private void assignSubstitutionalRole(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.println(selectedBundle.getString("chooseSubstitutionalRole"));
    MyBufferedReader.println(counterOne + Vorstandsrollen.ERSTER_VORSITZENDER_KOMMISSARISCH);
    MyBufferedReader.println(counterTwo + Vorstandsrollen.ZWEITER_VORSITZENDER_KOMMISSARISCH);
    MyBufferedReader.println(counterThree + Vorstandsrollen.SCHRIFTFUEHRER_KOMMISSARISCH);
    MyBufferedReader.println(counterFour + Vorstandsrollen.KASSENWART_KOMMISSARISCH);
    Vorstand newRole;
    int userInput = MyBufferedReader.forceReadInInt();
    switch (userInput) {
      case 1:
        if (ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.ERSTER_VORSITZENDER_KOMMISSARISCH);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.ERSTER_VORSITZENDER_KOMMISSARISCH);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case 2:
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.ZWEITER_VORSITZENDER_KOMMISSARISCH);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.ZWEITER_VORSITZENDER_KOMMISSARISCH);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case THREE:
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.SCHRIFTFUEHRER_KOMMISSARISCH);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.SCHRIFTFUEHRER_KOMMISSARISCH);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case FOUR:
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, Vorstandsrollen.KASSENWART_KOMMISSARISCH);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, Vorstandsrollen.KASSENWART_KOMMISSARISCH);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      default:
        MyBufferedReader.printInvalidInput();
    }
  }

  // Entfernt eine bestimmte Vorstandrolle von einem Mitglied
  private void vorstandsrolleEntfernen(Mitglied ausgewaehltesMitglied, Vorstandsrollen ausgewaehlteVorstandrolle) {
    Verein chosenClub = ausgewaehltesMitglied.getVerein();
    LinkedList<Vorstand> vorstandsrollenList = chosenClub.getVorstandsrollen();
    vorstandsrollenList.removeIf(vorstand -> vorstand.getVorstandsrolle() == ausgewaehlteVorstandrolle);
  }

  // Zeigt alle Vorstände an
  private void vorstandsrollenAnzeigen(Verein ausgewaehlterVerein) {
    if (ausgewaehlterVerein.getMitgliederliste().isEmpty()) {
      MyBufferedReader.printError(selectedBundle.getString(noClubMembersYet));
    }
    for (Vorstand vorstand : ausgewaehlterVerein.getVorstandsrollen()) {
      if (vorstand.getVorstandsrolle().equals(Vorstandsrollen.ERSTER_VORSITZENDER)
          || vorstand.getVorstandsrolle().equals(Vorstandsrollen.ZWEITER_VORSITZENDER)
          || vorstand.getVorstandsrolle().equals(Vorstandsrollen.SCHRIFTFUEHRER)
          || vorstand.getVorstandsrolle().equals(Vorstandsrollen.KASSENWART)) {
        MyBufferedReader.println(vorstand.toString(), BLUE);
      } else {
        MyBufferedReader.println(vorstand.toString(), YELLOW);
      }
    }

  }
  //</editor-fold>


  //</editor-fold>

  //<editor-fold desc="CASE 3 der Vereinslisten-Bearbeitung:">
  // Gibt die Vereine sortiert aus
  private void vereineAusgeben() {
    MyBufferedReader.println(selectedBundle.getString("printClubListOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        Collections.sort(getVereinsListe());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.println(verein.toString());
        }
        break;
      case 2:
        Collections.reverse(getVereinsListe());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.println(verein.toString());
        }
        break;
      case THREE:
        getVereinsListe().sort(new VereineMitgliederAnzahlAufwaerts());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.println(verein.toString());
        }
        break;
      case FOUR:
        getVereinsListe().sort(new VereineMitgliederAnzahlAbwaerts());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.println(verein.toString());
        }
        break;
      default:
        MyBufferedReader.printInvalidInput();
    }
  }
  //</editor-fold>

  //<editor-fold desc="CASE 4 der Vereinslisten-Bearbeitung:">
  // Entfernt einen Verein
  private void vereinEntfernen() {
    Collections.sort(getVereinsListe());
    MyBufferedReader.println(selectedBundle.getString("chooseClubToDelete"));
    int i = 1;
    for (Verein verein : getVereinsListe()) {
      MyBufferedReader.println(i + colon + verein.toString());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(getVereinsListe().size());
        ok = true;
      } catch (NotaNumberException e) {
        MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
      }
    }
    getVereinsListe().remove(userInput - 1);
    MyBufferedReader.println(selectedBundle.getString("clubDeletedSuccess"));
  }

  private void vereineExportieren() {
    //TODO Export anpassen an neue Klassen
    //String exportDirectoryString = MyBufferedReader.readInString(selectedBundle.getString("enterExportDirectoryPath"));
    //String exportFileNameString = MyBufferedReader.readInString(selectedBundle.getString("enterExportFileName"));
    //File exportDirectory = new File(exportDirectoryString);

    File exportDirectory = new File(exportImportFile);
    String exportFileName = "clubManagementExport";

    MyBufferedReader.println(exportDirectory.getAbsolutePath());

    CsvService exportCsvService = new CsvService(this.vereinsListe, exportDirectory.getPath(), exportFileName + ".csv");
    try {
      exportCsvService.exportClubListToCsv(new VereinsParser());
      MyBufferedReader.println(selectedBundle.getString("exportSuccessful"));
    } catch (FileNotFoundException e) {
      MyBufferedReader.printError(selectedBundle.getString(fileNotFoundException));
    } catch (IOException e) {
      MyBufferedReader.printError(selectedBundle.getString(ioException));
    }
  }

  private void vereinslisteImportieren() {
    //TODO Import reparieren
    //String importDirectoryString = MyBufferedReader.readInString(selectedBundle.getString("enterImportDirectoryPath"));
    //String exportFileNameString = MyBufferedReader.readInString(selectedBundle.getString("enterImportFileName"));
    //File importDirectory = new File(importDirectoryString);

    File importDirectory = new File(exportImportFile);
    String importFilename = "clubManagementExport.csv";
    CsvService importCsvService = new CsvService(this.vereinsListe, importDirectory.getPath(), importFilename);
    LinkedList<Verein> clubList;
    try {
      clubList = importCsvService.importClubToSystem();
      this.vereinsListe.addAll(clubList);
      MyBufferedReader.println(selectedBundle.getString("importSuccessful"));
    } catch (FileNotFoundException e) {
      MyBufferedReader.printError(fileNotFoundException);
    } catch (IOException e) {
      MyBufferedReader.printError(selectedBundle.getString(ioException));
    }
  }

  private void spracheWechseln() {
    try {
      MyBufferedReader.println(selectedBundle.getString("chooseLanguage"));
      int selectedLanguage = MyBufferedReader.readInInt(2);
      if (selectedLanguage == 1) {
        this.selectedBundle = this.germanBundle;
      } else if (selectedLanguage == 2) {
        this.selectedBundle = this.englishBundle;
      } else {
        MyBufferedReader.printError(selectedBundle.getString("chooseLanguageError"));
      }
    } catch (NotaNumberException e) {
      MyBufferedReader.printError(selectedBundle.getString(notaNumberException));
    } catch (NumberOutOfRangeException e) {
      MyBufferedReader.printError(selectedBundle.getString(numberOutOfRangeException));
    }
  }

  //</editor-fold>
}
