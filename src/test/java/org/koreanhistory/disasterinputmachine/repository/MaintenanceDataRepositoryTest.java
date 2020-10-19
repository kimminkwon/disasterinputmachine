package org.koreanhistory.disasterinputmachine.repository;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MaintenanceDataRepositoryTest {

    @Autowired
    MaintenanceDataRepository repository;

    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    public void 데이터정상적인저장_확인() {

        // given: 예제 데이터 작성
        String indexKR = "indexKR";
        String indexCN = "indexCN";
        String lclasKR = "lclasKR";
        String lclasCN = "lclasCN";
        String mclasKR = "mclasKR";
        String mclasCN = "mclasCN";
        String sclasKR = "sclasKR";
        String sclaeCN = "sclaeCN";
        String articlSumry = "articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다." +
                        "\n" + "홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreNM = "기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = "sourceKR";
        String sourceCN = "sourceCN";
        String yearNameOfTomb = "서기년도";
        int yearAD = 10;
        int month = 8;
        String nationKR = "nationKR";
        String dynastyKR = "dynastyKR";
        String nationCN = "nationCN";
        String dynastyCN = "dynastyCN";
        String area1KR = "area1KR";
        String area1CN = "area1CN";
        String area2KR = "area2KR";
        String area2CN = "area2CN";
        String area3KR = "area3KR";
        String area3CN = "area3CN";
        String referIndex = "referIndex";
        String remark = "remark";

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(new MaintenanceData(
                    indexKR + "0" + i, indexCN + "0" + i,
                    lclasKR + "0" + i, lclasCN + "0" + i, mclasKR + "0" + i, mclasCN + "0" + i, sclasKR + "0" + i, sclaeCN + "0" + i,
                    articlSumry, articlOrginl, ltrtreNM,
                    sourceKR + "0" + i, sourceCN + "0" + i,
                    yearNameOfTomb, yearAD, month,
                    nationKR + "0" + i, dynastyKR + "0" + i, nationCN + "0" + i, dynastyCN + "0" + i,
                    area1KR + "0" + i, area1CN + "0" + i,
                    area2KR + "0" + i, area2CN + "0" + i,
                    area3KR + "0" + i, area3CN + "0" + i,
                    referIndex + "0" + i, remark + "0" + i
            ));
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
                maintenanceData -> assertThat(maintenanceData.getIndexKR()).contains(indexKR)
        );
        // List의 데이터 한개 확인 2
        mdataList.forEach(
                maintenanceData -> assertThat(maintenanceData.getReferIndex()).contains(referIndex)
        );

        // Test이므로 해당 데이터 삭제
        cleanUp();
    }

    @Test
    public void 예제데이터_삽입() {
        // given: 예제 데이터 작성
        String indexKR = "indexKR";
        String indexCN = "indexCN";
        String lclasKR = "lclasKR";
        String lclasCN = "lclasCN";
        String mclasKR = "mclasKR";
        String mclasCN = "mclasCN";
        String sclasKR = "sclasKR";
        String sclaeCN = "sclaeCN";
        String articlSumry = "articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다." +
                        "\n" + "홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreNM = "기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = "sourceKR";
        String sourceCN = "sourceCN";
        String yearNameOfTomb = "서기년도";
        int yearAD = 10;
        int month = 8;
        String nationKR = "nationKR";
        String dynastyKR = "dynastyKR";
        String nationCN = "nationCN";
        String dynastyCN = "dynastyCN";
        String area1KR = "area1KR";
        String area1CN = "area1CN";
        String area2KR = "area2KR";
        String area2CN = "area2CN";
        String area3KR = "area3KR";
        String area3CN = "area3CN";
        String referIndex = "referIndex";
        String remark = "remark";

        // when: 예제 데이터 입력
        for (int i = 0; i < 100; i++) {
            repository.save(new MaintenanceData(
                    indexKR + "0" + i, indexCN + "0" + i,
                    lclasKR + "0" + i, lclasCN + "0" + i, mclasKR + "0" + i, mclasCN + "0" + i, sclasKR + "0" + i, sclaeCN + "0" + i,
                    articlSumry, articlOrginl, ltrtreNM,
                    sourceKR + "0" + i, sourceCN + "0" + i,
                    yearNameOfTomb, yearAD, month,
                    nationKR + "0" + i, dynastyKR + "0" + i, nationCN + "0" + i, dynastyCN + "0" + i,
                    area1KR + "0" + i, area1CN + "0" + i,
                    area2KR + "0" + i, area2CN + "0" + i,
                    area3KR + "0" + i, area3CN + "0" + i,
                    referIndex + "0" + i, remark + "0" + i
            ));
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
                maintenanceData -> assertThat(maintenanceData.getIndexKR()).contains(indexKR)
        );

        // List의 데이터 한개 확인 2
        mdataList.forEach(
                maintenanceData -> assertThat(maintenanceData.getReferIndex()).contains(referIndex)
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
}