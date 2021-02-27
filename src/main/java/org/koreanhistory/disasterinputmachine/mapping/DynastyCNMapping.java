package org.koreanhistory.disasterinputmachine.mapping;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.HashMap;

public class DynastyCNMapping {

    private static DynastyCNMapping dynastyCNMapping;
    private HashMap<String, String> dangMap;
    private HashMap<String, String> huLiangMap;
    private HashMap<String, String> huDangMap;
    private HashMap<String, String> huJinMap;
    private HashMap<String, String> huHanMap;
    private HashMap<String, String> huJuMap;
    private HashMap<String, String> songMap;
    private HashMap<String, String> yoMap;
    private HashMap<String, String> seohaMap;
    private HashMap<String, String> geomMap;
    private HashMap<String, String> whongMap;
    private HashMap<String, String> mongMap;
    private HashMap<String, String> chongMap;

    public static synchronized DynastyCNMapping getInstance() {
        if(dynastyCNMapping == null)
            dynastyCNMapping = new DynastyCNMapping();

        return dynastyCNMapping;
    }

    public String[] getYearADAndNameOfTomb(String dynasty, String yearAge) {
        if(dynasty.equals("唐") || dynasty.equals("당") || dynasty.equals("당(唐)"))
            return dangMap.containsKey(yearAge) ? dangMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("後梁") || dynasty.equals("후량") || dynasty.equals("후량(後梁)"))
            return huLiangMap.containsKey(yearAge) ? huLiangMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("後唐") || dynasty.equals("후당") || dynasty.equals("후당(後唐)"))
            return huDangMap.containsKey(yearAge) ? huDangMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("後晉") || dynasty.equals("후진") || dynasty.equals("후진(後晉)"))
            return huJinMap.containsKey(yearAge) ? huJinMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("後漢") || dynasty.equals("후한") || dynasty.equals("후한(後漢)"))
            return huHanMap.containsKey(yearAge) ? huHanMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("後周") || dynasty.equals("후주") || dynasty.equals("후주(後周)"))
            return huJuMap.containsKey(yearAge) ? huJuMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("宋") || dynasty.equals("송") || dynasty.equals("송(宋)"))
            return songMap.containsKey(yearAge) ? songMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("契丹(遼)") || dynasty.equals("遼") || dynasty.equals("契丹") || dynasty.equals("거란(요)") || dynasty.equals("거란") || dynasty.equals("요") || dynasty.equals("거란(요), 契丹(遼)"))
            return yoMap.containsKey(yearAge) ? yoMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("西夏") || dynasty.equals("서하") || dynasty.equals("서하(西夏)"))
            return seohaMap.containsKey(yearAge) ? seohaMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("金") || dynasty.equals("금") || dynasty.equals("금(金)"))
            return geomMap.containsKey(yearAge) ? geomMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("元") || dynasty.equals("원") || dynasty.equals("원(元)"))
            return whongMap.containsKey(yearAge) ? whongMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("明") || dynasty.equals("명") || dynasty.equals("명(明)"))
            return mongMap.containsKey(yearAge) ? mongMap.get(yearAge).split("-") : new String[]{"", ""};
        else if(dynasty.equals("淸") || dynasty.equals("청") || dynasty.equals("청(淸)"))
            return chongMap.containsKey(yearAge) ? chongMap.get(yearAge).split("-") : new String[]{"", ""};
        else
            return new String[]{"", ""};
    }

    private DynastyCNMapping(){
        dangMap = new HashMap<>(); huLiangMap = new HashMap<>(); huDangMap = new HashMap<>();
        huJinMap = new HashMap<>(); huHanMap = new HashMap<>(); huJuMap = new HashMap<>();
        songMap = new HashMap<>(); yoMap = new HashMap<>(); seohaMap = new HashMap<>();
        geomMap = new HashMap<>(); whongMap = new HashMap<>(); mongMap = new HashMap<>();
        chongMap = new HashMap<>();

        try {
            makeAreaMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAreaMap() throws Exception {
        String path = "C:/Users/User/Desktop/Disaster_Input_Machine/mappingdata/DynastyCNMapping.xlsx";
        // String path = "C:/Users/82102/Desktop/STUDY/WEB/disasterinputmachine/src/main/resources/mappingdata/DynastyCNMapping.xlsx";
        // String path = "C:/Users/82102/Desktop/Excel/DynastyCNMapping.xlsx";
        FileInputStream fis = new FileInputStream(path);

        Workbook workbook = new XSSFWorkbook(fis);

        makeMapForDang(workbook); makeMapForHuLiang(workbook);  makeMapForHuDang(workbook);
        makeMapForHuJin(workbook); makeMapForHuHan(workbook); makeMapForHuJu(workbook);
        makeMapForSong(workbook); makeMapForYo(workbook); makeMapForSeoha(workbook);
        makeMapForGeom(workbook); makeMapForWhong(workbook); makeMapForMong(workbook);
        makeMapForChong(workbook);
    }

    private void makeMapForDang(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(0);
        pushMap(worksheet, dangMap);
    }

    private void makeMapForHuLiang(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(1);
        pushMap(worksheet, huLiangMap);
    }

    private void makeMapForHuDang(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(2);
        pushMap(worksheet, huDangMap);
    }

    private void makeMapForHuJin(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(3);
        pushMap(worksheet, huJinMap);
    }

    private void makeMapForHuHan(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(4);
        pushMap(worksheet, huHanMap);
    }

    private void makeMapForHuJu(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(5);
        pushMap(worksheet, huJuMap);
    }

    private void makeMapForSong(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(6);
        pushMap(worksheet, songMap);
    }

    private void makeMapForYo(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(7);
        pushMap(worksheet, yoMap);
    }

    private void makeMapForSeoha(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(8);
        pushMap(worksheet, seohaMap);
    }

    private void makeMapForGeom(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(9);
        pushMap(worksheet, geomMap);
    }

    private void makeMapForWhong(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(10);
        pushMap(worksheet, whongMap);
    }

    private void makeMapForMong(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(11);
        pushMap(worksheet, mongMap);
    }

    private void makeMapForChong(Workbook workbook) {
        Sheet worksheet = workbook.getSheetAt(12);
        pushMap(worksheet, chongMap);
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
                if(value2 != null) value2.setCellType(CellType.STRING);
                String value1Str = value1.getStringCellValue() == "" ? " " : value1.getStringCellValue();
                String value2Str = value2 == null || value2.getStringCellValue() == "" ? " " : value2.getStringCellValue();

                dynastyMap.put(key.getStringCellValue(), value1Str + "-" + value2Str);
            }
        }
    }
}
