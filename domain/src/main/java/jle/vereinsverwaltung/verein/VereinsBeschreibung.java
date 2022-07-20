package jle.vereinsverwaltung.verein;

public class VereinsBeschreibung {

    private String vereinsBeschreibung;

    public VereinsBeschreibung(String vereinsBeschreibung) {
        this.vereinsBeschreibung = vereinsBeschreibung;
    }

    @Override
    public int hashCode() {
        return vereinsBeschreibung.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof VereinsBeschreibung) {
            VereinsBeschreibung vereinsBeschreibung = (VereinsBeschreibung) obj;
            if (this.getVereinsBeschreibung().toLowerCase().equals(vereinsBeschreibung.getVereinsBeschreibung().toLowerCase())){
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return vereinsBeschreibung;
    }

    public String getVereinsBeschreibung() {
        return vereinsBeschreibung;
    }

}
