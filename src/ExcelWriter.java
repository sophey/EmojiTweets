import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ExcelWriter {

  /**
   * Writes a HashMap containing states with an emoji frequency to Excel.
   *
   * @param fileName
   * @param map
   * @throws IOException
   */
  public static void writeToExcel(String fileName, HashMap<String,
      HashMap<String, Integer>> map) throws IOException {
    FileOutputStream out = new FileOutputStream(new File(fileName));
    XSSFWorkbook workbook = new XSSFWorkbook();
    workbook.createSheet();
    Sheet sheet = workbook.getSheetAt(0);

    // HashMap to store emojis with column
    HashMap<String, Integer> emojiMap = new HashMap<>();
    // add all emojis
    int colNum = 1;
    Row row0 = sheet.createRow(0);
    for (Emoji emoji : EmojiManager.getAll()) {
      emojiMap.put(emoji.getDescription(), colNum);
      row0.createCell(colNum++).setCellValue(emoji.getDescription());
    }
    // add all states
    int rowNum = 1;
    for (String state : map.keySet()) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(state);
      for (String emojiDesc : map.get(state).keySet()) {
        row.createCell(emojiMap.get(emojiDesc)).setCellValue(map.get(state).get
            (emojiDesc));
      }
    }

    workbook.write(out);
    out.close();
  }

}
