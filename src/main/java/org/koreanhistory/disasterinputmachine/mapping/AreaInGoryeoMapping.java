package org.koreanhistory.disasterinputmachine.mapping;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;

public class AreaInGoryeoMapping {

    private static AreaInGoryeoMapping areaInGoryeoMapping;
    private HashMap<String, String> areaMap;

    public static synchronized AreaInGoryeoMapping getInstance() {
        if(areaInGoryeoMapping == null)
            areaInGoryeoMapping = new AreaInGoryeoMapping();

        return areaInGoryeoMapping;
    }

    public String getAreaOfChina(String korean) {
        korean = korean.trim();
        return areaMap.containsKey(korean)? areaMap.get(korean) : "";
    }

    private AreaInGoryeoMapping(){
        areaMap = new HashMap<>();

        try {
            makeAreaMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAreaMap() throws Exception {
        String path = "C:/Users/User/Desktop/Disaster_Input_Machine/mappingdata/AreaMappingInGoryeo.xlsx";
        // String path = "C:/Users/82102/Desktop/STUDY/WEB/disasterinputmachine/src/main/resources/mappingdata/AreaMappingInGoryeo.xlsx";
        // String path = "C:/Users/82102/Desktop/Excel/AreaMappingInGoryeo.xlsx";
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
