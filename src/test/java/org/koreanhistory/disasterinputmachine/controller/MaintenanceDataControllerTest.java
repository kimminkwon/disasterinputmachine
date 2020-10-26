package org.koreanhistory.disasterinputmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
    private ObjectMapper mapper;

    @Before
    public void 목과매퍼객체생성() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    public void 예제데이터삽입() {
        this.entity = new MakeEntityMaintenance().getEntity("TEST");
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

    @Test
    @Transactional
    public void 데이터수정Modify() throws Exception {
        String url = "http://localhost:" + port + "/boards/modifyOfMaintenance";
        예제데이터삽입();
        MaintenanceDataModifyDto dto = new MaintenanceDataModifyDto(entity);
        dto.setMno(100l); // 100번 데이터가 수정될 예정
        log.info("DTO: " + dto);

        mockMvc.perform(post(url).flashAttr("dto", dto));

        Optional<MaintenanceData> optionalMaintenanceData = repository.findById(100l);
        MaintenanceData entity = optionalMaintenanceData.get();
        log.info("ENTITY: " + entity);

        assertThat(entity.getMno()).isEqualTo(100l);
        assertThat(entity.getIndexKR()).contains("TEST");
    }

    @Test
    @Transactional
    public void 데이터삭제() throws Exception {
        String url = "http://localhost:" + port + "/boards/deleteOfMaintenance";

        Long beforeSize = repository.count();
        log.info("BEFORE SIZE: " + beforeSize);
        mockMvc.perform(post(url).param("mno", "106"));

        log.info("AFTER SIZE: " + repository.count());
        assertThat(repository.count()).isLessThan(beforeSize);
    }

}
