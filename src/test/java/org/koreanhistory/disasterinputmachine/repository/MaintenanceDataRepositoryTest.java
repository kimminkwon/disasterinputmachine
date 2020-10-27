package org.koreanhistory.disasterinputmachine.repository;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MaintenanceDataRepositoryTest {

    @Autowired
    MaintenanceDataRepository repository;

    @Test
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    public void 데이터정상적인저장_확인() {

        MakeEntityMaintenance makeEntityMaintenance = new MakeEntityMaintenance();

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(makeEntityMaintenance.getEntity(String.valueOf(i)));
        }

        // then
        List<MaintenanceData> mdataList = Lists.newArrayList(repository.findAll());
        mdataList.forEach(
                maintenanceData -> log.info("IN TABLE DATA: " + maintenanceData)
        );

        // List의 길이 확인
        assertThat(mdataList.size()).isEqualTo(100);
        // List의 데이터 한개 확인 1
        mdataList.forEach(
                maintenanceData -> assertThat(maintenanceData.getIndexKR()).contains("indexKR")
        );
        // List의 데이터 한개 확인 2
        mdataList.forEach(
                maintenanceData -> assertThat(maintenanceData.getReferIndex()).contains("referIndex")
        );
    }

    @Test
    public void 예제데이터_삽입() {
        // given: 예제 데이터 작성
        MakeEntityMaintenance makeEntityMaintenance = new MakeEntityMaintenance();

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(makeEntityMaintenance.getEntity(String.valueOf(i)));
        }

        // then
        List<MaintenanceData> mdataList = Lists.newArrayList(repository.findAll());
        mdataList.forEach(
                maintenanceData -> log.info("IN TABLE DATA: " + maintenanceData)
        );

        // List의 길이 확인
        assertThat(mdataList.size()).isEqualTo(100);

        // List의 데이터 한개 확인 1
        mdataList.forEach(
                maintenanceData -> assertThat(maintenanceData.getIndexKR()).contains("indexKR")
        );

        // List의 데이터 한개 확인 2
        mdataList.forEach(
                maintenanceData -> assertThat(maintenanceData.getReferIndex()).contains("referIndex")
        );
    }

    @Test
    public void 검색조건_테스트_Querydsl() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "mno");
        Page<MaintenanceData> result = repository.findAll(repository.makePrdicate("index", "1"), pageable);

        log.info("PAGE: " + result.getPageable());
        log.info("============================================================");
        result.getContent().forEach(
                maintenanceData -> assertThat(maintenanceData.getIndexKR()).contains("1")
        );

        result.getContent().forEach(
                maintenanceData -> log.info("" + maintenanceData)
        );
    }

    @Test
    @Transactional
    public void 일괄삭제_테스트() {
        // when
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "mno");
        Page<MaintenanceData> result = repository.findAll(repository.makePrdicate("index", "1"), pageable);
        Long startedNum = result.getContent().get(0).getMno();
        List<Long> deleteNums = new ArrayList<>();
        // 6개의 숫자 지정
        deleteNums.add(startedNum); deleteNums.add(startedNum - 1); deleteNums.add(startedNum - 2); deleteNums.add(startedNum - 3); deleteNums.add(startedNum - 4); deleteNums.add(startedNum - 5);
        int listSize = deleteNums.size();
        Long repositorySize = repository.count();

        // given
        repository.deleteAllByIdInQuery(deleteNums);

        // then
        assertThat(repository.count()).isEqualTo(repositorySize - listSize);
    }

    @Test
    @Transactional
    public void 일괄검색_테스트() {
        // when
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "mno");
        Page<MaintenanceData> result = repository.findAll(repository.makePrdicate("index", "1"), pageable);
        Long startedNum = result.getContent().get(0).getMno();
        List<Long> selectNums = new ArrayList<>();
        // 6개의 숫자 지정
        selectNums.add(startedNum); selectNums.add(startedNum - 1); selectNums.add(startedNum - 2); selectNums.add(startedNum - 3); selectNums.add(startedNum - 4); selectNums.add(startedNum - 5);
        int listSize = selectNums.size();

        // given
        List<MaintenanceData> selecteList = repository.findAllByIdInQuery(selectNums);

        // then
        assertThat(selecteList.size()).isEqualTo(listSize);
    }

    @Test
    @Transactional
    public void 복합검색_테스트() {
        // when
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "mno");
        List<String> types = new ArrayList<>();
        List<String> keywords = new ArrayList<>();
        types.add("index"); types.add("large");
        keywords.add("9"); keywords.add("search");

        Page<MaintenanceData> result = repository.findAll(repository.makePrdicates(types, keywords), pageable);
        result.getContent().forEach(
                maintenanceData -> log.info("" + maintenanceData)
        );
        log.info("SIZE : " + result.getContent().size());
    }
}