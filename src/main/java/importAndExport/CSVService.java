package importAndExport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;

import vereinsverwaltung.verein.Verein;
import vereinsverwaltung.verein.VereinsParser;

public class CSVService {

  private LinkedList<Verein> vereinsListe;
  private String directoryPath;
  private String fileName;

  public CSVService(LinkedList<Verein> vereinsListe, String directoryPath, String fileName) {
    this.vereinsListe = vereinsListe;
    this.directoryPath = directoryPath;
    this.fileName = fileName;
  }

  public void exportClubListToCSV(CSVParser parser) throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(this.directoryPath + "/" + fileName));
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
    FileInputStream fis = new FileInputStream(this.directoryPath + "/" + this.fileName);
    StringBuilder clubListBuilder = new StringBuilder();
    VereinsParser vereinsParser = new VereinsParser();
    int ch;
    while ((ch = fis.read()) != -1) {
      clubListBuilder.append((char)ch);
    }
    return vereinsParser.parseStringToClubList(clubListBuilder.toString());
  }

}
