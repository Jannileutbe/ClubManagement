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
