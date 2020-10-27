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
    public void registerGET(@ModelAttribute("dto") DeleteDataSaveDto dto) {
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
}
