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
  public static final String SAVE_FILE = "saved_mapquest2";
  public static final String[] SAVE_FILES = new String[]{"saved_mapquest",
      "saved_mapquest2"};

  public static void main(String[] args) throws IOException {
//    saveTweets();
    readTweets();
  }

  /**
   * Prints out all the tweets that have been processed
   *
   * @throws IOException
   */
  public static void readTweets() throws IOException {
    List<Tweet> tweets = read(SAVE_FILES);
    System.out.println(tweets);
  }

  /**
   * Saves tweets from excel file into SAVE_FILE
   *
   * @throws IOException
   */
  public static void saveTweets() throws IOException {
    String excelFilePath = "tweets.xlsx";
    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

    InputStream is = new FileInputStream(new File(excelFilePath));
    Sheet sheet = StreamingReader.builder()
        .rowCacheSize(10)    // number of rows to keep in memory (defaults to
        // 10)
        .bufferSize(4096)     // buffer size to use when reading InputStream
        // to file (defaults to
        // 1024)
        .open(is)
        .getSheetAt(0);            // InputStream or File for XLSX file
    // (required)

    List<Tweet> tweets = iterate(sheet);
    save(SAVE_FILE, tweets);
    System.out.println(tweets.size());
    inputStream.close();
  }

  /**
   * Iterates through the inputted sheet and puts tweets with emojis into list.
   *
   * @param sheet
   * @return List of emoji tweets
   */
  public static List<Tweet> iterate(Sheet sheet) {
    List<Tweet> tweets = new ArrayList<>();

    int numGeo = 0; // number tweets added to list
    int start = 204820; // which row to start at
    int row = 0; // current row
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
          if (numGeo >= 15000)
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

  /**
   * Saves list of tweets into a file.
   *
   * @param fileName
   * @param tweets
   * @throws IOException
   */
  public static void save(String fileName, List<Tweet> tweets) throws
      IOException {
    FileOutputStream fout = new FileOutputStream(fileName);
    ObjectOutputStream oos = new ObjectOutputStream(fout);
    oos.writeObject(tweets);
    fout.close();
  }

  /**
   * Gets list of tweets from files
   *
   * @param fileNames array of file names to get the tweets from
   * @return list of tweets
   * @throws IOException
   */
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
