import com.vdurmont.emoji.Emoji;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Tweet implements Serializable {

  private String text;
  private double latitude;
  private double longitude;
  private String state;

  public Tweet(String text, double latitude, double longitude) throws IOException {
    this.text = text;
    this.latitude = latitude;
    this.longitude = longitude;
    state = new GeoLocator().getStateFromLatLongMapQuest(latitude, longitude);
    state = "";
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

  @Override
  public String toString() {
    return "Tweet{" +
        "text='" + text + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", state='" + state + '\'' +
        '}';
  }

  public HashMap<Emoji, Integer> getEmojiFreq() {
    return ParseEmoji.getEmojiFrequencies(text);
  }

}
