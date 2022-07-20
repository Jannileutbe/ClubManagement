package jle.vereinsverwaltung.mitglied.ValueObjects;

public class Vorname {
    private String vorname;

    public Vorname(String vorname) {
        this.vorname = vorname;
        //TODO null-check (für alle value Objects)
    }

    @Override
    public int hashCode() {
        return vorname.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof Vorname){
            Vorname vorname = (Vorname) obj;
            if (this.getVorname().toLowerCase().equals(vorname.getVorname().toLowerCase())){
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return vorname;
    }

    public String getVorname() {
        return vorname;
    }
}
