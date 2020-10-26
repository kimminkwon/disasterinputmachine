package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataSaveDto;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log
public class DataExchangeService {

    private final MaintenanceDataRepository maintenanceDataRepository;
    private final ReservationDataRepository reservationDataRepository;

    @Transactional
    public void maintenanceToReservation(ReservationDataSaveDto dto) {
        log.info("IN DataExchangeService: maintenanceToReservation() called...");
        log.info("DTO" + dto);
        reservationDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void reservationToMaintanance(MaintenanceDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToMaintanance() called...");
        log.info("DTO" + dto);
        maintenanceDataRepository.save(dto.toEntity());
    }
}
