package jle.vereinsverwaltung.mitglied.ValueObjects;

public class Nachname {
    private String nachname;

    public Nachname(String nachname) {
        this.nachname = nachname;
    }

    @Override
    public int hashCode() {
        return nachname.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof Nachname) {
            Nachname nachname = (Nachname) obj;
            if (this.getNachname().toLowerCase().equals(nachname.getNachname().toLowerCase())){
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return nachname;
    }

    public String getNachname() {
        return nachname;
    }

}
