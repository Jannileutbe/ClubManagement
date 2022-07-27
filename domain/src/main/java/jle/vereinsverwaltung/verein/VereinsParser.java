/*
 *
 * jle.vereinsverwaltung.verein.VereinsParser
 *
 *
 * This document contains trade secret data which is the property of
 * OpenKnowledge GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from open knowledge GmbH.
 *
 * Copyright (C) {YEAR} open knowledge GmbH / Oldenburg / Germany
 *
 */

package jle.vereinsverwaltung.verein;

import java.util.LinkedList;

import jle.importexport.CsvParser;
import jle.vereinsverwaltung.bankverbindung.Bankverbindung;
import jle.vereinsverwaltung.bankverbindung.Iban;
import jle.vereinsverwaltung.mitglied.Mitglied;
import jle.vereinsverwaltung.mitglied.valueobjects.Adresse;
import jle.vereinsverwaltung.mitglied.valueobjects.Geburtsdatum;
import jle.vereinsverwaltung.mitglied.valueobjects.Mitgliedsnummer;
import jle.vereinsverwaltung.mitglied.valueobjects.Nachname;
import jle.vereinsverwaltung.mitglied.valueobjects.Telefonnummer;
import jle.vereinsverwaltung.mitglied.valueobjects.Vorname;
import jle.vereinsverwaltung.vorstand.Vorstand;
import jle.vereinsverwaltung.vorstand.Vorstandsrollen;

public class VereinsParser implements CsvParser {

  private final String clubNameMarker = "clubName:";
  private final String clubDescriptionMarker = "clubDescription:";
  private final String clubBankDetailsMarker = "clubBankDetails:";
  private final String clubExecutivesMarker = "clubExecutives:";
  private final String clubMembersListMarker = "clubMembersList:";
  private final String clubMemberMarker = "member:";
  private final String memberAddressMarker = "address:";
  private final String memberPhoneNumberMarker = "phoneNumber:";
  private final String memberBankDetailsMarker = "bankDetails:";
  private final String commaSeparator = ",";

  public VereinsParser() {
  }


  @Override
  public String parseToString(Verein verein) {
    return appendClubName(verein)
        + appendClubDescription(verein)
        + appendClubBankDetails(verein)
        + appendClubExecutives(verein)
        + appendClubMembers(verein);
  }

  private String appendClubName(Verein verein) {
    return this.clubNameMarker + verein.getName().toString() + this.commaSeparator;
  }

  private String appendClubDescription(Verein verein) {
    return this.clubDescriptionMarker + verein.getBeschreibung().toString() + this.commaSeparator;
  }

  private String appendClubBankDetails(Verein verein) {
    StringBuilder clubBankDetailsBuilder = new StringBuilder();
    LinkedList<Bankverbindung> clubBankDetailsList = verein.getBankverbindungen();
    for (Bankverbindung bankverbindung : clubBankDetailsList) {
      clubBankDetailsBuilder.append(this.clubBankDetailsMarker).append(this.commaSeparator)
          .append(bankverbindung.getKontoinhaber().getMitgliedsnummer().toString()).append(this.commaSeparator)
          .append(bankverbindung.getIban().toString()).append(this.commaSeparator);
    }
    return clubBankDetailsBuilder.toString();
  }

  private String appendClubExecutives(Verein verein) {
    StringBuilder clubExecutivesBuilder = new StringBuilder();
    LinkedList<Vorstand> clubExecutives = verein.getVorstandsrollen();
    for (Vorstand executiveRole : clubExecutives) {
      clubExecutivesBuilder.append(this.clubExecutivesMarker).append(this.commaSeparator)
          .append(executiveRole.getMitglied().getMitgliedsnummer().toString()).append(this.commaSeparator)
          .append(executiveRole.getVorstandsrolle().toString()).append(this.commaSeparator);
    }
    return clubExecutivesBuilder.toString();
  }

  private String appendClubMembers(Verein verein) {
    StringBuilder clubMembersBuilder = new StringBuilder();
    LinkedList<Mitglied> clubMembersList = verein.getMitgliederliste();
    clubMembersBuilder.append(this.clubMembersListMarker).append(this.commaSeparator);
    for (Mitglied mitglied : clubMembersList) {
      clubMembersBuilder.append(buildMemberString(mitglied));
    }
    return clubMembersBuilder.toString();
  }

  private String buildMemberString(Mitglied mitglied) {
    StringBuilder memberBuilder = new StringBuilder();
    memberBuilder.append(clubMemberMarker).append(commaSeparator);
    memberBuilder.append(mitglied.getMitgliedsnummer().toString()).append(this.commaSeparator);
    memberBuilder.append(mitglied.getVorname().toString()).append(this.commaSeparator);
    memberBuilder.append(mitglied.getNachname().toString()).append(this.commaSeparator);
    memberBuilder.append(mitglied.getGeburtsdatum().toString()).append(this.commaSeparator);
    memberBuilder.append(this.memberAddressMarker).append(this.commaSeparator);
    LinkedList<Adresse> memberAddresses = mitglied.getAdressen();
    for (Adresse adresse : memberAddresses) {
      memberBuilder.append(adresse.toString()).append(this.commaSeparator);
    }
    memberBuilder.append(this.memberPhoneNumberMarker).append(this.commaSeparator);
    LinkedList<Telefonnummer> memberPhoneNumbers = mitglied.getTelefonnummern();
    for (Telefonnummer telefonnummer : memberPhoneNumbers) {
      memberBuilder.append(telefonnummer.toString()).append(this.commaSeparator);
    }
    if (mitglied.getBankverbindung() != null) {
      memberBuilder.append(this.memberBankDetailsMarker).append(this.commaSeparator).
          append(mitglied.getBankverbindung().getIban().toString()).append(commaSeparator);
    }
    return memberBuilder.toString();
  }


  public LinkedList<Verein> parseStringToClubList(String clubListAsString) {
    LinkedList<Verein> clubList = new LinkedList<>();
    int endOfClub;
    while (!clubListAsString.equals("")) {
      if (clubListAsString.indexOf(this.clubNameMarker, this.clubNameMarker.length()) != -1) {
        endOfClub = clubListAsString.indexOf(this.clubNameMarker, this.clubNameMarker.length());
      } else {
        endOfClub = clubListAsString.length();
      }
      String singleClub = clubListAsString.substring(0, endOfClub);
      clubList.add(parseStringToClub(singleClub));
      clubListAsString = clubListAsString.substring(endOfClub);
    }
    return clubList;
  }

  private Verein parseStringToClub(String clubAsString) {
    VereinsName clubName = parseClubStringToClubName(clubAsString);
    VereinsBeschreibung clubDescription = parseClubStringToClubDescription(clubAsString);
    LinkedList<Mitglied> clubMemberList = parseclubStringToClubMemberList(clubAsString);
    Verein club = new Verein(clubName, clubDescription, clubMemberList);
    for (Mitglied member : clubMemberList) {
      member.setVerein(club);
    }
    if (clubAsString.contains(this.clubBankDetailsMarker)) {
      club.getBankverbindungen().addAll(buildClubBankDetailsList(clubAsString, club.getMitgliederliste()));
    }
    if (clubAsString.contains(this.clubExecutivesMarker)) {
      club.getVorstandsrollen().addAll(buildClubExecutivesList(clubAsString, club.getMitgliederliste()));
    }
    return club;
  }

  private VereinsName parseClubStringToClubName(String clubAsString) {
    int clubNameStart = clubAsString.indexOf(this.clubNameMarker) + this.clubNameMarker.length();
    int clubNameEnd = clubAsString.indexOf(this.clubDescriptionMarker) - 1;
    String clubNameString = clubAsString.substring(clubNameStart, clubNameEnd);
    return new VereinsName(clubNameString);
  }

  private VereinsBeschreibung parseClubStringToClubDescription(String clubAsString) {
    int clubDescriptionStart = clubAsString.indexOf(this.clubDescriptionMarker) + this.clubDescriptionMarker.length();
    int clubDescriptionEnd;
    if (clubAsString.contains(this.clubBankDetailsMarker)) {
      clubDescriptionEnd = clubAsString.indexOf(this.clubBankDetailsMarker) - 1;
    } else {
      clubDescriptionEnd = clubAsString.indexOf(this.clubMembersListMarker) - 1;
    }
    String clubDescriptionString = clubAsString.substring(clubDescriptionStart, clubDescriptionEnd);
    return new VereinsBeschreibung(clubDescriptionString);
  }

  private LinkedList<Mitglied> parseclubStringToClubMemberList(String clubAsString) {
    LinkedList<Mitglied> clubMembersList = new LinkedList<>();
    int clubMembersListStart = clubAsString.indexOf(this.clubMembersListMarker) + this.clubMembersListMarker.length() + 1;
    int clubMembersListEnd = clubAsString.length();
    String clubMembersListString = clubAsString.substring(clubMembersListStart, clubMembersListEnd);
    int clubMemberStart;
    int clubMemberEnd;
    while (!clubMembersListString.equals("")) {
      clubMemberStart = clubMembersListString.indexOf(this.clubMemberMarker) + this.clubMemberMarker.length() + 1;
      if (clubMembersListString.indexOf(this.clubMemberMarker, this.clubMemberMarker.length()) != -1) {
        clubMemberEnd = clubMembersListString.indexOf(this.clubMemberMarker, this.clubMemberMarker.length());
      } else {
        clubMemberEnd = clubMembersListString.length();
      }
      clubMembersList.add(parseMemberStringToClubMember(clubMembersListString.substring(clubMemberStart, clubMemberEnd)));
      clubMembersListString = clubMembersListString.substring(clubMemberEnd);
    }
    return clubMembersList;
  }

  private Mitglied parseMemberStringToClubMember(String clubMemberString) {
    int memberNumberEnd = clubMemberString.indexOf(this.commaSeparator);
    Mitgliedsnummer memberNumber = new Mitgliedsnummer(clubMemberString.substring(0, memberNumberEnd));

    clubMemberString = clubMemberString.substring(memberNumberEnd + 1);

    int memberForenameEnd = clubMemberString.indexOf(this.commaSeparator);
    Vorname forename = new Vorname(clubMemberString.substring(0, memberForenameEnd));

    clubMemberString = clubMemberString.substring(memberForenameEnd + 1);

    int memberSurnameEnd = clubMemberString.indexOf(this.commaSeparator);
    Nachname surname = new Nachname(clubMemberString.substring(0, memberSurnameEnd));

    clubMemberString = clubMemberString.substring(memberSurnameEnd + 1);

    int memberBirthdateEnd = clubMemberString.indexOf(this.memberAddressMarker) - 1;
    Geburtsdatum birthdate = new Geburtsdatum(clubMemberString.substring(0, memberBirthdateEnd));

    clubMemberString = clubMemberString.substring(memberBirthdateEnd + 1);

    int addressesStart = this.memberAddressMarker.length() + 1;
    int addressesEnd = clubMemberString.indexOf(this.memberPhoneNumberMarker);
    LinkedList<Adresse> addressesList = buildMemberAdresses(clubMemberString.substring(addressesStart, addressesEnd));

    clubMemberString = clubMemberString.substring(addressesEnd);

    int phoneNumbersStart = this.memberPhoneNumberMarker.length() + 1;
    int phoneNumberEnd;
    if (clubMemberString.contains(this.memberBankDetailsMarker)) {
      phoneNumberEnd = clubMemberString.indexOf(this.memberBankDetailsMarker);
    } else {
      phoneNumberEnd = clubMemberString.length();
    }
    LinkedList<Telefonnummer> phoneNumbersList = buildMemberPhonenumbers(clubMemberString.substring(phoneNumbersStart, phoneNumberEnd));

    Mitglied member = new Mitglied(memberNumber, forename, surname, birthdate, addressesList, phoneNumbersList);
    if (clubMemberString.contains(memberBankDetailsMarker)) {
      int bankDetailsStart = clubMemberString.indexOf(this.memberBankDetailsMarker) + this.memberBankDetailsMarker.length();
      Bankverbindung bankDetails = buildMemberBankDetails(clubMemberString.substring(bankDetailsStart), member);
      member.setBankverbindung(bankDetails);
    }
    return member;
  }

  private LinkedList<Adresse> buildMemberAdresses(String memberAdressesString) {
    //TODO
    /*
    LinkedList<Adresse> memberAdressesList = new LinkedList<>();
    while (!memberAdressesString.equals("")) {
      int memberAddressEnd = memberAdressesString.indexOf(commaSeparator);
      Adresse address = new Adresse(memberAdressesString.substring(0, memberAddressEnd));
      memberAdressesString = memberAdressesString.substring(memberAddressEnd + 1);
      memberAdressesList.add(address);
    }
    return memberAdressesList;
     */
    return null;
  }

  private LinkedList<Telefonnummer> buildMemberPhonenumbers(String memberPhoneNumbersString) {
    LinkedList<Telefonnummer> memberPhoneNumbersList = new LinkedList<>();
    while (!memberPhoneNumbersString.equals("")) {
      Telefonnummer phoneNumber = new Telefonnummer(memberPhoneNumbersString
          .substring(0, memberPhoneNumbersString.indexOf(commaSeparator)));
      memberPhoneNumbersString = memberPhoneNumbersString.substring(memberPhoneNumbersString.indexOf(commaSeparator) + 1);
      memberPhoneNumbersList.add(phoneNumber);
    }
    return memberPhoneNumbersList;
  }

  private Bankverbindung buildMemberBankDetails(String memberIbanString, Mitglied accountOwner) {
    Iban iban = new Iban(memberIbanString);
    return new Bankverbindung(accountOwner, iban);
  }

  private LinkedList<Bankverbindung> buildClubBankDetailsList(String clubAsString, LinkedList<Mitglied> clubMemberList) {
    LinkedList<Bankverbindung> clubBankDetailsList = new LinkedList<>();
    int clubBankDetailsListStart = clubAsString.indexOf(clubBankDetailsMarker);
    int clubBankDetailsListEnd;
    if (clubAsString.contains(this.clubExecutivesMarker)) {
      clubBankDetailsListEnd = clubAsString.indexOf(this.clubExecutivesMarker);
    } else {
      clubBankDetailsListEnd = clubAsString.indexOf(this.clubMembersListMarker);
    }
    String clubBankDetailsAsString = clubAsString.substring(clubBankDetailsListStart, clubBankDetailsListEnd);
    while (!clubBankDetailsAsString.equals("")) {
      int clubBankDetailsStart = this.clubBankDetailsMarker.length() + 1;
      int clubBankDetailsEnd;
      if (clubBankDetailsAsString.indexOf(this.clubBankDetailsMarker, this.clubBankDetailsMarker.length()) != -1) {
        clubBankDetailsEnd = clubBankDetailsAsString.indexOf(this.clubBankDetailsMarker, this.clubBankDetailsMarker.length());
      } else {
        clubBankDetailsEnd = clubBankDetailsAsString.length();
      }
      String singleClubBankDetail = clubBankDetailsAsString.substring(clubBankDetailsStart, clubBankDetailsEnd);
      Bankverbindung clubBankDetails = buildClubBankDetails(singleClubBankDetail, clubMemberList);
      clubBankDetailsList.add(clubBankDetails);
      clubBankDetailsAsString = clubBankDetailsAsString.substring(clubBankDetailsEnd);
    }
    return clubBankDetailsList;
  }

  private Bankverbindung buildClubBankDetails(String singleClubBankDetails, LinkedList<Mitglied> clubMemberList) {
    int accountOwnerEnd = singleClubBankDetails.indexOf(this.commaSeparator);
    String accountOwnerNumber = singleClubBankDetails.substring(0, accountOwnerEnd);
    Mitglied accountOwner = null;
    for (Mitglied member : clubMemberList) {
      if (member.getMitgliedsnummer().toString().equals(accountOwnerNumber)) {
        accountOwner = member;
      }
    }
    int accountIbanStart = accountOwnerEnd + 1;
    int accountIbanEnd = singleClubBankDetails.indexOf(commaSeparator, accountOwnerEnd + 1);
    String accountIbanAsString = singleClubBankDetails.substring(accountIbanStart, accountIbanEnd);
    Iban accountIban = new Iban(accountIbanAsString);
    return new Bankverbindung(accountOwner, accountIban);
  }

  private LinkedList<Vorstand> buildClubExecutivesList(String clubAsString, LinkedList<Mitglied> clubMemberList) {
    LinkedList<Vorstand> clubExecutivesList = new LinkedList<>();
    int clubExecutivesListStart = clubAsString.indexOf(this.clubExecutivesMarker);
    int clubExecutivesListEnd = clubAsString.indexOf(this.clubMembersListMarker);
    String clubExecutivesAsString = clubAsString.substring(clubExecutivesListStart, clubExecutivesListEnd);
    while (!clubExecutivesAsString.equals("")) {
      int clubExecutiveStart = this.clubExecutivesMarker.length() + 1;
      int clubExecutiveEnd;
      if (clubExecutivesAsString.indexOf(this.clubExecutivesMarker, this.clubExecutivesMarker.length()) != -1) {
        clubExecutiveEnd = clubExecutivesAsString.indexOf(this.clubExecutivesMarker, this.clubExecutivesMarker.length());
      } else {
        clubExecutiveEnd = clubExecutivesAsString.length();
      }
      String singleClubExecutive = clubExecutivesAsString.substring(clubExecutiveStart, clubExecutiveEnd);
      Vorstand clubClubExecutive = buildClubExecutive(singleClubExecutive, clubMemberList);
      clubExecutivesList.add(clubClubExecutive);
      clubExecutivesAsString = clubExecutivesAsString.substring(clubExecutiveEnd);
    }
    return clubExecutivesList;
  }

  private Vorstand buildClubExecutive(String singleClubExecutive, LinkedList<Mitglied> clubMemberList) {
    int executiveRoleStart = singleClubExecutive.indexOf(this.commaSeparator);
    String executiveNumber = singleClubExecutive.substring(0, executiveRoleStart);
    Mitglied executive = null;
    for (Mitglied member : clubMemberList) {
      if (member.getMitgliedsnummer().toString().equals(executiveNumber)) {
        executive = member;
      }
    }
    int executiveRoleNameStart = executiveRoleStart + 1;
    int executiveRoleNameEnd = singleClubExecutive.indexOf(commaSeparator, executiveRoleStart + 1);
    String executiveRoleNameAsString = singleClubExecutive.substring(executiveRoleNameStart, executiveRoleNameEnd);
    Vorstandsrollen[] allExecutiveRoles = Vorstandsrollen.values();
    Vorstandsrollen executiveRole = null;
    for (Vorstandsrollen allRoles : allExecutiveRoles) {
      if (executiveRoleNameAsString.equals(allRoles.toString())) {
        executiveRole = allRoles;
      }
    }
    return new Vorstand(executive, executiveRole);
  }

}
