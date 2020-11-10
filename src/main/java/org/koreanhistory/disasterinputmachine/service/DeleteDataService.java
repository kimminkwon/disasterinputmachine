package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.repository.DeleteDataRepository;
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
@Log
@RequiredArgsConstructor
public class DeleteDataService {

    private final DeleteDataRepository repository;
    private final DataExchangeService dataExchangeService;

    @Transactional
    public Page<DeleteDataDto> list(PageVO vo) {
        Pageable pageable;
        if(vo.getOrder() == null || vo.getOrder().equals("")) pageable = vo.makePageable(0, "dno");
        else pageable = vo.makePageable(1, vo.getOrder(), "dno");

        String type = vo.getType(); String keyword = vo.getKeyword();

        log.info("TYPE" + type);
        log.info("KEYWORD" + keyword);

        List<String> types = type == null? null : splitTypesAndKeywords(type);
        List<String> keywords = keyword == null? null : splitTypesAndKeywords(keyword);

        log.info("TYPES" + types);
        log.info("KEYWORDS" + keywords);

        Page<DeleteData> result = repository.findAll(repository.makePrdicates(types, keywords), pageable);
        Page<DeleteDataDto> resultOfDto = result.map(new Function<DeleteData, DeleteDataDto>() {
            @Override
            public DeleteDataDto apply(DeleteData DeleteData) {
                DeleteDataDto dto = new DeleteDataDto(DeleteData);
                return dto;
            }
        });

        log.info("IN DeleteDataService: list() called...");
        log.info("" + pageable);
        log.info("" + resultOfDto);

        return resultOfDto;
    }

    @Transactional
    public DeleteDataDto findById(Long dno) {
        log.info("IN DeleteDataService: findById() called...");
        DeleteData entity = repository.findById(dno)
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + dno));
        return new DeleteDataDto(entity);
    }

    @Transactional
    public void save(DeleteDataSaveDto dto) {
        log.info("IN DeleteDataService: save() called...");
        repository.save(dto.toEntity());
    }

    @Transactional
    public void deleteById(Long dno) {
        log.info("IN DeleteDataService: deleteById() called...");
        repository.deleteById(dno);
    }

    @Transactional
    public void modify(DeleteDataModifyDto dto) {
        log.info("IN DeleteDataService: modify() called...");
        log.info("DTO" + dto);
        DeleteData entity = repository.findById(dto.getDno())
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + dto.getDno()));
        entity.update(dto);
    }

    @Transactional
    public void toMaintenance(Long dno, MaintenanceDataSaveDto dto) {
        log.info("IN DeleteDataService: toMaintenance() called...");
        log.info("DTO" + dto);

        dataExchangeService.deleteToMaintanance(dto);
        deleteById(dno);
    }

    @Transactional
    public void toReservation(Long dno, ReservationDataSaveDto dto) {
        log.info("IN DeleteDataService: toReservation() called...");
        log.info("DTO" + dto);

        dataExchangeService.deleteToReservation(dto);
        deleteById(dno);
    }

    @Transactional
    public void toReservationOnce(Long[] dnoList) {
        log.info("IN DeleteDataService: toReservationOnce() called...");
        log.info("DNOLIST" + Arrays.toString(dnoList));
        dataExchangeService.deleteToReservation(dnoList);
    }

    @Transactional
    public void toMaintenanceOnce(Long[] dnoList) {
        log.info("IN DeleteDataService: toMaintenanceOnce() called...");
        log.info("DNOLIST" + Arrays.toString(dnoList));
        dataExchangeService.deleteToMaintanance(dnoList);
    }


    private List<String> splitTypesAndKeywords(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, "-");
        List<String> strList = new ArrayList<>();

        while(tokenizer.hasMoreTokens())
            strList.add(tokenizer.nextToken());

        return strList;
    }

}
