import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sophey on 4/19/17.
 */
public class TweetProcessor {

  List<Tweet> tweets;

  public TweetProcessor(List<Tweet> tweets) {
    this.tweets = tweets;
  }

  public HashMap<String, Integer> getHashMapStates() {
    HashMap<String, Integer> states = new HashMap<>();
    for (Tweet tweet : tweets) {
      states.put(tweet.getState(), states.getOrDefault(tweet.getState(), 0) + 1);
    }
    return states;
  }

  public static void printHashMap(HashMap map) {
    for (Object key : map.keySet()) {
      System.out.println(key.toString() + " : " + map.get(key));
    }
  }

  public static void main(String[] args) throws IOException {
    TweetProcessor tweetProcessor = new TweetProcessor(ExcelReader.read(ExcelReader.SAVE_FILES));
    printHashMap(tweetProcessor.getHashMapStates());
  }

}
