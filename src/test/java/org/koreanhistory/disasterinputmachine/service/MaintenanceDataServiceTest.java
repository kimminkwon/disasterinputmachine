package org.koreanhistory.disasterinputmachine.service;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MaintenanceDataServiceTest {

    @Autowired
    MaintenanceDataService maintenanceDataService;

    @Autowired
    ReservationDataService reservationDataService;

    @Autowired
    DeleteDataService deleteDataService;

    @Test
    public void list테스트_조건없이() {
        // given
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);

        // when
        Page<MaintenanceDataDto> listOfDto = maintenanceDataService.list(vo);

        // then
        assertThat(listOfDto.getContent().size()).isEqualTo(10);
        assertThat(listOfDto.getContent().get(0).getMno()).isGreaterThan(100);
    }

    @Test
    public void list테스트_조건() {
        // given
        PageVO vo = new PageVO();
        vo.setType("index");
        vo.setKeyword("1");
        vo.setPage(1);
        vo.setSize(10);

        // when
        Page<MaintenanceDataDto> listOfDto = maintenanceDataService.list(vo);

        // then
        listOfDto.getContent().forEach(
                maintenanceDataDto -> assertThat(maintenanceDataDto.getIndexKR()).contains("1")
        );
    }

    @Test
    public void findById테스트() {
        // given
        Long testId = getTestId(0);
        // when
        MaintenanceDataDto testDto = maintenanceDataService.findById(testId);
        // then
        assertThat(testDto.getMno()).isEqualTo(testId);
    }

    @Test
    @Transactional
    public void save테스트() {
        // given
        MakeEntityMaintenance makeEntityMaintenance = new MakeEntityMaintenance();
        String subString = "SaveTest";
        MaintenanceDataSaveDto saveDto = makeEntityMaintenance.getSaveDto(subString);
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        // when
        maintenanceDataService.save(saveDto);
        // then
        Page<MaintenanceDataDto> listOfDto = maintenanceDataService.list(vo);
        assertThat(listOfDto.getContent().get(0).getIndexKR()).contains(subString);
    }

    @Test
    @Transactional
    public void deleteById테스트() {
        // given
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        Page<MaintenanceDataDto> listOfDto = maintenanceDataService.list(vo);
        Long deletedId = listOfDto.getContent().get(0).getMno();
        MaintenanceDataDto dtoOfDeleted = listOfDto.getContent().get(1);
        // when
        maintenanceDataService.deleteById(deletedId);
        // then
        listOfDto = maintenanceDataService.list(vo);
        assertThat(listOfDto.getContent().get(0).getIndexKR()).contains(dtoOfDeleted.getIndexKR());
    }

    @Test
    @Transactional
    public void modify테스트() {
        // given
        Long modifiedId = getTestId(0);

        MakeEntityMaintenance makeEntityMaintenance = new MakeEntityMaintenance();
        MaintenanceDataModifyDto modifyDto = makeEntityMaintenance.getModifyDto("Modify");
        modifyDto.setMno(modifiedId);
        // when
        maintenanceDataService.modify(modifyDto);
        // then
        MaintenanceDataDto dto = maintenanceDataService.findById(modifiedId);
        assertThat(dto.getIndexKR()).contains("Modify");
    }

    @Test
    @Transactional
    public void toReservation테스트() {
        ReservationDataSaveDto dto = new ReservationDataSaveDto();
        maintenanceDataService.toReservation(30l, dto);

        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        Page<ReservationDataDto> list = reservationDataService.list(vo);
        assertThat(list.getContent().get(0).getIndexKR()).isNull();
    }

    @Test
    @Transactional
    public void toDelete테스트() {
        DeleteDataSaveDto dto = new DeleteDataSaveDto();
        maintenanceDataService.toDelete(30l, dto);

        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        Page<DeleteDataDto> list = deleteDataService.list(vo);
        assertThat(list.getContent().get(0).getIndexKR()).isNull();
    }

    @Test
    @Transactional
    public void toReservationOnce테스트() {
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);

        List<MaintenanceDataDto> mdtos = maintenanceDataService.list(vo).getContent();
        Long[] mnoList = {mdtos.get(0).getMno(), mdtos.get(1).getMno(), mdtos.get(2).getMno()};
        maintenanceDataService.toReservationOnce(mnoList);

        List<ReservationDataDto> rdtos = reservationDataService.list(vo).getContent();
        assertThat(rdtos.get(0).getIndexKR()).contains(mdtos.get(0).getIndexKR());
        assertThat(rdtos.get(1).getIndexKR()).contains(mdtos.get(1).getIndexKR());
        assertThat(rdtos.get(2).getIndexKR()).contains(mdtos.get(2).getIndexKR());
    }

    @Test
    @Transactional
    public void toDeleteOnce테스트() {
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);

        List<MaintenanceDataDto> mdtos = maintenanceDataService.list(vo).getContent();
        Long[] mnoList = {mdtos.get(0).getMno(), mdtos.get(1).getMno(), mdtos.get(2).getMno()};
        maintenanceDataService.toDeleteOnce(mnoList);

        List<DeleteDataDto> ddtos = deleteDataService.list(vo).getContent();
        assertThat(ddtos.get(0).getIndexKR()).contains(mdtos.get(0).getIndexKR());
        assertThat(ddtos.get(1).getIndexKR()).contains(mdtos.get(1).getIndexKR());
        assertThat(ddtos.get(2).getIndexKR()).contains(mdtos.get(2).getIndexKR());
    }

    public Long getTestId(int num) {
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        Page<MaintenanceDataDto> listOfDto = maintenanceDataService.list(vo);
        Long id = listOfDto.getContent().get(num).getMno();
        return id;
    }
}
