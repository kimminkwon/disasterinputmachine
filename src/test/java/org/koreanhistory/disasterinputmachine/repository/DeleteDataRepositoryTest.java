package org.koreanhistory.disasterinputmachine.repository;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityDelete;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class DeleteDataRepositoryTest {

    @Autowired
    private DeleteDataRepository repository;

    @Test
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    public void 데이터정상적인저장_확인() {

        MakeEntityDelete MakeEntityDelete = new MakeEntityDelete();

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(MakeEntityDelete.getEntity(String.valueOf(i)));
        }

        // then
        List<DeleteData> ddataList = Lists.newArrayList(repository.findAll());
        ddataList.forEach(
                deleteData -> log.info("IN TABLE DATA: " + deleteData)
        );

        // List의 길이 확인
        assertThat(ddataList.size()).isEqualTo(100);
        // List의 데이터 한개 확인 1
        ddataList.forEach(
                deleteData -> assertThat(deleteData.getIndexKR()).contains("indexKR")
        );
        // List의 데이터 한개 확인 2
        ddataList.forEach(
                deleteData -> assertThat(deleteData.getReferIndex()).contains("referIndex")
        );
    }

    @Test
    public void 예제데이터_삽입() {

        MakeEntityDelete MakeEntityDelete = new MakeEntityDelete();

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(MakeEntityDelete.getEntity(String.valueOf(i)));
        }

        // then
        List<DeleteData> ddataList = Lists.newArrayList(repository.findAll());
        ddataList.forEach(
                deleteData -> log.info("IN TABLE DATA: " + deleteData)
        );

        // List의 길이 확인
        assertThat(ddataList.size()).isEqualTo(100);

        // List의 데이터 한개 확인 1
        ddataList.forEach(
                deleteData -> assertThat(deleteData.getIndexKR()).contains("indexKR")
        );

        // List의 데이터 한개 확인 2
        ddataList.forEach(
                deleteData -> assertThat(deleteData.getReferIndex()).contains("referIndex")
        );
    }

    @Test
    public void 검색조건_테스트_Querydsl() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "dno");
        Page<DeleteData> result = repository.findAll(repository.makePrdicate("index", "1"), pageable);

        log.info("PAGE: " + result.getPageable());
        log.info("============================================================");
        result.getContent().forEach(
                deleteData -> assertThat(deleteData.getIndexKR()).contains("1")
        );

        result.getContent().forEach(
                deleteData -> log.info("" + deleteData)
        );
    }
}
