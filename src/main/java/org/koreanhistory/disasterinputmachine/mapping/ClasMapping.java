package org.koreanhistory.disasterinputmachine.mapping;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;

public class ClasMapping {

    private static ClasMapping clasMapping;
    private HashMap<String, String[]> clasMap;

    public static synchronized ClasMapping getInstance() {
        if(clasMapping == null)
            clasMapping = new ClasMapping();

        return clasMapping;
    }

    public String[] getAreaDatas(String clasNum) {
        return clasMap.get(clasNum);
    }

    private ClasMapping(){
        clasMap = new HashMap<>();
        try {
            makeAreaMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAreaMap() throws Exception {
        String path = "src/main/resources/mappingdata/ClasMapping.xlsx";
        FileInputStream fis = new FileInputStream(path);

        Workbook workbook = new XSSFWorkbook(fis);
        Sheet worksheet = workbook.getSheetAt(0);

        for(int i = 1; i <= worksheet.getLastRowNum(); i++) {
            Row row = worksheet.getRow(i);
            if(row != null) {
                String key = String.valueOf(((int) row.getCell(0).getNumericCellValue()));
                String[] value =
                        {row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(),
                                row.getCell(4).getStringCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue()};

                clasMap.put(key, value);
            }
        }
    }
}
