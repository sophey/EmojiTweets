import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sophey on 4/13/17.
 */
public class ParseEmoji extends EmojiParser {

  /**
   * Checks if a string contains emojis.
   *
   * @param text
   * @return true if it does contain an emoji, false otherwise
   */
  public static boolean containsEmoji(String text) {
    return !getEmojiFrequencies(text).isEmpty();
  }

  /**
   * Get the frequencies of emojis in the text.
   *
   * @param text
   * @return HashMap of frequencies
   */
  public static HashMap<Emoji, Integer> getEmojiFrequencies(String text) {
    HashMap<Emoji, Integer> map = new HashMap<>();
    for (UnicodeCandidate uc : getUnicodeCandidates(text)) {
      map.put(uc.getEmoji(), map.getOrDefault(uc.getEmoji(), 0) + 1);
    }
    return map;
  }

}
