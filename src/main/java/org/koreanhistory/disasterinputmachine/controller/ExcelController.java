package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataSaveDto;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log
@RequiredArgsConstructor
@RequestMapping("/excel/")
public class ExcelController {

    private final ReservationDataRepository repository;

    @GetMapping("/upload")
    public void upload() {
        log.info("IN EXCEL CONTROLLER: calling upload()...");
    }

    @GetMapping("/error")
    public void error() {
        log.info("IN EXCEL CONTROLLER: calling error()...");
    }

    @PostMapping("/save")
    public String save(@RequestParam("file") MultipartFile file, Authentication authenticationm, Model model) throws IOException {
        log.info("IN EXCEL CONTROLLER: calling save()...");

        String writer = ((UserDetails) authenticationm.getPrincipal()).getUsername();
        log.info("WRITER: " + writer);
        // 해당 파일의 확장자를 가져와서, 확장자가 엑셀이 아니라면 에러페이지로 리다이렉트
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!extension.equals("xlsx") && !extension.equals("xls")) return "redirect:/excel/error";

        Workbook workbook = null;

        if(extension.equals("xlsx")) workbook = new XSSFWorkbook(file.getInputStream());
        else if(extension.equals("xls")) workbook = new HSSFWorkbook(file.getInputStream());

        // 1번 행을 가져옴
        Sheet worksheet = workbook.getSheetAt(0);
        List<ReservationData> rdtoList = new ArrayList<>();

        for(int i = 1; i < worksheet.getLastRowNum(); i++) {
            Row row = worksheet.getRow(i);
            if(row != null) {
                ReservationData data = new ReservationData();
                data.setCreateBy(writer);
                data.setModifyBy(writer);

                log.info("ROW " + i + "========================================================");
                // 총 28개 컬럼이 존재한다.
                for(int j = 0; j < 28; j++) {
                    // 예시 로그 출력
                    Cell cell = row.getCell(j);
                    if(cell != null) {
                        makeRdata(j, data, cell.getStringCellValue());
                    }
                }
                rdtoList.add(data);
                log.info("" + i + "-th rdata===================================");
                log.info("" + data);
            }
        }
        repository.saveAll(rdtoList);

        return "redirect:/reservation/list";
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

}
