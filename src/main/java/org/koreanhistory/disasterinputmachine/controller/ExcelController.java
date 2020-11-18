package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koreanhistory.disasterinputmachine.service.ExcelService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;

@Controller
@Log
@RequiredArgsConstructor
@RequestMapping("/excel/")
public class ExcelController {

    private final ExcelService service;

    @GetMapping("/upload")
    public void upload() {
        log.info("IN EXCEL CONTROLLER: calling upload()...");
    }

    @GetMapping("/error")
    public void error() {
        log.info("IN EXCEL CONTROLLER: calling error()...");
    }

    @GetMapping("/select")
    public void select() {
        log.info("IN EXCEL CONTROLLER: calling select()...");
    }

    @GetMapping("/download")
    public void download() {
        log.info("IN EXCEL CONTROLLER: calling download()...");
    }

    @PostMapping("/filedownload")
    public void fileDownload(@RequestParam("repositories") String[] repositories, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("IN EXCEL CONTROLLER: calling makeFile()...");
        log.info("REPOSITORIES: " + Arrays.toString(repositories));

        // 저장할 파일 경로
        String filePath = "src/main/resources/example.xlsx";
        FileOutputStream fos = new FileOutputStream(filePath);

        Workbook workbook = service.makeFile(repositories);
        workbook.write(fos);
        fos.close();

        service.excelFileDownload(response, workbook);
    }

    @PostMapping("/save")
    public String save(@RequestParam("file") MultipartFile file, Authentication authentication, Model model) throws IOException {
        log.info("IN EXCEL CONTROLLER: calling save()...");

        // 해당 파일의 확장자를 가져와서, 확장자가 엑셀이 아니라면 에러페이지로 리다이렉트
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(!extension.equals("xlsx") && !extension.equals("xls")) return "redirect:/excel/error";

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        service.save(workbook, ((UserDetails) authentication.getPrincipal()).getUsername());

        return "redirect:/reservation/list";
    }
}
