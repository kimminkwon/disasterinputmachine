package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.service.MaintenanceDataService;
import org.koreanhistory.disasterinputmachine.vo.PageMaker;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log
@RequiredArgsConstructor
@RequestMapping("/maintenance/")
public class MaintenanceDataController {

    private final MaintenanceDataService service;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageVO") PageVO vo, Model model) {
        Pageable pageable = vo.makePageable(0, "mno");
        Page<MaintenanceDataDto> listOfDto = service.list(vo);
        log.info("IN MAINTENANCE DATA CONTROLLER: calling list()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @GetMapping("/view")
    public void view(Long mno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN CONTROLLER: view() called...");
        log.info("RNO: " + mno);
        MaintenanceDataDto dto = service.findById(mno);
        log.info("DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // 데이터를 저장하는 페이지로 이동한다.
    @GetMapping("/register")
    public void registerGET(@ModelAttribute("dto") MaintenanceDataSaveDto dto) {
        log.info("IN CONTROLLER: registerGET() called...");
    }

    // registerGet()에서 데이터를 입력 후 submit했을 경우
    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("dto") MaintenanceDataSaveDto dto, RedirectAttributes rttr) {
        log.info("IN CONTROLLER: registerPOST() called...");
        log.info("SAVE DTO: " + dto);

        service.save(dto);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/maintenance/list";
    }

    @GetMapping("/modify")
    public void modifyGET(Long mno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN CONTROLLER: modifyGET() called...");
        log.info("MNO: " + mno);
        MaintenanceDataDto dto = service.findById(mno);
        log.info("FIND DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // modifyGET()에서 데이터를 입력 후 Submit했을 경우
    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute("dto") MaintenanceDataModifyDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN CONTROLLER: modifyPOST() called...");
        log.info("MODIFY DTO: " + dto);
        service.modify(dto);

        rttr.addFlashAttribute("msg", "success");
        rttr.addAttribute("mno", dto.getMno());

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/view";
    }

    @PostMapping("/deleteOfMaintenance")
    public String delete(Long mno, PageVO vo, RedirectAttributes rttr) {
        log.info("IN CONTROLLER: delete() called...");
        log.info("MNO: " + mno);
        service.deleteById(mno);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/list";
    }
}
