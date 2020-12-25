package org.koreanhistory.disasterinputmachine.mapping;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;

public class DynastyKRMapping {

    private static DynastyKRMapping dynastyKRMapping;
    private HashMap<String, String> sinraMap;
    private HashMap<String, String> goguryeoMap;
    private HashMap<String, String> baekjeMap;
    private HashMap<String, String> balhaeMap;
    private HashMap<String, String> huBaekjeMap;
    private HashMap<String, String> huGoguryeoMap;
    private HashMap<String, String> goryeoMap;
    private HashMap<String, String> joseonMap;

    public static synchronized DynastyKRMapping getInstance() {
        if(dynastyKRMapping == null)
            dynastyKRMapping = new DynastyKRMapping();

        return dynastyKRMapping;
    }

    public String[] getYearADAndAge(String dynasty, String yearNameOfTomb) {
        if(dynasty.equals("新羅") || dynasty.equals("신라") || dynasty.equals("신라(新羅)"))
            return sinraMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("高句麗") || dynasty.equals("고구려") || dynasty.equals("고구려(高句麗)"))
            return goguryeoMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("百濟") || dynasty.equals("백제") || dynasty.equals("백제(百濟)"))
            return baekjeMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("渤海") || dynasty.equals("발해") || dynasty.equals("발해(渤海)"))
            return balhaeMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("後百濟") || dynasty.equals("후백제") || dynasty.equals("후백제(後百濟)"))
            return huBaekjeMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("後高句麗") || dynasty.equals("후고구려") || dynasty.equals("후고구려(後高句麗)"))
            return huGoguryeoMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("高麗") || dynasty.equals("고려") || dynasty.equals("고려(高麗)"))
            return goryeoMap.get(yearNameOfTomb).split("-");
        else if(dynasty.equals("朝鮮") || dynasty.equals("조선") || dynasty.equals("조선(朝鮮)"))
            return joseonMap.get(yearNameOfTomb).split("-");
        else
            return new String[]{"", ""};
    }

    private DynastyKRMapping(){
        sinraMap = new HashMap<>(); goguryeoMap = new HashMap<>(); baekjeMap = new HashMap<>();
        balhaeMap = new HashMap<>(); huBaekjeMap = new HashMap<>(); huGoguryeoMap = new HashMap<>();
        goryeoMap = new HashMap<>(); joseonMap = new HashMap<>();

        try {
            makeAreaMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAreaMap() throws Exception {
        String path = "src/main/resources/mappingdata/DynastyKRMapping.xlsx";
        FileInputStream fis = new FileInputStream(path);

        Workbook workbook = new XSSFWorkbook(fis);

        makeMapForSinra(workbook); makeMapForGoguryeo(workbook);  makeMapForBaekje(workbook);
        makeMapForBalhae(workbook); makeMapForHuBaekje(workbook); makeMapForHuGoguryeo(workbook);
        makeMapForGoryeo(workbook); makeMapForJoseon(workbook);
    }

    private void makeMapForSinra(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(0);
        pushMap(worksheet, sinraMap);
    }

    private void makeMapForGoguryeo(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(1);
        pushMap(worksheet, goguryeoMap);
    }

    private void makeMapForBaekje(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(2);
        pushMap(worksheet, baekjeMap);
    }

    private void makeMapForBalhae(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(3);
        pushMap(worksheet, balhaeMap);
    }

    private void makeMapForHuBaekje(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(4);
        pushMap(worksheet, huBaekjeMap);
    }

    private void makeMapForHuGoguryeo(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(5);
        pushMap(worksheet, huGoguryeoMap);
    }

    private void makeMapForGoryeo(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(6);
        pushMap(worksheet, goryeoMap);
    }

    private void makeMapForJoseon(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(7);
        pushMap(worksheet, joseonMap);
    }

    private void pushMap(Sheet worksheet, HashMap<String, String> dynastyMap) {
        for(int i = 0; i <= worksheet.getLastRowNum(); i++) {
            Row row = worksheet.getRow(i);
            if(row != null) {
                Cell key = row.getCell(0);
                Cell value1 = row.getCell(1);
                Cell value2 = row.getCell(2);
                key.setCellType(CellType.STRING);
                value1.setCellType(CellType.STRING);
                value2.setCellType(CellType.STRING);
                String value1Str = value1.getStringCellValue() == "" ? " " : value1.getStringCellValue();
                String value2Str = value2.getStringCellValue() == "" ? " " : value2.getStringCellValue();

                dynastyMap.put(key.getStringCellValue(), value1Str + "-" + value2Str);
            }
        }
    }
}
