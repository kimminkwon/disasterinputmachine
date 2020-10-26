package org.koreanhistory.disasterinputmachine.service;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.koreanhistory.disasterinputmachine.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MaintenanceDataServiceTest {

    @Autowired
    MaintenanceDataService service;

    @Test
    public void list테스트_조건없이() {
        // given
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);

        // when
        Page<MaintenanceDataDto> listOfDto = service.list(vo);

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
        Page<MaintenanceDataDto> listOfDto = service.list(vo);

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
        MaintenanceDataDto testDto = service.findById(testId);
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
        service.save(saveDto);
        // then
        Page<MaintenanceDataDto> listOfDto = service.list(vo);
        assertThat(listOfDto.getContent().get(0).getIndexKR()).contains(subString);
    }

    @Test
    @Transactional
    public void deleteById테스트() {
        // given
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        Page<MaintenanceDataDto> listOfDto = service.list(vo);
        Long deletedId = listOfDto.getContent().get(0).getMno();
        MaintenanceDataDto dtoOfDeleted = listOfDto.getContent().get(1);
        // when
        service.deleteById(deletedId);
        // then
        listOfDto = service.list(vo);
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
        service.modify(modifyDto);
        // then
        MaintenanceDataDto dto = service.findById(modifiedId);
        assertThat(dto.getIndexKR()).contains("Modify");
    }

    public Long getTestId(int num) {
        PageVO vo = new PageVO();
        vo.setPage(1);
        vo.setSize(10);
        Page<MaintenanceDataDto> listOfDto = service.list(vo);
        Long id = listOfDto.getContent().get(num).getMno();
        return id;
    }
}
