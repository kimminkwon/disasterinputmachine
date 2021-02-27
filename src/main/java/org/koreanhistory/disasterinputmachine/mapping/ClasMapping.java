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

    public String[] getClasDatas(String clasNum) {;
        clasNum = clasNum.trim();
        clasNum = clasNum.replace("<", "");
        clasNum = clasNum.replace(">", "");
        return clasMap.containsKey(clasNum) ? clasMap.get(clasNum) : new String[] {"", "", "", "", "", ""};
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
        String path = "C:/Users/User/Desktop/Disaster_Input_Machine/mappingdata/ClasMapping.xlsx";
        // String path = "C:/Users/82102/Desktop/STUDY/WEB/disasterinputmachine/src/main/resources/mappingdata/ClasMapping.xlsx";
        // String path = "C:/Users/82102/Desktop/Excel/ClasMapping.xlsx";
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
