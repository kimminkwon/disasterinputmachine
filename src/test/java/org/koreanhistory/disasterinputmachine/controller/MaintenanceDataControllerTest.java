package org.koreanhistory.disasterinputmachine.controller;

import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest
public class MaintenanceDataControllerTest {
    @LocalServerPort
    private int port; // LocalServerPort가 저장된다.

    private MockMvc mockMvc;

    @Autowired
    private MaintenanceDataRepository repository;

    private MaintenanceData entity;

    public void 예제데이터삽입() {
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
        this.entity = new MaintenanceData(
                indexKR, indexCN,
                lclasKR, lclasCN, mclasKR, mclasCN, sclasKR, sclaeCN,
                articlSumry, articlOrginl, ltrtreNM,
                sourceKR, sourceCN,
                yearNameOfTomb, yearAD, month,
                nationKR, dynastyKR, nationCN, dynastyCN,
                area1KR, area1CN,
                area2KR, area2CN,
                area3KR, area3CN,
                referIndex, remark
        );
        entity.setMno(10000l);
    }

    @Test
    public void 테스트환경적용테스트() {

    }

}
