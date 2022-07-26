package jle.vereinsverwaltung.verein;

public class VereinsName {

  private final String vereinsNameString;

  public VereinsName(String vereinsNameString) {
    this.vereinsNameString = vereinsNameString;
  }

  @Override
  public int hashCode() {

    return vereinsNameString.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    boolean gleich = false;
    if (obj instanceof VereinsName) {
      VereinsName vereinsName = (VereinsName)obj;
      if (this.getVereinsNameString().equalsIgnoreCase(vereinsName.getVereinsNameString())) {
        gleich = true;
      }
    }
    return gleich;
  }

  @Override
  public String toString() {
    return vereinsNameString;
  }

  public String getVereinsNameString() {
    return vereinsNameString;
  }
}
