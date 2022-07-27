/*
 *
 * jle.vereinsverwaltung.mitglied.valueobjects.Nachname
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

package jle.vereinsverwaltung.mitglied.valueobjects;

public class Nachname {
    private final String nachnameString;

    public Nachname(String nachnameString) {
        this.nachnameString = nachnameString;
    }

    @Override
    public int hashCode() {
        return nachnameString.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof Nachname) {
            Nachname nachname = (Nachname)obj;
            if (this.getNachnameString().equalsIgnoreCase(nachname.getNachnameString())) {
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return nachnameString;
    }

    public String getNachnameString() {
        return nachnameString;
    }

}
