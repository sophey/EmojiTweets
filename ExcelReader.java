import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	List<Tweet> tweets = new ArrayList<Tweet>();
	Workbook workbook;
	Sheet sheet;
	
	/**
	 * Iterate through the spreadsheet and create a new Tweet object for each row.
	 */
	public static void main(String[] args) throws IOException {
	    
		String excelFilePath = "tweets.xlsx";
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	     
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet sheet = workbook.getSheetAt(0);
	    
	    iterate(sheet);
	    
	    workbook.close();
        inputStream.close();
    }
	
	public void iterate(Sheet sheet)
	{
		Iterator<Row> iterator = sheet.iterator();
	    //for each row in the sheet
	    while (iterator.hasNext()) {
	    	Row nextRow = iterator.next();
	    	tweets.add(getTweet(nextRow));
	    }
	}
	
	
	/**
	 * Get the values of the cells in the row, create a new Tweet object.
	 * @param cell
	 * @return
	 */
	private Tweet getTweet(Row row) {
	    // column B (1) = tweet
	    // column C (2) = latitude
	    // column D (3) = longitude
		if ((row.getCell(1) != null) && (row.getCell(2) != null) && (row.getCell(3) != null)){
			Tweet newTweet;
			String text = row.getCell(1).getStringCellValue();
			double lat = row.getCell(2).getNumericCellValue();
			double lon = row.getCell(3).getNumericCellValue();
			newTweet = new Tweet(text, lat, lon);
			return newTweet;
		}
		else
			return null;
	}
}
