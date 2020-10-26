package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataDto;
import org.koreanhistory.disasterinputmachine.service.ReservationDataService;
import org.koreanhistory.disasterinputmachine.vo.PageMaker;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
