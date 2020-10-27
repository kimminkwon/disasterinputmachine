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

import java.util.function.Function;

@Service
@Log
@RequiredArgsConstructor
public class DeleteDataService {

    private final DeleteDataRepository repository;
    private final DataExchangeService dataExchangeService;

    @Transactional
    public Page<DeleteDataDto> list(PageVO vo) {
        Pageable pageable = vo.makePageable(0, "dno");
        Page<DeleteData> result = repository.findAll(repository.makePrdicate(vo.getType(), vo.getKeyword()), pageable);
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

}
