package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.ReservationDataDto;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log
public class ReservationDataService {

    private final ReservationDataRepository repository;

    @Transactional
    public Page<ReservationDataDto> list(PageVO vo) {
        Pageable pageable = vo.makePageable(0, "rno");
        Page<ReservationData> result = repository.findAll(repository.makePrdicate(vo.getType(), vo.getKeyword()), pageable);
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
}
