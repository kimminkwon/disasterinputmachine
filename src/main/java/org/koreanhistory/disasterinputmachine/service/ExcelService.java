package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.ExcelDto;
import org.koreanhistory.disasterinputmachine.repository.DeleteDataRepository;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log
@RequiredArgsConstructor
public class ExcelService {

    private int rowNum;
    private final ReservationDataRepository reservationDataRepository;
    private final MaintenanceDataRepository maintenanceDataRepository;
    private final DeleteDataRepository deleteDataRepository;

    @Transactional
    public void save(Workbook workbook, String writer) {
        log.info("IN EXCEL SERVICE: calling save()...");

        // 1번 행을 가져옴
        Sheet worksheet = workbook.getSheetAt(0);
        List<ReservationData> rdtoList = new ArrayList<>();

        log.info("LASTROWNUMBER: " + worksheet.getLastRowNum());

        for(int i = 1; i <= worksheet.getLastRowNum(); i++) {
            Row row = worksheet.getRow(i);
            if(row != null) {
                ReservationData data = new ReservationData();
                data.setCreateBy(writer);
                data.setModifyBy(writer);

                // 총 28개 컬럼이 존재한다.
                for(int j = 0; j < 28; j++) {
                    // 예시 로그 출력
                    Cell cell = row.getCell(j);
                    if(cell != null) {
                        makeRdata(j, data, cell.getStringCellValue());
                    }
                }
                rdtoList.add(data);
                log.info("===================================" + i + "-th rdata===================================");
                log.info("" + data);
            }
        }
        reservationDataRepository.saveAll(rdtoList);
    }

    @Transactional
    public SXSSFWorkbook makeFile(String[] repositories) {
        rowNum = 2;

        // 워크북 생성
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // Header와 Body의 CellStyle 생성
        CellStyle headerStyle = makeHeaderStyleForExcel(workbook, sheet);
        CellStyle tableStyle = makeTableStyleForExcel(workbook, sheet);
        CellStyle bodyStyle = makeBodyStyleForExcel(workbook, sheet);

        // 헤더, 목차, 바디 생성
        makeHeader(sheet, headerStyle);
        makeTable(sheet, tableStyle);
        Arrays.stream(repositories).forEach(repositoryName -> makeFileForRepository(repositoryName, sheet, bodyStyle));

        return workbook;
    }

    @Transactional
    public void excelFileDownload(HttpServletResponse response, Workbook workbook) throws IOException {
        OutputStream out = null;
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
            response.setContentType("application/vnd.ms-excel");
            out = new BufferedOutputStream(response.getOutputStream());
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) out.close();
            if(workbook != null) workbook.close();
        }
    }

    private void makeFileForRepository(String repositoryName, Sheet sheet, CellStyle bodyStyle) {
        if(repositoryName.equals("maintenance")) makeFileForMaintenance(sheet, bodyStyle);
        else if(repositoryName.equals("reservation")) makeFileForReservation(sheet, bodyStyle);
        else if(repositoryName.equals("delete")) makeFileForDelete(sheet, bodyStyle);
    }

    private void makeFileForMaintenance(Sheet sheet, CellStyle bodyStyle) {
        maintenanceDataRepository.findAll().forEach(
                maintenanceData -> {
                    if(rowNum % 1000 == 0) {
                        try { ((SXSSFSheet)sheet).flushRows(1000); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                    makeRow(sheet.createRow(rowNum++), new ExcelDto(maintenanceData), bodyStyle);
                }
        );
    }

    private void makeFileForReservation(Sheet sheet, CellStyle bodyStyle) {
        reservationDataRepository.findAll().forEach(
                reservationData ->  {
                    if(rowNum % 100 == 0) {
                        try { ((SXSSFSheet)sheet).flushRows(100); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                    makeRow(sheet.createRow(rowNum++), new ExcelDto(reservationData), bodyStyle);
                }
        );
    }

    private void makeFileForDelete(Sheet sheet, CellStyle bodyStyle) {
        deleteDataRepository.findAll().forEach(
                deleteData -> {
                    if(rowNum % 100 == 0) {
                        try { ((SXSSFSheet)sheet).flushRows(100); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                    makeRow(sheet.createRow(rowNum++), new ExcelDto(deleteData), bodyStyle);
                }
        );
    }

    private void makeRow(Row row, ExcelDto excelDto, CellStyle bodyStyle) {
        Cell cell; String data;
        for(int i = 0; i < 28; i++) {
            cell = row.createCell(i);
            data = makeCell(i, excelDto);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(data);
        }
    }

    private String makeCell(int columnNum, ExcelDto excelDto) {
        String data = "";
        switch (columnNum) {
            case 0:
                data = excelDto.getClasNo();
                break;
            case 1:
                data = excelDto.getIndexKR();
                break;
            case 2:
                data = excelDto.getIndexCN();
                break;
            case 3:
                data = excelDto.getLclasKR();
                break;
            case 4:
                data = excelDto.getLclasCN();
                break;
            case 5:
                data = excelDto.getMclasKR();
                break;
            case 6:
                data = excelDto.getMclasCN();
                break;
            case 7:
                data = excelDto.getSclasKR();
                break;
            case 8:
                data = excelDto.getSclasCN();
                break;
            case 9:
                data = excelDto.getArticlSumry();
                break;
            case 10:
                data = excelDto.getArticlOrginl();
                break;
            case 11:
                data = excelDto.getLtrtreClas();
                break;
            case 12:
                data = excelDto.getLtrtreNM();
                break;
            case 13:
                data = excelDto.getSourceKR();
                break;
            case 14:
                data = excelDto.getSourceCN();
                break;
            case 15:
                data = excelDto.getYearNameOfTomb();
                break;
            case 16:
                data = excelDto.getYearAD();
                break;
            case 17:
                data = excelDto.getMonth();
                break;
            case 18:
                data = excelDto.getDynastyKR();
                break;
            case 19:
                data = excelDto.getDynastyCN();
                break;
            case 20:
                data = excelDto.getArea1KR();
                break;
            case 21:
                data = excelDto.getArea1CN();
                break;
            case 22:
                data = excelDto.getArea2KR();
                break;
            case 23:
                data = excelDto.getArea2CN();
                break;
            case 24:
                data = excelDto.getArea3KR();
                break;
            case 25:
                data = excelDto.getArea3CN();
                break;
            case 26:
                data = excelDto.getReferIndexStr();
                break;
            case 27:
                data = excelDto.getRemark();
                break;
        }

        return data;
    }

    public ReservationData makeRdata(int columnNum, ReservationData data, String value) {
        switch (columnNum) {
            case 0:
                data.setClasNo(value);
                break;
            case 1:
                data.setIndexKR(value);
                break;
            case 2:
                data.setIndexCN(value);
                break;
            case 3:
                data.setLclasKR(value);
                break;
            case 4:
                data.setLclasCN(value);
                break;
            case 5:
                data.setMclasKR(value);
                break;
            case 6:
                data.setMclasCN(value);
                break;
            case 7:
                data.setSclasKR(value);
                break;
            case 8:
                data.setSclasCN(value);
                break;
            case 9:
                data.setArticlSumry(value);
                break;
            case 10:
                data.setArticlOrginl(value);
                break;
            case 11:
                data.setLtrtreClas(value);
                break;
            case 12:
                data.setLtrtreNM(value);
                break;
            case 13:
                data.setSourceKR(value);
                break;
            case 14:
                data.setSourceCN(value);
                break;
            case 15:
                data.setYearNameOfTomb(value);
                break;
            case 16:
                data.setYearAD(value);
                break;
            case 17:
                data.setMonth(value);
                break;
            case 18:
                data.setDynastyKR(value);
                break;
            case 19:
                data.setDynastyCN(value);
                break;
            case 20:
                data.setArea1KR(value);
                break;
            case 21:
                data.setArea1CN(value);
                break;
            case 22:
                data.setArea2KR(value);
                break;
            case 23:
                data.setArea2CN(value);
                break;
            case 24:
                data.setArea3KR(value);
                break;
            case 25:
                data.setArea3CN(value);
                break;
            case 26:
                data.setReferIndex(value);
                break;
            case 27:
                data.setRemark(value);
                break;
        }
        return data;
    }

    private void makeHeader(Sheet sheet, CellStyle headerStyle) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 27));
        Row headerRow = sheet.createRow(0);
        headerRow.setHeight((short)1100);
        Cell cell = headerRow.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("DISASTER DATAs [ Sources by CUK 인문사회연구소 ]");
    }

    private void makeTable(Sheet sheet, CellStyle tableStyle) {
        String[] columnList = getColumnList();
        int[] sizeList = getSizeList();
        Row tableRow = sheet.createRow(1);
        for(int i = 0; i < columnList.length; i++) {
            Cell headerCell = tableRow.createCell(i);
            headerCell.setCellStyle(tableStyle);
            headerCell.setCellValue(columnList[i]);
            sheet.setColumnWidth(i, sizeList[i]);
        }
    }

    private CellStyle makeHeaderStyleForExcel(Workbook workbook, Sheet sheet) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderTop(BorderStyle.THIN); headerStyle.setBorderBottom(BorderStyle.THIN); headerStyle.setBorderLeft(BorderStyle.THIN); headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short)24);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        return headerStyle;
    }

    private CellStyle makeTableStyleForExcel(Workbook workbook, Sheet sheet) {
        CellStyle tableStyle = workbook.createCellStyle();
        tableStyle.setBorderTop(BorderStyle.THIN); tableStyle.setBorderBottom(BorderStyle.THIN); tableStyle.setBorderLeft(BorderStyle.THIN); tableStyle.setBorderRight(BorderStyle.THIN);
        tableStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        tableStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        tableStyle.setAlignment(HorizontalAlignment.CENTER);

        return tableStyle;
    }

    private CellStyle makeBodyStyleForExcel(Workbook workbook, Sheet sheet) {
        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setBorderTop(BorderStyle.THIN); bodyStyle.setBorderBottom(BorderStyle.THIN); bodyStyle.setBorderLeft(BorderStyle.THIN); bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setAlignment(HorizontalAlignment.LEFT);

        return bodyStyle;
    }

    private String[] getColumnList() {
        String[] columnArr = {"아이디(분류번호)", "색인어(한글)", "색인어(한자)", "대분류(한글)", "대분류(한자)", "중분류(한글)", "중분류(한자)", "소분류(한글)", "소분류(한자)", "기사 개요", "기사 원문", "문헌분류", "문헌명칭", "출전(한글)", "출전(한자)", "연도(묘호년)", "연도(서기)", "월", "왕조(한국)", "왕조(중국)", "지역1(한글)", "지역1(한자)", "지역2(한글)", "지역2(한자)", "지역3(한글)", "지역3(한자)", "참고색인어", "비고"};
        return columnArr;
    }

    private int[] getSizeList() {
        int sizeValue = 275;
        int[] sizeList = {sizeValue * 16, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 30, sizeValue * 50, sizeValue * 20, sizeValue * 20, sizeValue * 40, sizeValue * 40, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 12, sizeValue * 40, sizeValue * 20};
        return sizeList;
    }
}
