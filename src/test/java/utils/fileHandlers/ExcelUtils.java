package utils.fileHandlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ExcelUtils {
    private static String testCaseMasterFilePath = "src/test/resources/TestCaseMaster.xlsx";
    private static Logger logger = LogManager.getLogger(ExcelUtils.class);

    public static String readPropertyFromExcel(String sheetName, String property) throws IOException {
        logger.info("Reading Property '" + property +"' from sheet '" + sheetName + "'");
        String value = "";
        FileInputStream fis = new FileInputStream(testCaseMasterFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        XSSFRow row;
        for(int i=1;i<=sheet.getLastRowNum();i++){
            row = sheet.getRow(i);
            if(row.getCell(0).toString().equalsIgnoreCase(property)){
//                logger.info(row.getCell(0).toString() + ":" + row.getCell(1).toString());
                value = row.getCell(1).toString();
                break;
            }
        }
        logger.info("Value is : " + value );
        return value;
    }

    public static  List<Map<String,String>> readExcelSheet(String sheetName) throws IOException {
        DataFormatter formatter = new DataFormatter();
        FileInputStream fis = new FileInputStream(testCaseMasterFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(0);
        List<String> header = new LinkedList<>();
        List<Map<String,String>> data = new ArrayList<>();
        Map<String,String> rowData ;

        //loop to get headers
        for(int i=0;i<row.getLastCellNum();i++){
            header.add(formatter.formatCellValue(row.getCell(i)));
        }

        for(int i=1;i<=sheet.getLastRowNum();i++){
            rowData = new HashMap<>();
            row = sheet.getRow(i);

            for(int j=0;j< row.getLastCellNum();j++){
                rowData.put(header.get(j),formatter.formatCellValue(row.getCell(j)));
            }

            data.add(rowData);
        }
        return data;
    }
}
