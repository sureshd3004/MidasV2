package com.midas.qa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import com.midas.qa.base.TestBase;

import io.restassured.response.Response;

public class FileUtil extends TestBase {

	static String currentDir = System.getProperty("user.dir");
	public static String TESTDATA_SHEET_PATH = currentDir+ "/src/main/java/com/midas/qa/testdata/Data.xlsx";

	static Workbook book;
	static Sheet sheet;


	public static List<String> readAllLinesFrom(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = r.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines;
	}
	public static String getValueFromJsonFile(String key) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(currentDir+"/JsonFiles/Json.json")));
			JSONObject json = new JSONObject(content);
			return json.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveJsonToFile(String json) {
		try (FileWriter file = new FileWriter(currentDir+"/JsonFiles/Json.json")) {
			file.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logDataInTxt(String injection,Response response , String fileName) throws IOException {
		String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		FileWriter writer = new FileWriter(currentDir+"/TXT/" + fileName + timestamp, true);
		String logEntry = "=============================\n" +
				"Payload: " + injection + "\n" +
				"Status Code: " + response.getStatusCode() + "\n" +
				"Response Body: \n" + response.getBody().asString() + "\n";

		// Write log entry into file
		 writer.write(logEntry);
		 writer.close();
	}
	public static Object[][] getTestDataBasedColoumn(String sheetName, String targetRole) throws IOException {
		FileInputStream file = new FileInputStream(TESTDATA_SHEET_PATH);
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();

		List<Object[]> filteredData = new ArrayList<>();
		int roleColumnIndex = -1;  // To find the "Role" column dynamically

		// Identify the column index for "Role"
		if (rowIterator.hasNext()) {
			Row headerRow = rowIterator.next();
			for (Cell cell : headerRow) {
				if (cell.getStringCellValue().equalsIgnoreCase("Role")) {
					roleColumnIndex = cell.getColumnIndex();
					break;
				}
			}
		}
		if (roleColumnIndex == -1) {
			throw new RuntimeException("Role column not found in Excel file");
		}
		// Read and filter rows based on the target role
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String role = row.getCell(roleColumnIndex).getStringCellValue();
			if (role.equalsIgnoreCase(targetRole)) {
				String userName = row.getCell(1).getStringCellValue();
				String password = row.getCell(2).getStringCellValue();
			
				filteredData.add(new Object[]{userName, password}); 
				break;
			}
		}
		workbook.close();
		file.close();
		return filteredData.toArray(new Object[0][]);
	}
	public static Object readColumnLastData(String sheetName, String columnName) {
		Object lastValue = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		try {
			// Open the Excel file
			fis = new FileInputStream(TESTDATA_SHEET_PATH);
			workbook = new XSSFWorkbook(fis);

			// Get the sheet by name
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				System.err.println("Sheet not found: " + sheetName);
				return null;  // Return null if sheet not found
			}

			// Get the header row (assuming the first row contains the column names)
			Row headerRow = sheet.getRow(0);
			int columnIdx = -1;

			// Find the column index by matching the column name
			for (Cell cell : headerRow) {
				if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					columnIdx = cell.getColumnIndex();
					break;
				}
			}

			if (columnIdx == -1) {
				System.err.println("Column not found: " + columnName);
				return null;  // Return null if column not found
			}

			// Iterate through the rows and get the last non-empty value
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next(); // Skip the header row

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell cell = row.getCell(columnIdx);
				if (cell != null) {
					// Determine the type of the cell and update lastValue accordingly
					switch (cell.getCellType()) {
					case STRING:
						lastValue = cell.getStringCellValue();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							lastValue = cell.getDateCellValue(); // Add date if it's a date cell
						} else {
							lastValue = cell.getNumericCellValue(); // Add numeric value
						}
						break;
					case BOOLEAN:
						lastValue = cell.getBooleanCellValue();
						break;
					case FORMULA:
						lastValue = cell.getCellFormula(); // Add formula if it's a formula cell
						break;
					default:
						lastValue = null; // Set null if cell type is unsupported
						break;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Return the last value found in the column
		return lastValue;
	}

	private static Sheet getSheet(String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(TESTDATA_SHEET_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) throw new IllegalArgumentException("Sheet not found: " + sheetName);
		return sheet;
	}

	private static int getColumnIndex(Sheet sheet, String columnName) {
		Row headerRow = sheet.getRow(0);
		for (Cell cell : headerRow) {
			if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
				return cell.getColumnIndex();
			}
		}
		throw new IllegalArgumentException("Column not found: " + columnName);
	}

	private static Object getCellValue(Cell cell) {
		if (cell == null) return "";
		switch (cell.getCellType()) {
			case STRING: return cell.getStringCellValue();
			case NUMERIC: return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
			case BOOLEAN: return cell.getBooleanCellValue();
			case FORMULA: return cell.getCellFormula();
			default: return "";
		}
	}

	public static Object[] readColumnData(String sheetName, String columnName) {
		List<Object> columnData = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(TESTDATA_SHEET_PATH))) {
			Sheet sheet = workbook.getSheet(sheetName);
			int columnIdx = getColumnIndex(sheet, columnName);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) columnData.add(getCellValue(row.getCell(columnIdx)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return columnData.toArray();
	}

	public static Object[] getRowDataByFirstColumnValue(String sheetName, String firstColumnValue) {
		try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(TESTDATA_SHEET_PATH))) {
			Sheet sheet = workbook.getSheet(sheetName);
			for (Row row : sheet) {
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(firstColumnValue)) {
					Object[] data = new Object[row.getLastCellNum()];
					for (int i = 0; i < row.getLastCellNum(); i++) {
						data[i] = getCellValue(row.getCell(i));
					}
					return data;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Object[0];
	}
        
	public static Object[][] getTestData(String testCaseName) {
		List<Object[]> dataList = new ArrayList<>();

		try (FileInputStream file = new FileInputStream(new File(TESTDATA_SHEET_PATH));
				Workbook workbook = new XSSFWorkbook(file)) {

			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Cell testCaseCell = row.getCell(0);

				if (testCaseCell != null && testCaseCell.getStringCellValue().equalsIgnoreCase(testCaseName)) {
					int totalColumns = row.getLastCellNum(); // Get total columns

					// Convert row data to Object array
					List<Object> rowData = new ArrayList<>();
					for (int i = 1; i < totalColumns; i++) {
						Cell cell = row.getCell(i);
						rowData.add(cell != null ? cell.getStringCellValue() : "");
					}
					dataList.add(rowData.toArray());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList.toArray(new Object[0][0]);
	}
	public static Object[][] getTestData2Array(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();				
			}
		}
		return data;
	}
	public static void sendValueToExcel(String sheetName, String columnHeader, String value) {
		try (FileInputStream fis = new FileInputStream(TESTDATA_SHEET_PATH);
				 XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

				Sheet sheet = workbook.getSheet(sheetName);
				int colIndex = getColumnIndex(sheet, columnHeader);
				int lastRow = sheet.getLastRowNum() + 1;
				Row newRow = sheet.createRow(lastRow);
				newRow.createCell(colIndex).setCellValue(value);

				try (FileOutputStream fos = new FileOutputStream(TESTDATA_SHEET_PATH)) {
					workbook.write(fos);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}