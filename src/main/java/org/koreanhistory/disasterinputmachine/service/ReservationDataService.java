package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log
public class ReservationDataService {

    private final ReservationDataRepository repository;
    private final DataExchangeService dataExchangeService;

    @Transactional
    public Page<ReservationDataDto> list(PageVO vo) {
        Pageable pageable = vo.makePageable(0, "rno");
        String type = vo.getType();
        String keyword = vo.getKeyword();

        log.info("TYPE" + type);
        log.info("KEYWORD" + keyword);

        List<String> types = type == null? null : splitTypesAndKeywords(type);
        List<String> keywords = keyword == null? null : splitTypesAndKeywords(keyword);
        Page<ReservationData> result = repository.findAll(repository.makePrdicates(types, keywords), pageable);
        Page<ReservationDataDto> resultOfDto = result.map(new Function<ReservationData, ReservationDataDto>() {
            @Override
            public ReservationDataDto apply(ReservationData reservationData) {
                ReservationDataDto dto = new ReservationDataDto(reservationData);
                return dto;
            }
        });

        log.info("IN ReservationDataService: list() called...");
        log.info("" + pageable);
        log.info("" + resultOfDto);

        return resultOfDto;
    }

    @Transactional
    public ReservationDataDto findById(Long rno) {
        log.info("IN ReservationDataService: findById() called...");
        ReservationData entity = repository.findById(rno)
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + rno));
        return new ReservationDataDto(entity);
    }

    @Transactional
    public void save(ReservationDataSaveDto dto) {
        log.info("IN ReservationDataService: save() called...");
        repository.save(dto.toEntity());
    }

    @Transactional
    public void modify(ReservationDataModifyDto dto) {
        log.info("IN ReservationDataService: modify() called...");
        log.info("DTO" + dto);
        ReservationData entity = repository.findById(dto.getRno())
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + dto.getRno()));
        entity.update(dto);
    }

    @Transactional
    public void deleteById(Long rno) {
        log.info("IN ReservationDataService: deleteById() called...");
        repository.deleteById(rno);
    }

    @Transactional
    public void toMaintenance(Long rno, MaintenanceDataSaveDto dto) {
        log.info("IN ReservationDataService: toReservation() called...");
        log.info("DTO" + dto);

        dataExchangeService.reservationToMaintanance(dto);
        deleteById(rno);
    }

    @Transactional
    public void toDelete(Long rno, DeleteDataSaveDto dto) {
        log.info("IN ReservationDataService: toDelete() called...");
        log.info("DTO" + dto);

        dataExchangeService.reservationToDelete(dto);
        deleteById(rno);
    }

    @Transactional
    public void toMaintenanceOnce(Long[] rnoList) {
        log.info("IN ReservationDataService: toMaintenanceOnce() called...");
        log.info("RNOLIST" + Arrays.toString(rnoList));
        dataExchangeService.reservationToMaintanance(rnoList);
    }

    @Transactional
    public void toDeleteOnce(Long[] rnoList) {
        log.info("IN ReservationDataService: toMaintenanceOnce() called...");
        log.info("RNOLIST" + Arrays.toString(rnoList));
        dataExchangeService.reservationToDelete(rnoList);
    }

    private List<String> splitTypesAndKeywords(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, "-");
        List<String> strList = new ArrayList<>();

        while(tokenizer.hasMoreTokens())
            strList.add(tokenizer.nextToken());

        return strList;
    }
}
