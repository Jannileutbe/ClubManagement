/*
 *
 * jle.vereinsverwaltung.bankverbindung.Bankverbindung
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

package jle.vereinsverwaltung.bankverbindung;

import jle.vereinsverwaltung.mitglied.Mitglied;

public class Bankverbindung {
    private Mitglied kontoinhaber;
    private Iban iban;

    public Bankverbindung(Mitglied kontoinhaber, Iban iban) {
        this.kontoinhaber = kontoinhaber;
        this.iban = iban;
    }

    public Mitglied getKontoinhaber() {
        return kontoinhaber;
    }

    public Iban getIban() {
        return iban;
    }

    public void setKontoinhaber(Mitglied kontoinhaber) {
        this.kontoinhaber = kontoinhaber;
    }

    public void setIban(Iban iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "Kontoinhaber: "
            + kontoinhaber
            + "\nIBAN: "
            + iban;
    }
}
