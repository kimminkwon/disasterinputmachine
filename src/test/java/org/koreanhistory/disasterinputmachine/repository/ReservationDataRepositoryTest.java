package org.koreanhistory.disasterinputmachine.repository;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class ReservationDataRepositoryTest {

    @Autowired
    private ReservationDataRepository repository;

    @Test
    @Transactional
    public void 데이터정상적인저장_확인() {

        MakeEntityReservation makeEntityReservation = new MakeEntityReservation();

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(makeEntityReservation.getEntity(String.valueOf(i)));
        }

        // then
        List<ReservationData> rdataList = Lists.newArrayList(repository.findAll());
        rdataList.forEach(
                reservationData -> log.info("IN TABLE DATA: " + reservationData)
        );

        // List의 길이 확인
        assertThat(rdataList.size()).isEqualTo(100);
        // List의 데이터 한개 확인 1
        rdataList.forEach(
                reservationData -> assertThat(reservationData.getIndexKR()).contains("indexKR")
        );
        // List의 데이터 한개 확인 2
        rdataList.forEach(
                reservationData -> assertThat(reservationData.getReferIndex()).contains("referIndex")
        );
    }

    @Test
    public void 예제데이터_삽입() {
        // given: 예제 데이터 작성
        MakeEntityReservation makeEntityReservation = new MakeEntityReservation();

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(makeEntityReservation.getEntity(String.valueOf(i)));
        }

        // then
        List<ReservationData> rdataList = Lists.newArrayList(repository.findAll());
        rdataList.forEach(
                reservationData -> log.info("IN TABLE DATA: " + reservationData)
        );

        // List의 길이 확인
        assertThat(rdataList.size()).isEqualTo(100);

        // List의 데이터 한개 확인 1
        rdataList.forEach(
                reservationData -> assertThat(reservationData.getIndexKR()).contains("indexKR")
        );

        // List의 데이터 한개 확인 2
        rdataList.forEach(
                reservationData -> assertThat(reservationData.getReferIndex()).contains("referIndex")
        );
    }

    @Test
    public void 검색조건_테스트_Querydsl() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "rno");
        Page<ReservationData> result = repository.findAll(repository.makePrdicate("index", "1"), pageable);

        log.info("PAGE: " + result.getPageable());
        log.info("============================================================");
        result.getContent().forEach(
                reservationData -> assertThat(reservationData.getIndexKR()).contains("1")
        );

        result.getContent().forEach(
                reservationData -> log.info("" + reservationData)
        );
    }
}
