package jle.vereinsverwaltung.vorstand;

import java.util.Comparator;

public class VorstandComparator implements Comparator<Vorstand> {


  @Override
  public int compare(Vorstand executiveOne, Vorstand executiveTwo) {
    return Integer.compare(executiveOne.getVorstandsrolle().ordinal(), executiveTwo.getVorstandsrolle().ordinal());
  }
}
