package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.DeleteDataSaveDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataSaveDto;
import org.koreanhistory.disasterinputmachine.repository.DeleteDataRepository;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class DataExchangeService {

    private final MaintenanceDataRepository maintenanceDataRepository;
    private final ReservationDataRepository reservationDataRepository;
    private final DeleteDataRepository deleteDataRepository;

    // ======================== FOR MAINTENANCE  ======================== //
    @Transactional
    public void maintenanceToReservation(ReservationDataSaveDto dto) {
        log.info("IN DataExchangeService: maintenanceToReservation() called...");
        log.info("DTO" + dto);
        reservationDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void maintenanceToReservation(Long[] mnoArray) {
        log.info("IN DataExchangeService: maintenanceToReservation() called...");
        List<Long> mnoList = arrayToList(mnoArray);
        log.info("mno List" + mnoList);
        List<ReservationData> rdataList = new ArrayList<>();
        maintenanceDataRepository.findAllByIdInQuery(mnoList).forEach(
                maintenanceData -> rdataList.add(maintenanceData.toReservationData())
        );
        reservationDataRepository.saveAll(rdataList);
        maintenanceDataRepository.deleteAllByIdInQuery(mnoList);
    }


    @Transactional
    public void maintenanceToDelete(DeleteDataSaveDto dto) {
        log.info("IN DataExchangeService: maintenanceToDelete() called...");
        log.info("DTO" + dto);
        deleteDataRepository.save(dto.toEntity());
    }

    // ======================== FOR RESERVATION  ======================== //
    @Transactional
    public void reservationToMaintanance(MaintenanceDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToMaintanance() called...");
        log.info("DTO" + dto);
        maintenanceDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void reservationToDelete(DeleteDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToDelete() called...");
        log.info("DTO" + dto);
        deleteDataRepository.save(dto.toEntity());
    }

    // ======================== FOR DELETE  ======================== //
    @Transactional
    public void deleteToMaintanance(MaintenanceDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToMaintanance() called...");
        log.info("DTO" + dto);
        maintenanceDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void deleteToReservation(ReservationDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToDelete() called...");
        log.info("DTO" + dto);
        reservationDataRepository.save(dto.toEntity());
    }

    private List<Long> arrayToList(Long[] array) {
        return new ArrayList<Long>(Arrays.asList(array));
    }
}
