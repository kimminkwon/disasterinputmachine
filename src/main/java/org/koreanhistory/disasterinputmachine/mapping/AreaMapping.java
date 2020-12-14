package org.koreanhistory.disasterinputmachine.mapping;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;

public class AreaMapping {

    private static AreaMapping areaMapping;
    private HashMap<String, String> areaMap;

    public static synchronized AreaMapping getInstance() {
        if(areaMapping == null)
            areaMapping = new AreaMapping();

        return areaMapping;
    }

    public String getAreaOfChina(String korean) {
        return areaMap.get(korean);
    }
    private AreaMapping(){
        areaMap = new HashMap<>();
        try {
            makeAreaMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAreaMap() throws Exception {
        String path = "src/main/resources/mappingdata/AreaMappingInJosun.xlsx";
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
