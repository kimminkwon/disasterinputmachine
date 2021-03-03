package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.service.MaintenanceDataService;
import org.koreanhistory.disasterinputmachine.vo.MaintenanceModifyOnceVO;
import org.koreanhistory.disasterinputmachine.vo.PageMaker;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

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
        log.info("IN MAINTENANCE DATA CONTROLLER: view() called...");
        log.info("PAGE_VO의 TYPE" + vo.getType());
        log.info("PAGE_VO의 KEYWORD" + vo.getKeyword());
        log.info("MNO: " + mno);
        MaintenanceDataDto dto = service.findById(mno);
        log.info("DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // 데이터를 저장하는 페이지로 이동한다.
    @GetMapping("/register")
    public void registerGET(@ModelAttribute("dto") MaintenanceDataSaveDto dto, @ModelAttribute("pageVO") PageVO vo) {
        log.info("IN MAINTENANCE DATA CONTROLLER: registerGET() called...");
    }

    // registerGet()에서 데이터를 입력 후 submit했을 경우
    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("dto") MaintenanceDataSaveDto dto, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: registerPOST() called...");
        log.info("SAVE DTO: " + dto);

        service.save(dto);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/maintenance/list";
    }

    // 복합 검색하는 페이지로 이동한다.
    @GetMapping("/search")
    public void searchGET(@ModelAttribute("pageVO") PageVO vo, @ModelAttribute("dto") SearchDto dto) {
        log.info("IN MAINTENANCE DATA CONTROLLER: searchGET() called...");
        log.info("PAGE_VO의 TYPE" + vo.getType());
        log.info("PAGE_VO의 KEYWORD" + vo.getKeyword());
    }

    // searchGET()에서 서치할 데이터를 입력 후 submit했을 경우
    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("pageVO") PageVO vo, @ModelAttribute("dto") SearchDto dto, Model model, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: searchPOST() called...");
        log.info("Search DTO: " + dto);
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + dto.getType());
        log.info("KEYWORD: " + dto.getKeyword());


        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", dto.getType());
        rttr.addAttribute("keyword", dto.getKeyword());

        // String urlData = "?page=1&type=" + dto.getType() + "&keyword=" + dto.getKeyword();
        return "redirect:/maintenance/list";
    }

    @GetMapping("/modify")
    public void modifyGET(Long mno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN MAINTENANCE DATA CONTROLLER: modifyGET() called...");
        log.info("MNO: " + mno);
        MaintenanceDataDto dto = service.findById(mno);
        log.info("FIND DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // modifyGET()에서 데이터를 입력 후 Submit했을 경우
    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute("dto") MaintenanceDataModifyDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: modifyPOST() called...");
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

    @PostMapping("/delete")
    public String delete(Long mno, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: delete() called...");
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

    @PostMapping("/toreservation")
    public String toReservation(Long mno, @ModelAttribute("dto") ReservationDataSaveDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: toReservation() called...");
        log.info("DELETE MNO: " + mno);
        log.info("MOVING DTO: " + dto);

        service.toReservation(mno, dto);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/list";
    }

    @PostMapping("/todelete")
    public String toDelete(Long mno, @ModelAttribute("dto") DeleteDataSaveDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: toDelete() called...");
        log.info("DELETE MNO: " + mno);
        log.info("MOVING DTO: " + dto);

        service.toDelete(mno, dto);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/list";
    }

    @GetMapping("/listforonce")
    public void listForOnce(@ModelAttribute("pageVO") PageVO vo, Model model, RedirectAttributes rttr) {
        Pageable pageable = vo.makePageable(0, "mno");
        Page<MaintenanceDataDto> listOfDto = service.list(vo);
        log.info("IN MAINTENANCE DATA CONTROLLER: calling listForOnce()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @PostMapping("/toreservationonce")
    public String toReservationOnce(@ModelAttribute("mnoList") Long[] mnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: calling toReservationOnce()...");
        log.info("MNOLIST" + Arrays.toString(mnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.toReservationOnce(mnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/list";
    }

    @PostMapping("/todeleteonce")
    public String toDeleteOnce(@ModelAttribute("mnoList") Long[] mnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: calling toDeleteOnce()...");
        log.info("MNOLIST" + Arrays.toString(mnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.toDeleteOnce(mnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/list";
    }

    @PostMapping("/deleteall")
    public String deleteAll(@ModelAttribute("mnoList") Long[] mnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN MAINTENANCE DATA CONTROLLER: calling deleteAll()...");
        log.info("MNOLIST" + Arrays.toString(mnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.deleteAll(mnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/maintenance/list";
    }
}
