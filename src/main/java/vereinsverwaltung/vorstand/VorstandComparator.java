package vereinsverwaltung.vorstand;

import java.util.Comparator;

import vereinsverwaltung.vorstand.Vorstand;

public class VorstandComparator implements Comparator<Vorstand> {


  @Override
  public int compare(Vorstand o1, Vorstand o2) {
    return Integer.compare(o1.getVorstandsrolle().ordinal(), o2.getVorstandsrolle().ordinal());
  }
}
