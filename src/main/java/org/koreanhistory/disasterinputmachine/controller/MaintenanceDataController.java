package org.koreanhistory.disasterinputmachine.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.service.MaintenanceDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Log
@RequiredArgsConstructor
@RequestMapping("/boards/")
public class MaintenanceDataController {
    private final MaintenanceDataService service;

    @GetMapping("/listOfMaintenance")
    public void listOfMaintenance() {
        log.info("IN MAINTENANCE DATA CONTROLLER: calling listOfMaintenance()...");

    }
}
