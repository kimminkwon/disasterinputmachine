package org.koreanhistory.disasterinputmachine.mapping;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;

public class AreaInJosunMapping {

    private static AreaInJosunMapping areaInJosunMapping;
    private HashMap<String, String> areaMap;

    public static synchronized AreaInJosunMapping getInstance() {
        if(areaInJosunMapping == null)
            areaInJosunMapping = new AreaInJosunMapping();

        return areaInJosunMapping;
    }

    public String getAreaOfChina(String korean) {
        korean = korean.trim();
        return areaMap.containsKey(korean)? areaMap.get(korean) : "";
    }

    private AreaInJosunMapping(){
        areaMap = new HashMap<>();

        try {
            makeAreaMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAreaMap() throws Exception {
        String path = "C:/Users/User/Desktop/Disaster_Input_Machine/mappingdata/AreaMappingInJosun.xlsx";
        // String path = "C:/Users/82102/Desktop/STUDY/WEB/disasterinputmachine/src/main/resources/mappingdata/AreaMappingInJosun.xlsx";
        // String path = "C:/Users/82102/Desktop/Excel/AreaMappingInJosun.xlsx";
        FileInputStream fis = new FileInputStream(path);

        Workbook workbook = new XSSFWorkbook(fis);
        Sheet worksheet = workbook.getSheetAt(0);

        for(int i = 0; i <= worksheet.getLastRowNum(); i++) {
            Row row = worksheet.getRow(i);
            if(row != null) {
                Cell key = row.getCell(0);
                Cell value = row.getCell(1);

                areaMap.put(key.getStringCellValue(), value.getStringCellValue());
            }
        }
    }
}
