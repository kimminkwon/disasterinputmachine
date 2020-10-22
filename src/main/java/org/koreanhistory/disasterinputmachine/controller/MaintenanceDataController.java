package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
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
@RequestMapping("/boards/")
public class MaintenanceDataController {
    private final MaintenanceDataService service;

    @GetMapping("/listOfMaintenance")
    public void listOfMaintenance(@ModelAttribute("pageVO") PageVO vo, Model model) {
        Pageable pageable = vo.makePageable(0, "mno");
        Page<MaintenanceDataDto> listOfDto = service.list(vo);log.info("IN MAINTENANCE DATA CONTROLLER: calling listOfMaintenance()...");
        log.info("" + pageable);
        log.info("" + listOfDto);
        log.info("TOTAL PAGE NUMBER: " + listOfDto.getTotalPages());

        model.addAttribute("listOfDto", new PageMaker(listOfDto));
    }

    @GetMapping("/viewOfMaintenance")
    public void view(Long mno, @ModelAttribute("pageVO") PageVO vo, Model model) {
        log.info("IN CONTROLLER: view() called...");
        log.info("MNO: " + mno);
        MaintenanceDataDto dto = service.findById(mno);
        log.info("DTO: " + dto);
        model.addAttribute("dto", dto);
    }

    // 데이터를 저장하는 페이지로 이동한다.
    @GetMapping("/registerOfMaintenance")
    public void registerGET(@ModelAttribute("dto") MaintenanceDataSaveDto dto) {
        log.info("IN CONTROLLER: registerGET() called...");
    }

    // registerGet()에서 데이터를 입력 후 submit했을 경우
    @PostMapping("/registerOfMaintenance")
    public String registerPOST(@ModelAttribute("dto") MaintenanceDataSaveDto dto, RedirectAttributes rttr) {
        log.info("IN CONTROLLER: registerPOST() called...");
        log.info("SAVE DTO: " + dto);

        service.save(dto);
        rttr.addFlashAttribute("msg", "success");

        return "redirect:/boards/listOfMaintenance";
    }
}
