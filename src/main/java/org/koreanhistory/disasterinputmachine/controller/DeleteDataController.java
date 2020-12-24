package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.service.DeleteDataService;
import org.koreanhistory.disasterinputmachine.vo.PageMaker;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@Log
@RequiredArgsConstructor
@RequestMapping("/delete/")
public class DeleteDataController {
    private final DeleteDataService service;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageVO") PageVO vo, Model model) {
        Pageable pageable = vo.makePageable(0, "dno");
        Page<DeleteDataDto> listOfDto = service.list(vo);
        log.info("IN DELETE DATA CONTROLLER: calling list()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @GetMapping("/view")
    public void view(Long dno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN DELETE DATA CONTROLLER: view() called...");
        log.info("DNO: " + dno);
        DeleteDataDto dto = service.findById(dno);
        log.info("DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // 데이터를 저장하는 페이지로 이동한다.
    @GetMapping("/register")
    public void registerGET(@ModelAttribute("dto") DeleteDataSaveDto dto, @ModelAttribute("pageVO") PageVO vo) {
        log.info("IN DELETE DATA CONTROLLER: registerGET() called...");
    }

    // registerGet()에서 데이터를 입력 후 submit했을 경우
    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("dto") DeleteDataSaveDto dto, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: registerPOST() called...");
        log.info("SAVE DTO: " + dto);

        service.save(dto);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/delete/list";
    }

    // 복합 검색하는 페이지로 이동한다.
    @GetMapping("/search")
    public void searchGET(@ModelAttribute("pageVO") PageVO vo, @ModelAttribute("dto") SearchDto dto) {
        log.info("IN DELETE DATA CONTROLLER: searchGET() called...");
        log.info("PAGE_VO의 TYPE" + vo.getType());
        log.info("PAGE_VO의 KEYWORD" + vo.getKeyword());
    }

    // searchGET()에서 서치할 데이터를 입력 후 submit했을 경우
    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("pageVO") PageVO vo, @ModelAttribute("dto") SearchDto dto, Model model, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: searchPOST() called...");
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

        return "redirect:/delete/list";
    }

    @GetMapping("/modify")
    public void modifyGET(Long dno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN DELETE DATA CONTROLLER: modifyGET() called...");
        log.info("DNO: " + dno);
        DeleteDataDto dto = service.findById(dno);
        log.info("FIND DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // modifyGET()에서 데이터를 입력 후 Submit했을 경우
    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute("dto") DeleteDataModifyDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: modifyPOST() called...");
        log.info("MODIFY DTO: " + dto);
        service.modify(dto);

        rttr.addFlashAttribute("msg", "success");
        rttr.addAttribute("dno", dto.getDno());

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/delete/view";
    }

    @PostMapping("/delete")
    public String delete(Long dno, PageVO vo, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: delete() called...");
        log.info("DNO: " + dno);
        service.deleteById(dno);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/delete/list";
    }

    @PostMapping("/tomaintenance")
    public String toMaintenance(Long dno, @ModelAttribute("dto") MaintenanceDataSaveDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: toMaintenance() called...");
        log.info("DELETE MNO: " + dno);
        log.info("MOVING DTO: " + dto);

        service.toMaintenance(dno, dto);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/delete/list";
    }

    @PostMapping("/toreservation")
    public String toReservation(Long dno, @ModelAttribute("dto") ReservationDataSaveDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: toReservation() called...");
        log.info("DELETE MNO: " + dno);
        log.info("MOVING DTO: " + dto);

        service.toReservation(dno, dto);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/delete/list";
    }

    @GetMapping("/listforonce")
    public void listForOnce(@ModelAttribute("pageVO") PageVO vo, Model model, RedirectAttributes rttr) {
        Pageable pageable = vo.makePageable(0, "dno");
        Page<DeleteDataDto> listOfDto = service.list(vo);
        log.info("IN DELETE DATA CONTROLLER: calling listForOnce()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @PostMapping("/toreservationonce")
    public String toReservationOnce(@ModelAttribute("dnoList") Long[] dnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: calling toReservationOnce()...");
        log.info("DNOLIST" + Arrays.toString(dnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.toReservationOnce(dnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/delete/list";
    }

    @PostMapping("/tomaintenanceonce")
    public String toMaintenanceOnce(@ModelAttribute("dnoList") Long[] dnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN DELETE DATA CONTROLLER: calling toMaintenanceOnce()...");
        log.info("DNOLIST" + Arrays.toString(dnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.toMaintenanceOnce(dnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/delete/list";
    }
}
