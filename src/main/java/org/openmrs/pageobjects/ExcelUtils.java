package org.openmrs.pageobjects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExcelUtils {
    public static List<String[]> readDataDrivenTestDataFromExcel(String excelFilePath, String sheetName) {
        List<String[]> testData = new ArrayList<>();
        try {
            File file = new File(excelFilePath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet testDataSheet = workbook.getSheet(sheetName);
            int allRowsCount = testDataSheet.getLastRowNum();
            for (int i = 1; i < allRowsCount; i++) {
                Row eachRow = testDataSheet.getRow(i);
                int columnCount = eachRow.getLastCellNum();
                String[] eachRowDataArray = new String[columnCount];
                int index = 0;
                Iterator<Cell> allCells = eachRow.cellIterator();
                while (allCells.hasNext()) {
                    Cell eachCell = allCells.next();
                    switch (eachCell.getCellType()) {
                        case STRING:
                            String strData = eachCell.getStringCellValue();
                            eachRowDataArray[index] = strData;
                            break;
                        case NUMERIC:
                            double numberData = eachCell.getNumericCellValue();
                            eachRowDataArray[index] = String.valueOf(numberData);
                            break;
                        case BLANK:
                            break;
                        default:
                            break;
                    }
                    index++;
                }
                testData.add(eachRowDataArray);
            }
            fis.close();
            return testData;
        } catch (Exception e) {
            return null;
        }
    }

    public static Iterator<Object[]> readHybridFrameworkTestDataFromExcel(String excelFilePath, String sheetName, String testCaseName) {
        try {
            File file = new File(excelFilePath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet testDataSheet = workbook.getSheet(sheetName);
            List<Map<String, String>> testData = getTestDataByTestName(testDataSheet, testCaseName);
//            System.out.println(testData);
            fis.close();
            return convertTestDataListOfMapsToListOfObjectArray(testData);
        } catch (Exception e) {
            System.out.println("Exception occurred while reading the Hydrid Driven Data from excel: "+ e.getMessage());
            return null;
        }
    }

    public static int getTestMethodStartRowIndex(XSSFSheet testDataSheet, String testCaseName) {
        try {
            for (int i = 1; i < testDataSheet.getLastRowNum(); i++) {
                if (testDataSheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK) != null) {
                    if (!testDataSheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue().trim().isEmpty()) {
                        if (testDataSheet.getRow(i).getCell(0).getStringCellValue().trim().equalsIgnoreCase(testCaseName)) {
                            return i;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred while fetching the start row index of test method" + testCaseName + " :" + e.getMessage());
        }
        return 0;
    }

    public static int getTestMethodEndRowIndex(XSSFSheet testDataSheet, String testCaseName, int startRowIndex) {
        try {
            for (int i = startRowIndex + 1; i <= testDataSheet.getLastRowNum(); i++) {
                if (testDataSheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK) != null) {
                    if (!testDataSheet.getRow(i).getCell(0, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK).getStringCellValue().trim().isEmpty()) {
                        if (testDataSheet.getRow(i).getCell(0).getStringCellValue().trim().equalsIgnoreCase(testCaseName)) {
                            return i;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred while fetching the end row index of test method" + testCaseName + " :" + e.getMessage());
        }
        return startRowIndex;
    }

    public static List<String> getTestDataColumnHeaders(XSSFSheet testDataSheet, int startRowIndex) {
        List<String> headersList = new ArrayList<>();
        try {
            Row columnHeadersRow = testDataSheet.getRow(startRowIndex + 1);
            for (int i = 1; i <= columnHeadersRow.getLastCellNum(); i++) {
                Cell eachCell = columnHeadersRow.getCell(i, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                if (eachCell == null) {
                    headersList.add("CELL NOT FOUND");
                } else if (eachCell.getStringCellValue().trim().isEmpty()) {
                    headersList.add("CELL DATA NOT FOUND");
                } else {
                    eachCell.setCellType(CellType.STRING);
                    headersList.add(eachCell.getStringCellValue().trim());
                }
            }
            return headersList;
        } catch (Exception e) {
            System.out.println("Exception Occurred while fetching the test Data Column headers: " + e.getMessage());
        }
        return null;
    }

    public static List<Map<String, String>> getTestDataByTestName(XSSFSheet testDataSheet, String testCaseName) {
        List<Map<String, String>> allTestDataMapsList = new ArrayList<>();
        try {
            int startRowIndex = getTestMethodStartRowIndex(testDataSheet, testCaseName);
            int endRowIndex = getTestMethodEndRowIndex(testDataSheet, testCaseName, startRowIndex);
            List<String> testDataColumnHeaders = getTestDataColumnHeaders(testDataSheet, startRowIndex);
            for (int i = startRowIndex + 2; i < endRowIndex; i++) {
                Map<String, String> eachRowTestData = new LinkedHashMap<>();
                Row eachTestDataRow = testDataSheet.getRow(i);
                Cell eachTestDataCell;
                for (int j = 0; j < testDataColumnHeaders.size(); j++) {
                    if (!testDataColumnHeaders.get(j).equalsIgnoreCase("CELL NOT FOUND") && !testDataColumnHeaders.get(j).equalsIgnoreCase("CELL DATA NOT FOUND")) {
                        eachTestDataCell = eachTestDataRow.getCell(j + 1, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                        if (eachTestDataCell != null) {
                            eachTestDataCell.setCellType(CellType.STRING);
                        }
                        if (eachTestDataCell != null && !eachTestDataCell.getStringCellValue().trim().isEmpty()) {
                            eachRowTestData.put(testDataColumnHeaders.get(j), eachTestDataCell.getStringCellValue().trim());
                        }
                    }
                }
                allTestDataMapsList.add(eachRowTestData);
            }
            return allTestDataMapsList;
        } catch (Exception e) {
            System.out.println("Exception Occurred while reading the Test Data for test Method " + testCaseName + " :" + e.getMessage());
            return null;
        }
    }

    public static Iterator<Object[]> convertTestDataListOfMapsToListOfObjectArray(List<Map<String, String>> testDataMapsList) {
        List<Object[]> objectArrList = new ArrayList<>();
        try {
            for (Map<String, String> eachTestDataMap : testDataMapsList) {
                objectArrList.add(new Object[]{eachTestDataMap});
            }
            return objectArrList.iterator();
        } catch (Exception e) {
            System.out.println("Exception occurred while converting ListOfMaps data to ListOf ObjectArray : " + e.getMessage());
            return null;
        }
    }
}
