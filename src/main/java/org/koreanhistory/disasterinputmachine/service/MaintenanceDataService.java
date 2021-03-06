package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.vo.MaintenanceModifyOnceVO;
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
public class MaintenanceDataService {
    private final MaintenanceDataRepository repository;
    private final DataExchangeService dataExchangeService;

    @Transactional
    public Page<MaintenanceDataDto> list(PageVO vo) {
        Pageable pageable;
        if(vo.getOrder() == null || vo.getOrder().equals("")) pageable = vo.makePageable(0, "mno");
        else pageable = vo.makePageable(1, vo.getOrder(), "mno");

        String type = vo.getType(); String keyword = vo.getKeyword();

        log.info("TYPE" + type);
        log.info("KEYWORD" + keyword);

        List<String> types = type == null? null : splitTypesAndKeywords(type);
        List<String> keywords = keyword == null? null : splitTypesAndKeywords(keyword);

        log.info("TYPES" + types);
        log.info("KEYWORDS" + keywords);

        Page<MaintenanceData> result = repository.findAll(repository.makePrdicates(types, keywords), pageable);
        Page<MaintenanceDataDto> resultOfDto = result.map(new Function<MaintenanceData, MaintenanceDataDto>() {
            @Override
            public MaintenanceDataDto apply(MaintenanceData maintenanceData) {
                MaintenanceDataDto dto = new MaintenanceDataDto(maintenanceData);
                return dto;
            }
        });

        log.info("IN MaintenanceDataService: list() called...");
        log.info("" + pageable);
        log.info("" + resultOfDto);

        return resultOfDto;
    }

    @Transactional
    public MaintenanceDataDto findById(Long mno) {
        log.info("IN MaintenanceDataService: findById() called...");
        MaintenanceData entity = repository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + mno));
        return new MaintenanceDataDto(entity);
    }

    @Transactional
    public void save(MaintenanceDataSaveDto dto) {
        log.info("IN MaintenanceDataService: save() called...");
        repository.save(dto.toEntity());
    }

    @Transactional
    public void deleteById(Long mno) {
        log.info("IN MaintenanceDataService: deleteById() called...");
        repository.deleteById(mno);
    }

    @Transactional
    public void modify(MaintenanceDataModifyDto dto) {
        log.info("IN MaintenanceDataService: modify() called...");
        log.info("DTO" + dto);
        MaintenanceData entity = repository.findById(dto.getMno())
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + dto.getMno()));
        entity.update(dto);
    }

    @Transactional
    public void toReservation(Long mno, ReservationDataSaveDto dto) {
        log.info("IN MaintenanceDataService: toReservation() called...");
        log.info("DTO" + dto);

        dataExchangeService.maintenanceToReservation(dto);
        deleteById(mno);
    }

    @Transactional
    public void toDelete(Long mno, DeleteDataSaveDto dto) {
        log.info("IN MaintenanceDataService: toDelete() called...");
        log.info("DTO" + dto);

        dataExchangeService.maintenanceToDelete(dto);
        deleteById(mno);

    }

    @Transactional
    public void toReservationOnce(Long[] mnoList) {
        log.info("IN MaintenanceDataService: toReservationOnce() called...");
        log.info("MNOLIST" + Arrays.toString(mnoList));
        dataExchangeService.maintenanceToReservation(mnoList);
    }

    @Transactional
    public void toDeleteOnce(Long[] mnoList) {
        log.info("IN MaintenanceDataService: toDelete() called...");
        log.info("MNOLIST" + Arrays.toString(mnoList));
        dataExchangeService.maintenanceToDelete(mnoList);
    }

    @Transactional
    public void deleteAll(Long[] mnoList) {
        log.info("IN MaintenanceDataService: deleteAll() called...");
        log.info("MNOLIST" + Arrays.toString(mnoList));
        repository.deleteAllByIdInQuery(Arrays.asList(mnoList));
    }

    private List<String> splitTypesAndKeywords(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, "-");
        List<String> strList = new ArrayList<>();

        while(tokenizer.hasMoreTokens())
            strList.add(tokenizer.nextToken());

        return strList;
    }

}
