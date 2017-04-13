import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sophey on 4/13/17.
 */
public class ParseEmoji extends EmojiParser {

  public static HashMap<Emoji, Integer> getEmojiFrequencies(String text) {
    HashMap<Emoji, Integer> map = new HashMap<>();
    for (UnicodeCandidate uc : getUnicodeCandidates(text)) {
      map.put(uc.getEmoji(), map.getOrDefault(uc.getEmoji(), 0) + 1);
    }
    return map;
  }

  public static void main(String[] args) {
    HashMap<Emoji, Integer> map = getEmojiFrequencies("\uD83D\uDE01\uD83D\uDE01");
    for (Map.Entry<Emoji, Integer> entry : map.entrySet()) {
      System.out.println(entry.getKey().getUnicode() + " | " + entry.getValue());
    }
  }

}
