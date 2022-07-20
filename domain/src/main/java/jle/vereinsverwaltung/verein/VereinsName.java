package jle.vereinsverwaltung.verein;

public class VereinsName {

    private String vereinsName;

    public VereinsName(String vereinsName) {
        this.vereinsName = vereinsName;
    }

    @Override
    public int hashCode() {

        return vereinsName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean gleich = false;
        if (obj instanceof VereinsName) {
            VereinsName vereinsName = (VereinsName) obj;
            String vereinsNameString = vereinsName.getVereinsName();
            if (this.getVereinsName().toLowerCase().equals(vereinsNameString.toLowerCase())){
                gleich = true;
            }
        }
        return gleich;
    }

    @Override
    public String toString() {
        return vereinsName;
    }

    public String getVereinsName() {
        return vereinsName;
    }
}
