package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.DeleteDataSaveDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataSaveDto;
import org.koreanhistory.disasterinputmachine.repository.DeleteDataRepository;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
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

    @Transactional
    public void maintenanceToDelete(Long[] mnoArray) {
        log.info("IN DataExchangeService: maintenanceToDelete() called...");
        List<Long> mnoList = arrayToList(mnoArray);
        log.info("mno List" + mnoList);
        List<DeleteData> ddataList = new ArrayList<>();
        maintenanceDataRepository.findAllByIdInQuery(mnoList).forEach(
                maintenanceData -> ddataList.add(maintenanceData.toDeleteData())
        );
        deleteDataRepository.saveAll(ddataList);
        maintenanceDataRepository.deleteAllByIdInQuery(mnoList);
    }

    // ======================== FOR RESERVATION  ======================== //
    @Transactional
    public void reservationToMaintanance(MaintenanceDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToMaintanance() called...");
        log.info("DTO" + dto);
        maintenanceDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void reservationToMaintanance(Long[] rnoArray) {
        log.info("IN DataExchangeService: reservationToMaintanance() called...");
        List<Long> rnoList = arrayToList(rnoArray);
        log.info("rno List" + rnoList);
        List<MaintenanceData> mdataList = new ArrayList<>();
        reservationDataRepository.findAllByIdInQuery(rnoList).forEach(
                reservationData -> mdataList.add(reservationData.toMaintenanceData())
        );
        maintenanceDataRepository.saveAll(mdataList);
        reservationDataRepository.deleteAllByIdInQuery(rnoList);
    }

    @Transactional
    public void reservationToDelete(DeleteDataSaveDto dto) {
        log.info("IN DataExchangeService: reservationToDelete() called...");
        log.info("DTO" + dto);
        deleteDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void reservationToDelete(Long[] rnoArray) {
        log.info("IN DataExchangeService: reservationToDelete() called...");
        List<Long> rnoList = arrayToList(rnoArray);
        log.info("rno List" + rnoList);
        List<DeleteData> ddataList = new ArrayList<>();
        reservationDataRepository.findAllByIdInQuery(rnoList).forEach(
                reservationData -> ddataList.add(reservationData.toDeleteData())
        );
        deleteDataRepository.saveAll(ddataList);
        reservationDataRepository.deleteAllByIdInQuery(rnoList);
    }

    // ======================== FOR DELETE  ======================== //
    @Transactional
    public void deleteToMaintanance(MaintenanceDataSaveDto dto) {
        log.info("IN DataExchangeService: deleteToMaintanance() called...");
        log.info("DTO" + dto);
        maintenanceDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void deleteToMaintanance(Long[] dnoArray) {
        log.info("IN DataExchangeService: deleteToMaintanance() called...");
        List<Long> dnoList = arrayToList(dnoArray);
        log.info("dno List" + dnoList);
        List<MaintenanceData> mdataList = new ArrayList<>();
        deleteDataRepository.findAllByIdInQuery(dnoList).forEach(
                deleteData -> mdataList.add(deleteData.toMaintenanceData())
        );

        maintenanceDataRepository.saveAll(mdataList);
        deleteDataRepository.deleteAllByIdInQuery(dnoList);
    }

    @Transactional
    public void deleteToReservation(ReservationDataSaveDto dto) {
        log.info("IN DataExchangeService: deleteToReservation() called...");
        log.info("DTO" + dto);
        reservationDataRepository.save(dto.toEntity());
    }

    @Transactional
    public void deleteToReservation(Long[] dnoArray) {
        log.info("IN DataExchangeService: deleteToReservation() called...");
        List<Long> dnoList = arrayToList(dnoArray);
        log.info("dno List" + dnoList);
        List<ReservationData> rdataList = new ArrayList<>();
        deleteDataRepository.findAllByIdInQuery(dnoList).forEach(
                deleteData -> rdataList.add(deleteData.toReservationData())
        );
        reservationDataRepository.saveAll(rdataList);
        deleteDataRepository.deleteAllByIdInQuery(dnoList);

    }

    private List<Long> arrayToList(Long[] array) {
        return new ArrayList<Long>(Arrays.asList(array));
    }



}
