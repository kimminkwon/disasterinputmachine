package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataSaveDto;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log
public class MaintenanceDataService {
    private final MaintenanceDataRepository repository;
    private final DataExchangeService dataExchangeService;

    @Transactional
    public Page<MaintenanceDataDto> list(PageVO vo) {
        Pageable pageable = vo.makePageable(0, "mno");
        Page<MaintenanceData> result = repository.findAll(repository.makePrdicate(vo.getType(), vo.getKeyword()), pageable);
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
}
