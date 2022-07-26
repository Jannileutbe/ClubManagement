package jle.vereinsverwaltung.mitglied.valueobjects;

public class Vorname {
    private final String vornameString;

    public Vorname(String vornameString) {
        this.vornameString = vornameString;
    }

    @Override
    public int hashCode() {
        return vornameString.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof Vorname) {
            Vorname vorname = (Vorname)obj;
            if (this.getVornameString().equalsIgnoreCase(vorname.getVornameString())) {
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return vornameString;
    }

    public String getVornameString() {
        return vornameString;
    }
}
