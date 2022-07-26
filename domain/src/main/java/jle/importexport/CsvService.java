package jle.importexport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;

import jle.vereinsverwaltung.verein.Verein;
import jle.vereinsverwaltung.verein.VereinsParser;

public class CsvService {

  private final LinkedList<Verein> vereinsListe;
  private final String directoryPath;
  private final String fileName;

  private final String slash = "/";

  public CsvService(LinkedList<Verein> vereinsListe, String directoryPath, String fileName) {
    this.vereinsListe = vereinsListe;
    this.directoryPath = directoryPath;
    this.fileName = fileName;
  }

  public void exportClubListToCsv(CsvParser parser) throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(this.directoryPath + slash + fileName));
    StringBuilder clubListBuilder = new StringBuilder();
    Iterator<Verein> clubListIterator = this.vereinsListe.iterator();
    while (clubListIterator.hasNext()) {
      clubListBuilder.append(parser.parseToString(clubListIterator.next()));
      if (!clubListIterator.hasNext()) {
        clubListBuilder.deleteCharAt(clubListBuilder.lastIndexOf(","));
      }
    }
    osw.write(clubListBuilder.toString());
    osw.close();
  }

  public LinkedList<Verein> importClubToSystem() throws IOException {
    FileInputStream fis = new FileInputStream(this.directoryPath + slash + this.fileName);
    StringBuilder clubListBuilder = new StringBuilder();
    VereinsParser vereinsParser = new VereinsParser();
    int ch;
    while ((ch = fis.read()) != -1) {
      clubListBuilder.append((char)ch);
    }
    return vereinsParser.parseStringToClubList(clubListBuilder.toString());
  }

}
