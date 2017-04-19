import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelReader {

  public static final int TWEET_COL = 1;
  public static final int LAT_COL = 2;
  public static final int LONG_COL = 3;
  public static final String SAVE_FILE = "saved_mapquest";
  public static final String[] SAVE_FILES = new String[]{"saved_google_1", "saved_mapquest"};

  /**
   * Iterate through the spreadsheet and create a new Tweet object for each row.
   */
  public static void main(String[] args) throws IOException {
//    saveTweets();
    readTweets();
  }

  public static void readTweets() throws IOException {
    List<Tweet> tweets = read(SAVE_FILES);
    System.out.println(tweets.size());
//    System.out.println(tweets);
  }

  public static void saveTweets() throws IOException {
    String excelFilePath = "tweets.xlsx";
    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

    InputStream is = new FileInputStream(new File(excelFilePath));
    Sheet sheet = StreamingReader.builder()
        .rowCacheSize(10)    // number of rows to keep in memory (defaults to 10)
        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to
        // 1024)
        .open(is)
        .getSheetAt(0);            // InputStream or File for XLSX file (required)

    List<Tweet> tweets = iterate(sheet);
    save(SAVE_FILE, tweets);
    System.out.println(tweets.size());
    inputStream.close();
  }

  public static List<Tweet> iterate(Sheet sheet) {
    List<Tweet> tweets = new ArrayList<>();

    int numGeo = 0;
    int start = 138473;
    int row = 0;
    for (Row r : sheet) {
      row++;
      if (row >= start) {
        Tweet tweet = null;
        try {
          tweet = getTweet(r);
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (tweet != null) {
          tweets.add(tweet);
          numGeo++;
          if (numGeo >= 15000 - 33)
            break;
        }
      }
    }
    System.out.println("Row num: " + row);
    return tweets;
  }


  /**
   * Get the values of the cells in the row, create a new Tweet object.
   *
   * @param row
   * @return
   */
  private static Tweet getTweet(Row row) throws IOException {
    // column B (1) = tweet
    // column C (2) = latitude
    // column D (3) = longitude
    Tweet newTweet;
    String text = row.getCell(TWEET_COL).getStringCellValue();
    if (!ParseEmoji.containsEmoji(text))
      return null;
    double lat = Double.parseDouble(row.getCell(LAT_COL).getStringCellValue());
    double lon = Double.parseDouble(row.getCell(LONG_COL).getStringCellValue());
    newTweet = new Tweet(text, lat, lon);
    return newTweet;
  }

  public static void save(String fileName, List<Tweet> tweets) throws IOException {
    FileOutputStream fout = new FileOutputStream(fileName);
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(tweets);
    fout.close();
  }

  public static List<Tweet> read(String[] fileNames) throws IOException {
    List<Tweet> tweets = new ArrayList<>();
    for (String fileName : fileNames) {
      FileInputStream fin = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fin);
      try {
        tweets.addAll((ArrayList<Tweet>) ois.readObject());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      fin.close();
    }
    return tweets;

  }

}
