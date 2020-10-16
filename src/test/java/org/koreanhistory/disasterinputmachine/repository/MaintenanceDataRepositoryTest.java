package org.koreanhistory.disasterinputmachine.repository;

import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MaintenanceDataRepositoryTest {

    @Autowired
    MaintenanceDataRepository repository;

    @After
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    public void 데이터정상적인저장_확인() {
        // given
        String indexKR = "indexKR"; String indexCN = "indexCN";
        String lclasKR = "lclasKR"; String lclasCN = "lclasCN"; String mclasKR = "mclasKR"; String mclasCN = "mclasCN"; String sclasKR = "sclasKR"; String sclaeCN = "sclaeCN";
        String articlSumry = "articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다.\n" +
                "\n" +
                "유엔 재난위험경감사무국은 오늘 유엔이 지정한 국제재난위험경감의 날을 맞아 2000~2019년에 발생한 재해 데이터와 1980~1999년에 발생한 재해 데이터와 비교·분석한 리포트를 발표했다.\n" +
                "\n" +
                "리포트에 따르면 2000~2019년 전 세계에서 총 7348건의 재해가 발생해 123만명이 목숨을 잃었고, 약 2조 9700억 달러(약 3400조)의 경제적 손실이 발생했다. 1980~1999년에는 4212건 이 발생해 119만명이 사망하고 1조 6300억 달러(약 1870억)의 피해를 입었다. 사망자 수는 비슷하지만, 재해 발생 횟수와 경제적 손실이 약 2배씩 늘었다.\n" +
                "\n" +
                "가뭄, 산불, 극한 기온을 포함한 여러 재해 항목 중 기후변화로 인해 날씨 관련 재해의 수가 3656건에서 6681건으로 늘어난 것이 주요 원인이었다. 가장 자주 발생하는 홍수와 태풍의 경우, 최근 20년 동안 심각한 수준의 홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreNM = "기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = "sourceKR"; String sourceCN = "sourceCN";
        String yearNameOfTomb = "서기년도"; int yearAD = 10; int month = 8;
        String nationKR = "nationKR"; String dynastyKR = "dynastyKR"; String nationCN = "nationCN"; String dynastyCN = "dynastyCN";
        String area1KR = "area1KR"; String area1CN = "area1CN";
        String area2KR = "area2KR"; String area2CN = "area2CN";
        String area3KR = "area3KR"; String area3CN = "area3CN";
        String referIndex = "referIndex"; String remark = "remark";

        // when
        for(int i = 0; i < 100; i++) {
                repository.save(new MaintenanceData(
                        indexKR + "0" +  i,  indexCN + "0" +  i,
                        lclasKR + "0" +  i,  lclasCN + "0" +  i,  mclasKR + "0" +  i,  mclasCN + "0" +  i,  sclasKR + "0" +  i,  sclaeCN + "0" +  i,
                        articlSumry, articlOrginl, ltrtreNM,
                        sourceKR + "0" +  i, sourceCN + "0" +  i,
                        yearNameOfTomb, yearAD, month,
                        nationKR + "0" +  i,  dynastyKR + "0" +  i,  nationCN + "0" +  i, dynastyCN + "0" +  i,
                        area1KR + "0" +  i,  area1CN + "0" +  i,
                        area2KR + "0" +  i,  area2CN + "0" +  i,
                        area3KR + "0" +  i,  area3CN + "0" +  i,
                        referIndex + "0" +  i,  remark + "0" +  i
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
}
