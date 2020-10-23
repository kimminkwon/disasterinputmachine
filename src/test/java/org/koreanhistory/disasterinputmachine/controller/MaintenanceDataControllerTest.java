package org.koreanhistory.disasterinputmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MaintenanceDataControllerTest {

    @LocalServerPort
    private int port; // LocalServerPort가 저장된다.

    @Autowired
    private MaintenanceDataRepository repository;

    @Autowired
    private MaintenanceDataController controller;

    private MockMvc mockMvc;
    private MaintenanceData entity;

    @Before
    public void 목객체생성() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    public void 예제데이터삽입() {
        // given: 예제 데이터 작성
        String indexKR = "indexKR_TEST";
        String indexCN = "indexCN_TEST";
        String lclasKR = "lclasKR_TEST";
        String lclasCN = "lclasCN_TEST";
        String mclasKR = "mclasKR_TEST";
        String mclasCN = "mclasCN_TEST";
        String sclasKR = "sclasKR_TEST";
        String sclaeCN = "sclaeCN_TEST";
        String articlSumry = "articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다." +
                        "\n" + "홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreNM = "기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = "sourceKR_TEST";
        String sourceCN = "sourceCN_TEST";
        String yearNameOfTomb = "서기년도_TEST";
        int yearAD = 10;
        int month = 8;
        String nationKR = "nationKR_TEST";
        String dynastyKR = "dynastyKR_TEST";
        String nationCN = "nationCN_TEST";
        String dynastyCN = "dynastyCN_TEST";
        String area1KR = "area1KR_TEST";
        String area1CN = "area1CN_TEST";
        String area2KR = "area2KR_TEST";
        String area2CN = "area2CN_TEST";
        String area3KR = "area3KR_TEST";
        String area3CN = "area3CN_TEST";
        String referIndex = "referIndex_TEST";
        String remark = "remark_TEST";

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
    public void list정상작동_기본() throws Exception {
        String url = "http://localhost:" + port + "/boards/listOfMaintenance";

        // 기본 url로 접근시
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void list정상작동_페이징() throws Exception {
        String url = "http://localhost:" + port + "/boards/listOfMaintenance";

        // page=5 & size=10
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("page", "5");
        param.add("size", "10");
        mockMvc.perform(get(url).params(param))
                .andExpect(status().isOk());
    }

    @Test
    public void list정상작동_검색() throws Exception {
        String url = "http://localhost:" + port + "/boards/listOfMaintenance";

        // type=middle & keyword=50
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("type", "middle");
        param.add("keyword", "50");

        mockMvc.perform(get(url).params(param))
                .andExpect(status().isOk());
    }

    @Test
    public void view정상작동_기본() throws Exception {
        String url = "http://localhost:" + port + "/boards/viewOfMaintenance";

        mockMvc.perform(get(url).param("mno", "30"))
                .andExpect(status().isOk());
    }

    @Test
    public void view정상작동_페이징과검색() throws Exception {
        String url = "http://localhost:" + port + "/boards/viewOfMaintenance";

        // type, keyword, page, size, mno
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("type", "middle");
        param.add("keyword", "50");
        param.add("page", "5");
        param.add("size", "10");
        param.add("mno", "30");

        mockMvc.perform(get(url).param("mno", "30"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional // 실제로 추가되지는 않음
    public void register데이터저장() throws Exception {
        String url = "http://localhost:" + port + "/boards/registerOfMaintenance";
        예제데이터삽입();
        MaintenanceDataSaveDto dto = new MaintenanceDataSaveDto(entity);
        log.info("TEST ENTITY: " + entity);
        log.info("TEST DTO: " + dto);
        Long beforeSize = repository.count();
        mockMvc.perform(post(url).flashAttr("dto", dto));

        // 역순으로 해야 끝 값을 확인할 수 있음
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "mno");
        List<MaintenanceData> list = repository.findAll(repository.makePrdicate(null, null), pageable).getContent();

        // 이전보다 크기가 커졌는가?
        assertThat(repository.count()).isGreaterThan(beforeSize);
        // 테스트 데이터가 올바르게 들어갔는가?
        assertThat(list.get(0).getIndexKR()).contains("TEST");
    }




}
