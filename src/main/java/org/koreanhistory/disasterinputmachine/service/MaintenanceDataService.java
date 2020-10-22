package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
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
        MaintenanceData entity = repository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다. id: " + mno));
        return new MaintenanceDataDto(entity);
    }

    @Transactional
    public void save(MaintenanceDataSaveDto dto) {
        repository.save(dto.toEntity());
    }
}
