package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.service.ReservationDataService;
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
@RequestMapping("/reservation/")
public class ReservationDataController {

    private final ReservationDataService service;

    @GetMapping("/list")
    public void list(@ModelAttribute("pageVO") PageVO vo, Model model) {
        Pageable pageable = vo.makePageable(0, "rno");
        Page<ReservationDataDto> listOfDto = service.list(vo);
        log.info("IN RESERVATION DATA CONTROLLER: calling list()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @GetMapping("/view")
    public void view(Long rno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN RESERVATION DATA CONTROLLER: view() called...");
        log.info("RNO: " + rno);
        ReservationDataDto dto = service.findById(rno);
        log.info("DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // 데이터를 저장하는 페이지로 이동한다.
    @GetMapping("/register")
    public void registerGET(@ModelAttribute("dto") ReservationDataSaveDto dto, @ModelAttribute("pageVO") PageVO vo) {
        log.info("IN RESERVATION DATA CONTROLLER: registerGET() called...");
    }

    // searchGET()에서 서치할 데이터를 입력 후 submit했을 경우
    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("pageVO") PageVO vo, @ModelAttribute("dto") SearchDto dto, RedirectAttributes rttr) {
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

        return "redirect:/reservation/list";
    }

    // registerGet()에서 데이터를 입력 후 submit했을 경우
    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("dto") ReservationDataSaveDto dto, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: registerPOST() called...");
        log.info("SAVE DTO: " + dto);

        service.save(dto);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/reservation/list";
    }

    // 복합 검색하는 페이지로 이동한다.
    @GetMapping("/search")
    public void searchGET(@ModelAttribute("pageVO") PageVO vo, @ModelAttribute("dto") SearchDto dto) {
        log.info("IN RESERVATION DATA CONTROLLER: searchGET() called...");
        log.info("PAGE_VO의 TYPE" + vo.getType());
        log.info("PAGE_VO의 KEYWORD" + vo.getKeyword());
    }

    @GetMapping("/modify")
    public void modifyGET(Long rno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN RESERVATION DATA CONTROLLER: modifyGET() called...");
        log.info("RNO: " + rno);
        ReservationDataDto dto = service.findById(rno);
        log.info("FIND DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // modifyGET()에서 데이터를 입력 후 Submit했을 경우
    @PostMapping("/modify")
    public String modifyPOST(@ModelAttribute("dto") ReservationDataModifyDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: modifyPOST() called...");
        log.info("MODIFY DTO: " + dto);
        service.modify(dto);

        rttr.addFlashAttribute("msg", "success");
        rttr.addAttribute("rno", dto.getRno());

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/view";
    }

    @PostMapping("/delete")
    public String delete(Long rno, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: delete() called...");
        log.info("RNO: " + rno);
        service.deleteById(rno);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/list";
    }

    @PostMapping("/tomaintenance")
    public String toMaintenance(Long rno, @ModelAttribute("dto") MaintenanceDataSaveDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: toMaintenance() called...");
        log.info("DELETE RNO: " + rno);
        log.info("MOVING DTO: " + dto);

        service.toMaintenance(rno, dto);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/list";
    }

    @PostMapping("/todelete")
    public String toDelete(Long rno, @ModelAttribute("dto") DeleteDataSaveDto dto, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: toDelete() called...");
        log.info("DELETE RNO: " + rno);
        log.info("MOVING DTO: " + dto);

        service.toDelete(rno, dto);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/list";
    }

    @GetMapping("/listforonce")
    public void listForOnce(@ModelAttribute("pageVO") PageVO vo, Model model, RedirectAttributes rttr) {
        Pageable pageable = vo.makePageable(0, "rno");
        Page<ReservationDataDto> listOfDto = service.list(vo);
        log.info("IN RESERVATION DATA CONTROLLER: calling listForOnce()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @PostMapping("/tomaintenanceonce")
    public String toMaintenanceOnce(@ModelAttribute("rnoList") Long[] rnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: calling toMaintenanceOnce()...");
        log.info("RNOLIST" + Arrays.toString(rnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.toMaintenanceOnce(rnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/list";
    }

    @PostMapping("/todeleteonce")
    public String toDeleteOnce(@ModelAttribute("rnoList") Long[] rnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: calling toDeleteOnce()...");
        log.info("RNOLIST" + Arrays.toString(rnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.toDeleteOnce(rnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/list";
    }

    @PostMapping("/deleteall")
    public String deleteAll(@ModelAttribute("rnoList") Long[] rnoList, PageVO vo, RedirectAttributes rttr) {
        log.info("IN RESERVATION DATA CONTROLLER: calling deleteAll()...");
        log.info("RNOLIST" + Arrays.toString(rnoList));
        log.info("PAGE: " + vo.getPage());
        log.info("SIZE: " + vo.getSize());
        log.info("TYPE: " + vo.getType());
        log.info("KEYWORD: " + vo.getKeyword());

        service.deleteAll(rnoList);

        rttr.addFlashAttribute("msg", "success");

        // Paging과 검색 결과를 유지하기 위한 데이터 보내기
        rttr.addAttribute("page", vo.getPage());
        rttr.addAttribute("size", vo.getSize());
        rttr.addAttribute("type", vo.getType());
        rttr.addAttribute("keyword", vo.getKeyword());

        return "redirect:/reservation/list";
    }
}
