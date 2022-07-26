/*
 *
 * jle.vereinsverwaltung.vorstand.Vorstandsrollen
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

package jle.vereinsverwaltung.vorstand;

public enum Vorstandsrollen {
    ERSTER_VORSITZENDER("Erster Vorsitzender"),
    ZWEITER_VORSITZENDER("Zweiter Vorsitzende"),
    SCHRIFTFUEHRER("Schriftführer"),
    KASSENWART("Kassenwart"),
    ERSTER_VORSITZENDER_KOMMISSARISCH("Kommissarisch: Erster Vorsitzender"),
    ZWEITER_VORSITZENDER_KOMMISSARISCH("Kommissarisch: Zweiter Vorsitzende"),
    SCHRIFTFUEHRER_KOMMISSARISCH("Kommissarisch: Schriftführer"),
    KASSENWART_KOMMISSARISCH("Kommissarisch: Kassenwart");

    private final String bezeichnung;

    Vorstandsrollen(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public String toString() {
        return getBezeichnung();
    }
}
