package jle.vereinsverwaltung.vorstand;

import jle.vereinsverwaltung.mitglied.Mitglied;

public class Vorstand {
    private Mitglied mitglied;
    private final Vorstandsrollen vorstandsrolle;

    public Vorstand(Mitglied mitglied, Vorstandsrollen vorstandsrolle) {
        this.mitglied = mitglied;
        this.vorstandsrolle = vorstandsrolle;
    }

    public Mitglied getMitglied() {
        return mitglied;
    }

    public void setMitglied(Mitglied mitglied) {
        this.mitglied = mitglied;
    }

    public Vorstandsrollen getVorstandsrolle() {
        return vorstandsrolle;
    }

    @Override
    public String toString() {
        return "| "
            + this.mitglied.getVorname().toString()
            + " " + this.mitglied.getNachname().toString()
            + ": " + this.vorstandsrolle.toString()
            + " |";
    }
}
