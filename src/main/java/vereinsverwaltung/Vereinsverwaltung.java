package vereinsverwaltung;

import static consoleinoutput.inputoutput.ConsoleColors.BLUE;
import static consoleinoutput.inputoutput.ConsoleColors.RED;
import static consoleinoutput.inputoutput.ConsoleColors.RESET;
import static consoleinoutput.inputoutput.ConsoleColors.YELLOW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

import consoleinoutput.mybufferedreader.MyBufferedReader;
import exceptions.AddressAlreadyExistsException;
import exceptions.ClubAlreadyExistsException;
import exceptions.InvalidAddressLineException;
import exceptions.InvalidBirthdayException;
import exceptions.InvalidCityException;
import exceptions.InvalidIbanException;
import exceptions.InvalidPhonenumberException;
import exceptions.InvalidPostalCodeException;
import exceptions.NotANumberException;
import exceptions.NumberOutOfRangeException;
import exceptions.PhoneNumberAlreadyExistsExeption;
import importAndExport.CSVService;
import vereinsverwaltung.bankverbindung.Bankverbindung;
import vereinsverwaltung.bankverbindung.Iban;
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
import vereinsverwaltung.verein.VereinsParser;
import vereinsverwaltung.verein.VereinsService;
import vereinsverwaltung.vereineSortieren.VereineMitgliederAnzahlAbwaerts;
import vereinsverwaltung.vereineSortieren.VereineMitgliederAnzahlAufwaerts;
import vereinsverwaltung.vorstand.Vorstand;
import vereinsverwaltung.vorstand.Vorstandsrollen;

public class Vereinsverwaltung {

  private static final int THREE = 3;
  private static final int FOUR = 4;
  private static final int FIVE = 5;
  private static final int SIX = 6;
  private static final int SEVEN = 7;

  private VereinsService vereinsService;
  private LinkedList<Verein> vereinsListe;
  private final ResourceBundle germanBundle = ResourceBundle.getBundle("Messages", new Locale("de"));
  private final ResourceBundle englishBundle = ResourceBundle.getBundle("Messages", new Locale("en"));
  private ResourceBundle selectedBundle;


  public Vereinsverwaltung() {
    this.vereinsListe = new LinkedList<Verein>();
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
    MyBufferedReader.print(selectedBundle.getString("addOrEditClubOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        Verein neuerVerein = vereinsService.createVerein(selectedBundle.getString("clubNameRules"), selectedBundle.getString("clubDescriptionRules"));
        if (thisClubAlreadyExists(neuerVerein)) {
          throw new ClubAlreadyExistsException();
        } else {
          vereinsListe.add(neuerVerein);
        }
        break;
      case 2:
        if (getVereinsListe().isEmpty()) {
          MyBufferedReader.printError(selectedBundle.getString("noClubExistsException"));
        } else {
          vereinBearbeiten();
        }
        break;
      case THREE:
        if (getVereinsListe().isEmpty()) {
          MyBufferedReader.printError(selectedBundle.getString("noClubExistsException"));
        } else {
          vereineAusgeben();
        }
        break;
      case FOUR:
        if (getVereinsListe().isEmpty()) {
          MyBufferedReader.printError(selectedBundle.getString("noClubExistsException"));
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
      default:
        MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
    }
    //vereinAnlegenOderBearbeiten();
  }
  //<editor-fold desc="CASE 1 der Vereinslisten-Bearbeitung:">

  //Ein Verein wird der Vereins-Liste hinzugefügt

  // Die Methode prüft, ob es bereits einen Verein mit dem gleichen Namen gibt
  public boolean vereinSchonVorhanden(VereinsName vereinsName) {
    boolean schonVorhanden = false;
    for (int i = 0; i < vereinsListe.size(); i++) {
      if (vereinsListe.get(i).getName().equals(vereinsName)) {
        schonVorhanden = true;
        break;
      }
    }
    return schonVorhanden;
  }
  //</editor-fold>

  //<editor-fold desc="CASE 2 der Vereinslisten-Bearbeitung:">

  // Bearbeitung eines Vereins
  private void vereinBearbeiten() {
    Verein ausgewaehlterVerein = vereinAuswaehlen();
    MyBufferedReader.print(selectedBundle.getString("editClubOptions"));
    // Der Switch wird so lange durchlaufen wie der Programmteil nicht aktiv durch eine Eingabe verlassen wird
    boolean ok = true;
    while (ok) {
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
          MyBufferedReader.print(selectedBundle.getString("onlyOnePersonPerExecutiveRole"), YELLOW);
          MyBufferedReader.print(selectedBundle.getString("whichPersonForExecutiveRole"));
          Mitglied ausgewaehltesMitglied = mitgliedAuswaehlen(ausgewaehlterVerein);
          vorstandsrolleZuweisen(ausgewaehltesMitglied);
          break;
        case SIX:
          vorstandsrollenAnzeigen(ausgewaehlterVerein);
          break;
        case SEVEN:
          // Es wird zurück zur Startauswahl gewechselt
          ok = false;
          return;
        default:
          MyBufferedReader.printInvalidInput();
      }
      // Am Ende jedes Cases muss einmal Enter gedrückt werden, um zur Bearbeitungsübersicht zurückzugelangen
      MyBufferedReader.print(selectedBundle.getString("backToEditingView"));
      MyBufferedReader.readInString();
      MyBufferedReader.print(selectedBundle.getString("selectedClubPrefix") + ausgewaehlterVerein.getName());
      MyBufferedReader.print(selectedBundle.getString("editClubOptions"));
    }
  }

  //<editor-fold desc="Hilfsfunktionen vereinBearbeitet">

  // Gibt alle Vereine nummeriert aus
  private Verein vereinAuswaehlen() {
    Verein ausgewaehlterVerein;
    Collections.sort(vereinsListe);
    MyBufferedReader.print(selectedBundle.getString("chooseClub"));
    int i = 1;
    for (Verein verein : vereinsListe) {
      MyBufferedReader.print(i + ". " + verein.getName());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(vereinsListe.size());
        ok = true;
      } catch (NotANumberException e) {
        MyBufferedReader.printError(selectedBundle.getString("notANumberException"));
      } catch (NumberOutOfRangeException e) {
        System.err.println(selectedBundle.getString("numberOutOfRangeException"));
      }
    }
    ausgewaehlterVerein = vereinsListe.get(userInput - 1);
    return ausgewaehlterVerein;
  }
  //</editor-fold>

  private void vereinUmbenennen(Verein ausgewaehlterVerein) {
    MyBufferedReader.print(selectedBundle.getString("selectedClubNamePrefix") + ausgewaehlterVerein.getName() + selectedBundle.getString("enterNewClubName"));
    VereinsName neuerName = new VereinsName(MyBufferedReader.readInString());
    if (vereinSchonVorhanden(neuerName)) {
      MyBufferedReader.printError(selectedBundle.getString("clubAlreadyExistsException"));
    } else if (!neuerName.getVereinsName().equals("")) {
      ausgewaehlterVerein.setName(neuerName);
    } else {
      MyBufferedReader.print(RED + selectedBundle.getString("dontLeaveFieldEmpty") + RESET);
    }
  }

  public void beschreibungAendern(Verein ausgewaehlterVerein) {
    // Die alte Beschreibung des Vereins wird ausgegeben und die neue wird dem Verein zugewiesen
    MyBufferedReader.print(selectedBundle.getString("clubDescriptionPrefix") + ausgewaehlterVerein.getBeschreibung() + selectedBundle.getString("enterNewClubDescription"));
    VereinsBeschreibung neueBeschreibung = new VereinsBeschreibung(MyBufferedReader.readInString());
    if (!neueBeschreibung.getVereinsBeschreibung().equals("")) {
      ausgewaehlterVerein.setBeschreibung(neueBeschreibung);
    } else {
      MyBufferedReader.print(RED + selectedBundle.getString("dontLeaveFieldEmpty") + RESET);
    }
  }

  private void mitgliederVerwalten(Verein ausgewaehlterVerein) {
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(FOUR, selectedBundle.getString("editClubMemberListOptions"));
        ok = true;
      } catch (NotANumberException e) {
        MyBufferedReader.printError("notANumberException");
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError("numberOutOfRangeException");
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
          MyBufferedReader.printError(selectedBundle.getString("noExistingClubMembers"));
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
              MyBufferedReader.print(output, BLUE);
              break;
            }
          }
          ausgewaehlterVerein.getMitgliederliste().remove(ausgewaehltesMitglied);
          MyBufferedReader.print(selectedBundle.getString("memberDeleted"));
        } else {
          MyBufferedReader.printError(selectedBundle.getString("noExistingClubMembers"));
        }
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
    }
  }

  private boolean mitgliedSchonVorhanden(Mitgliedsnummer neueMitgliedsnummer, Verein ausgewaehlterVerein) {
    boolean istgleich = false;
    for (int i = 0; i < ausgewaehlterVerein.getMitgliederliste().size(); i++) {
      Mitgliedsnummer vorhandeneMitgliedsnummer = ausgewaehlterVerein.getMitgliederliste().get(i).getMitgliedsnummer();
      if (neueMitgliedsnummer.equals(vorhandeneMitgliedsnummer)) {
        istgleich = true;
      }
    }
    return istgleich;
  }

  private Mitglied neuesMitgliedErstellen(Verein ausgewaehlterVerein) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    MyBufferedReader.print(selectedBundle.getString("enterEveryFieldSeparately"));
    MyBufferedReader.print(selectedBundle.getString("memberNumberPrefix"));
    Mitgliedsnummer mitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
    // Es wird geprüft, ob die Mitgliedsnummers schon vergeben ist
    while (mitgliedSchonVorhanden(mitgliedsnummer, ausgewaehlterVerein)) {
      MyBufferedReader.print(RED + selectedBundle.getString("memberNumberAlreadyUsed"));
      mitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
    }
    MyBufferedReader.print(selectedBundle.getString("forenamePrefix"));
    Vorname vorname = new Vorname(MyBufferedReader.readInString());
    MyBufferedReader.print(selectedBundle.getString("surnamePrefix"));
    Nachname nachname = new Nachname(MyBufferedReader.readInString());
    MyBufferedReader.print(selectedBundle.getString("birthdayPrefix"));
    Geburtsdatum geburtsdatum = entervalidBirthday();
    MyBufferedReader.print(selectedBundle.getString("addressPrefix"));
    Adresse adresse = enterValidAddress();
    MyBufferedReader.print(selectedBundle.getString("phoneNumberPrefix"));
    Telefonnummer telefonnummer = enterValidPhoneNumber();
    Mitglied neuesMitglied = new Mitglied(mitgliedsnummer, vorname, nachname, geburtsdatum, adresse, telefonnummer, ausgewaehlterVerein);
    MyBufferedReader.print(selectedBundle.getString("wantToAddBankDetails"));
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
    MyBufferedReader.print(selectedBundle.getString("enterIban"));
    Iban iban = enterValidIban();
    neuesMitglied.setBankverbindung(new Bankverbindung(neuesMitglied, iban));
  }

  // Methode genutzt in CASE 2 der Vereinslisten-Bearbeitung
  // Die Methode gibt eine nummerierte Liste der Mitglieder aus und gibt nach einer Eingabe ein Mitglied zurück
  private Mitglied mitgliedAuswaehlen(Verein ausgewaehlterVerein) {
    // Durchnummerierte Ausgabe der Mitglieder
    int nummerDesMiglieds = 1;
    for (Mitglied mitglied : ausgewaehlterVerein.getMitgliederliste()) {
      MyBufferedReader.print(nummerDesMiglieds + ": " + mitglied.toString());
      nummerDesMiglieds++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehlterVerein.getMitgliederliste().size(), selectedBundle.getString("chooseClubMember"));
        ok = true;
      } catch (NotANumberException e) {
        System.err.println(selectedBundle.getString("notANumberException"));
      } catch (NumberOutOfRangeException e) {
        System.err.println(selectedBundle.getString("numberOutOfRangeException"));
      }
    }
    return ausgewaehlterVerein.getMitgliederliste().get(userInput - 1);
  }

  // Methode genutzt in CASE 2 der Vereinslisten-Bearbeitung
  // Die Methode bearbeitet ein Mitglied, indem neue Value-Objects zugewiesen werden
  private void mitgliedBearbeiten(Mitglied ausgewaehltesMitglied, Verein ausgewaehlterVerein) {
    MyBufferedReader.print(selectedBundle.getString("editClubMemberOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.print(selectedBundle.getString("newMemberNumber") + ausgewaehltesMitglied.getMitgliedsnummer() + ")");
        MyBufferedReader.print(RED + selectedBundle.getString("noDoubleMemberNumber") + RESET);
        Mitgliedsnummer neueMitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
        while (mitgliedSchonVorhanden(neueMitgliedsnummer, ausgewaehlterVerein)) {
          MyBufferedReader.print(RED + selectedBundle.getString("memberNumberAlreadyUsed") + RESET);
          neueMitgliedsnummer = new Mitgliedsnummer(MyBufferedReader.readInString());
        }
        ausgewaehltesMitglied.setMitgliedsnummer(neueMitgliedsnummer);
        break;
      case 2:
        MyBufferedReader.print(selectedBundle.getString("newForename") + ausgewaehltesMitglied.getVorname() + ")");
        Vorname neuerVorname = new Vorname(MyBufferedReader.readInString());
        ausgewaehltesMitglied.setVorname(neuerVorname);
        break;
      case THREE:
        MyBufferedReader.print(selectedBundle.getString("newSurname") + ausgewaehltesMitglied.getNachname() + ")");
        Nachname neuerNachname = new Nachname(MyBufferedReader.readInString());
        ausgewaehltesMitglied.setNachname(neuerNachname);
        break;
      case FOUR:
        MyBufferedReader.print(selectedBundle.getString("newBirthday") + ausgewaehltesMitglied.getGeburtsdatum() + ")");
        Geburtsdatum neuesGeburtsdatum = entervalidBirthday();
        ausgewaehltesMitglied.setGeburtsdatum(neuesGeburtsdatum);
        break;
      case FIVE:
        adressenBearbeiten(ausgewaehltesMitglied, ausgewaehlterVerein);
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
      MyBufferedReader.printError(selectedBundle.getString("noExistingClubMembers"));
    } else {
      MyBufferedReader.print(selectedBundle.getString("chooseMemberListOutput"));
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
            MyBufferedReader.print(mitglied.toString() + "\n");
          }
          break;
        default:
          MyBufferedReader.printInvalidInput();
      }
    }
  }

  //<editor-fold desc="Methoden zum bearbeiten der Adressen">
  // Methode leitet die Bearbeitung der Adressen ein
  private void adressenBearbeiten(Mitglied ausgewaehltesMitglied, Verein ausgewaehlterVerein) {
    MyBufferedReader.print(selectedBundle.getString("currentAddressesPrefix") + ausgewaehltesMitglied.getAdressen());
    MyBufferedReader.print(selectedBundle.getString("editAddressesOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.print(selectedBundle.getString("newAddressWithCurrent") + ausgewaehltesMitglied.getAdressen() + ")");
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
        MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
        break;
    }
  }

  //Methode zum ändern einer Adresse
  public void eineAdresseAendern(Mitglied ausgewaehltesMitglied) {
    if (ausgewaehltesMitglied.getAdressen().size() == 1) {
      MyBufferedReader.print(selectedBundle.getString("newAddressWithCurrent") + ausgewaehltesMitglied.getAdressen() + ")");
      Adresse neueAdresse = enterValidAddress();
      ausgewaehltesMitglied.getAdressen().remove(0);
      ausgewaehltesMitglied.getAdressen().add(neueAdresse);
    } else {
      MyBufferedReader.print(selectedBundle.getString("chooseAddress"));
      int i = 1;
      for (Adresse adresse : ausgewaehltesMitglied.getAdressen()) {
        MyBufferedReader.print(i + ": " + adresse.toString());
        i++;
      }
      int userInput;
      while (true) {
        try {
          userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getAdressen().size(), selectedBundle.getString("chooseClubMember"));
          break;
        } catch (NotANumberException e) {
          System.err.println(selectedBundle.getString("notANumberException"));
        } catch (NumberOutOfRangeException e) {
          System.err.println(selectedBundle.getString("numberOutOfRangeException"));
        }
      }
      ausgewaehltesMitglied.getAdressen().add((userInput - 1), uniqueAddress(ausgewaehltesMitglied, enterValidAddress()));
      ausgewaehltesMitglied.getAdressen().remove(userInput);
    }
  }

  private void eineAdresseLoeschen(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.print(selectedBundle.getString("deleteAddress"));
    int i = 1;
    for (Adresse adresse : ausgewaehltesMitglied.getAdressen()) {
      MyBufferedReader.print(i + ": " + adresse.toString());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getAdressen().size());
        ok = true;
      } catch (NotANumberException e) {
        System.err.println(selectedBundle.getString("notANumberException"));
      } catch (NumberOutOfRangeException e) {
        System.err.println(selectedBundle.getString("numberOutOfRangeException"));
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
    MyBufferedReader.print(selectedBundle.getString("currentPhoneNumber") + ausgewaehltesMitglied.getTelefonnummern() + selectedBundle.getString("editPhoneNumberOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.print(selectedBundle.getString("newPhoneNumber") + ausgewaehltesMitglied.getTelefonnummern() + ")");
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
        MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
        break;
    }
  }

  // Ändert eine Telefonnummer
  private void eineTelefonnummerAendern(Mitglied ausgewaehltesMitglied) {
    if (ausgewaehltesMitglied.getTelefonnummern().size() == 1) {
      MyBufferedReader.print(selectedBundle.getString("newPhoneNumber") + ausgewaehltesMitglied.getTelefonnummern() + ")");
      Telefonnummer neueTelefonnummer = enterValidPhoneNumber();
      ausgewaehltesMitglied.getTelefonnummern().remove(0);
      ausgewaehltesMitglied.getTelefonnummern().add(neueTelefonnummer);
    } else {
      MyBufferedReader.print(selectedBundle.getString("chooseEditPhoneNumber"));
      int i = 1;
      for (Telefonnummer telefonnummer : ausgewaehltesMitglied.getTelefonnummern()) {
        MyBufferedReader.print(i + ": " + telefonnummer.getTelefonnummer());
        i++;
      }
      int userInput = -1;
      boolean ok = false;
      while (!ok) {
        try {
          userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getTelefonnummern().size());
          ok = true;
        } catch (NotANumberException e) {
          MyBufferedReader.printError(selectedBundle.getString("notANumberException"));
        } catch (NumberOutOfRangeException e) {
          MyBufferedReader.printError(selectedBundle.getString("numberOutOfRangeException"));
        }
      }
      MyBufferedReader.print("Please enter the changed phone number.");
      ausgewaehltesMitglied.getTelefonnummern().remove(userInput - 1);
      ausgewaehltesMitglied.getTelefonnummern().add((userInput - 1), uniquePhoneNumber(ausgewaehltesMitglied, enterValidPhoneNumber()));
    }
  }

  // Löscht eine Telefonnummer
  private void eineTelefonnummerLoeschen(Mitglied ausgewaehltesMitglied) {
    MyBufferedReader.print(selectedBundle.getString("chooseDeletePhoneNumber"));
    int i = 1;
    for (Telefonnummer telefonnummer : ausgewaehltesMitglied.getTelefonnummern()) {
      MyBufferedReader.print(i + ": " + telefonnummer.getTelefonnummer());
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehltesMitglied.getTelefonnummern().size() - 1);
        ok = true;
      } catch (NotANumberException e) {
        MyBufferedReader.printError(selectedBundle.getString("notANumberException"));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString("numberOutOfRangeException"));
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
      MyBufferedReader.printError(selectedBundle.getString("noClubMembersYet"));
    } else {
      MyBufferedReader.print(selectedBundle.getString("bankDetailsOptions"));
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
          MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
          break;
      }
    }
  }

  // Methode fügt einem Verein eine Bankverbindung hinzu
  private void addBankverbindung(Verein ausgewaehlterVerein) {
    MyBufferedReader.print(selectedBundle.getString("bankDetailsInfo"));
    MyBufferedReader.print(RED + selectedBundle.getString("bankDetailsRule"));
    System.out.print(selectedBundle.getString("chooseAccountOwner"));
    int mitgliedsNummer = 1;
    for (Mitglied mitglied : ausgewaehlterVerein.getMitgliederliste()) {
      MyBufferedReader.print(mitgliedsNummer + ": " + mitglied.toString());
      mitgliedsNummer++;
    }
    Mitglied ausgewaehltesMitglied = mitgliedAuswaehlen(ausgewaehlterVerein);
    MyBufferedReader.print(selectedBundle.getString("enterIban"));
    Iban ibanDesKontos = enterValidIban();
    if (ausgewaehlterVerein.getBankverbindungen().size() > 0) {
      while (bankverbindungSchonVorhanden(ibanDesKontos, ausgewaehlterVerein)) {
        MyBufferedReader.print(RED + selectedBundle.getString("accountAlreadyExists") + RESET);
        ibanDesKontos = new Iban(MyBufferedReader.readInString());
      }
    }
    ausgewaehlterVerein.getBankverbindungen().add(new Bankverbindung(ausgewaehltesMitglied, ibanDesKontos));
    MyBufferedReader.print(selectedBundle.getString("bankDetailsAdded"));
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
    MyBufferedReader.print(selectedBundle.getString("whatShouldBeEdited"));
    MyBufferedReader.print("1. " + selectedBundle.getString("accountOwnerPrefix") + ausgewaehlteBankverbindung.getKontoinhaber());
    MyBufferedReader.print("2. " + selectedBundle.getString("ibanPrefix") + ausgewaehlteBankverbindung.getIban());
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        MyBufferedReader.print(selectedBundle.getString("newAccountOwner"));
        Mitglied neuerKontoinhaber = mitgliedAuswaehlen(ausgewaehlterVerein);
        ausgewaehlteBankverbindung.setKontoinhaber(neuerKontoinhaber);
        MyBufferedReader.print(selectedBundle.getString("accountOwnerChangeSuccessful"));
        break;
      case 2:
        MyBufferedReader.print(selectedBundle.getString("newIban"));
        Iban neueIban = enterValidIban();
        while (bankverbindungSchonVorhanden(neueIban, ausgewaehlterVerein)) {
          MyBufferedReader.print(RED + selectedBundle.getString("accountAlreadyExists"));
          neueIban = new Iban(MyBufferedReader.readInString());
        }
        ausgewaehlteBankverbindung.setIban(neueIban);
        MyBufferedReader.print(selectedBundle.getString("accountIbanChangeSuccessful"));
        break;
      case 0:
        break;
      default:
        MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
    }
  }

  private Bankverbindung bankverbindungAuswaehlen(Verein ausgewaehlterVerein) {
    MyBufferedReader.print(selectedBundle.getString("currentAccountInfoOfClub") + ausgewaehlterVerein.getName() + ":\n");
    for (int i = 0; i < ausgewaehlterVerein.getBankverbindungen().size(); i++) {
      MyBufferedReader.print(selectedBundle.getString("bankAccountPrefix") + (i + 1) + ":\n" + ausgewaehlterVerein.getBankverbindungen().get(i).toString());
    }
    MyBufferedReader.print(selectedBundle.getString("chooseAccount"));
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(ausgewaehlterVerein.getBankverbindungen().size());
        ok = true;
      } catch (NotANumberException e) {
        System.err.println(selectedBundle.getString("notANumberException"));
      } catch (NumberOutOfRangeException e) {
        System.err.println(selectedBundle.getString("numberOutOfRangeException"));
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
    MyBufferedReader.print(selectedBundle.getString("executiveRoleCompleteOrSubstitutional"));
    String vollstaendigOderKommissarisch = MyBufferedReader.readInString();
    if (vollstaendigOderKommissarisch.equalsIgnoreCase("v") || vollstaendigOderKommissarisch.equalsIgnoreCase("k")) {
      vorstandAuswaehlen(ausgewaehltesMitglied, vollstaendigOderKommissarisch.toLowerCase());
    } else {
      MyBufferedReader.printError(selectedBundle.getString("faultyEntry"));
    }

  }

  // Weißt die Vorstandrollen zu

  private void vorstandAuswaehlen(Mitglied ausgewaehltesMitglied, String vollstaendigOderKommissarisch) {
    if (vollstaendigOderKommissarisch.equals("v")) {
      MyBufferedReader.print(selectedBundle.getString("chooseExecutiveRole"));
      MyBufferedReader.print("1. " + Vorstandsrollen.ERSTER_VORSITZENDER);
      MyBufferedReader.print("2. " + Vorstandsrollen.ZWEITER_VORSITZENDER);
      MyBufferedReader.print("3. " + Vorstandsrollen.SCHRIFTFUEHRER);
      MyBufferedReader.print("4. " + Vorstandsrollen.KASSENWART);
    } else {
      MyBufferedReader.print(selectedBundle.getString("chooseSubstitutionalRole"));
      MyBufferedReader.print("1. " + Vorstandsrollen.ERSTER_VORSITZENDER_KOMMISSARISCH);
      MyBufferedReader.print("2. " + Vorstandsrollen.ZWEITER_VORSITZENDER_KOMMISSARISCH);
      MyBufferedReader.print("3. " + Vorstandsrollen.SCHRIFTFUEHRER_KOMMISSARISCH);
      MyBufferedReader.print("4. " + Vorstandsrollen.KASSENWART_KOMMISSARISCH);
    }
    Vorstandsrollen chosenRole;
    Vorstand newRole;
    int userInput = MyBufferedReader.forceReadInInt();
    switch (userInput) {
      case 1:
        if (vollstaendigOderKommissarisch.equals("v")) {
          chosenRole = Vorstandsrollen.ERSTER_VORSITZENDER;
        } else {
          chosenRole = Vorstandsrollen.ERSTER_VORSITZENDER_KOMMISSARISCH;
        }
        if (ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, chosenRole);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, chosenRole);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case 2:
        if (vollstaendigOderKommissarisch.equals("v")) {
          chosenRole = Vorstandsrollen.ZWEITER_VORSITZENDER;
        } else {
          chosenRole = Vorstandsrollen.ZWEITER_VORSITZENDER_KOMMISSARISCH;
        }
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, chosenRole);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, chosenRole);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case THREE:
        if (vollstaendigOderKommissarisch.equals("v")) {
          chosenRole = Vorstandsrollen.SCHRIFTFUEHRER;
        } else {
          chosenRole = Vorstandsrollen.SCHRIFTFUEHRER_KOMMISSARISCH;
        }
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, chosenRole);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, chosenRole);
        ausgewaehltesMitglied.getVerein().getVorstandsrollen().add(newRole);
        break;
      case FOUR:
        if (vollstaendigOderKommissarisch.equals("v")) {
          chosenRole = Vorstandsrollen.KASSENWART;
        } else {
          chosenRole = Vorstandsrollen.KASSENWART_KOMMISSARISCH;
        }
        if (!ausgewaehltesMitglied.getVerein().getVorstandsrollen().isEmpty()) {
          vorstandsrolleEntfernen(ausgewaehltesMitglied, chosenRole);
        }
        newRole = new Vorstand(ausgewaehltesMitglied, chosenRole);
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
      MyBufferedReader.printError(selectedBundle.getString("noClubMembersYet"));
    }
    for (Vorstand vorstand : ausgewaehlterVerein.getVorstandsrollen()) {
      if (vorstand.getVorstandsrolle().equals(Vorstandsrollen.ERSTER_VORSITZENDER) ||
          vorstand.getVorstandsrolle().equals(Vorstandsrollen.ZWEITER_VORSITZENDER) ||
          vorstand.getVorstandsrolle().equals(Vorstandsrollen.SCHRIFTFUEHRER) ||
          vorstand.getVorstandsrolle().equals(Vorstandsrollen.KASSENWART)) {
        MyBufferedReader.print(vorstand.toString(), BLUE);
      } else {
        MyBufferedReader.print(vorstand.toString(), YELLOW);
      }
    }

  }
  //</editor-fold>


  //</editor-fold>

  //<editor-fold desc="CASE 3 der Vereinslisten-Bearbeitung:">
  // Gibt die Vereine sortiert aus
  private void vereineAusgeben() {
    MyBufferedReader.print(selectedBundle.getString("printClubListOptions"));
    int eingabe = MyBufferedReader.forceReadInInt();
    switch (eingabe) {
      case 1:
        Collections.sort(getVereinsListe());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.print(verein.toString() + "\n");
        }
        break;
      case 2:
        Collections.reverse(getVereinsListe());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.print(verein.toString() + "\n");
        }
        break;
      case THREE:
        Collections.sort(getVereinsListe(), new VereineMitgliederAnzahlAufwaerts());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.print(verein.toString() + "\n");
        }
        break;
      case FOUR:
        Collections.sort(getVereinsListe(), new VereineMitgliederAnzahlAbwaerts());
        for (Verein verein : getVereinsListe()) {
          MyBufferedReader.print(verein.toString() + "\n");
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
    MyBufferedReader.print(selectedBundle.getString("chooseClubToDelete"));
    int i = 1;
    for (Verein verein : getVereinsListe()) {
      MyBufferedReader.print(i + ": " + verein.toString() + "\n");
      i++;
    }
    int userInput = -1;
    boolean ok = false;
    while (!ok) {
      try {
        userInput = MyBufferedReader.readInInt(getVereinsListe().size());
        ok = true;
      } catch (NotANumberException e) {
        MyBufferedReader.printError(selectedBundle.getString("notANumberException"));
      } catch (NumberOutOfRangeException e) {
        MyBufferedReader.printError(selectedBundle.getString("numberOutOfRangeException"));
      }
    }
    getVereinsListe().remove(userInput - 1);
    MyBufferedReader.print(selectedBundle.getString("clubDeletedSuccess"));
  }

  private void vereineExportieren() {
    String exportDirectoryString = MyBufferedReader.readInString(selectedBundle.getString("enterExportDirectoryPath"));
    String exportFileNameString = MyBufferedReader.readInString(selectedBundle.getString("enterExportFileName"));
    //File exportDirectory = new File(exportDirectoryString);

    File exportDirectory = new File("./resources/clubManagement");
    String exportFileName = "clubManagementExport";
    CSVService exportCsvService = new CSVService(this.vereinsListe, exportDirectory.getPath(), exportFileName + ".csv");
    try {
      exportCsvService.exportClubListToCSV(new VereinsParser());
      MyBufferedReader.print(selectedBundle.getString("exportSuccessful"));
    } catch (FileNotFoundException e) {
      MyBufferedReader.printError(selectedBundle.getString("fileNotFoundException"));
    } catch (IOException e) {
      MyBufferedReader.printError(selectedBundle.getString("ioException"));
    }
  }

  private void vereinslisteImportieren() {
    String importDirectoryString = MyBufferedReader.readInString(selectedBundle.getString("enterImportDirectoryPath"));
    String exportFileNameString = MyBufferedReader.readInString(selectedBundle.getString("enterImportFileName"));
    //File importDirectory = new File(importDirectoryString);

    File importDirectory = new File("./resources/clubManagement");
    String importFilename = "clubManagementExport.csv";
    CSVService importCsvService = new CSVService(this.vereinsListe, importDirectory.getPath(), importFilename);
    LinkedList<Verein> clubList = new LinkedList<>();
    try {
      clubList = importCsvService.importClubToSystem();
      this.vereinsListe.addAll(clubList);
      MyBufferedReader.print(selectedBundle.getString("importSuccessful"));
    } catch (FileNotFoundException e) {
      System.err.println("fileNotFoundException");
    } catch (IOException e) {
      System.err.println(selectedBundle.getString("ioException"));
    }
  }

  private void spracheWechseln() {
    try {
      MyBufferedReader.print(selectedBundle.getString("chooseLanguage"));
      int selectedLanguage = MyBufferedReader.readInInt(2);
      if (selectedLanguage == 1) {
        this.selectedBundle = this.germanBundle;
      } else if (selectedLanguage == 2) {
        this.selectedBundle = this.englishBundle;
      } else {
        MyBufferedReader.printError(selectedBundle.getString("chooseLanguageError"));
      }
    } catch (NotANumberException e) {
      MyBufferedReader.printError(selectedBundle.getString("notANumberException"));
    } catch (NumberOutOfRangeException e) {
      MyBufferedReader.printError(selectedBundle.getString("numberOutOfRangeException"));
    }
  }

}
//</editor-fold>
