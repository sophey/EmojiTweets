import com.vdurmont.emoji.Emoji;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Tweet implements Serializable {

  private static final long serialVersionUID = 6337662978794843238L;

  private String text;
  private double latitude;
  private double longitude;
  private String state;

  public Tweet(String text, double latitude, double longitude) throws
      IOException {
    this.text = text;
    this.latitude = latitude;
    this.longitude = longitude;
    state = new GeoLocator().getStateFromLatLongMapQuest(latitude, longitude);
  }

  public String getText() {
    return text;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getState() {
    return state;
  }

  @Override
  public String toString() {
    return "Tweet{" +
        "text='" + text + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", state='" + state + '\'' +
        '}';
  }

  /**
   * Get the frequencies of individual emojis in the tweet.
   *
   * @return HashMap of emojis to frequencies
   */
  public HashMap<Emoji, Integer> getEmojiFreq() {
    return ParseEmoji.getEmojiFrequencies(text);
  }

}
