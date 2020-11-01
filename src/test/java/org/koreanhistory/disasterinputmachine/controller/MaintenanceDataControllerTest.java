package org.koreanhistory.disasterinputmachine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.dto.*;
import org.koreanhistory.disasterinputmachine.for_test.MakeEntityMaintenance;
import org.koreanhistory.disasterinputmachine.repository.DeleteDataRepository;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.koreanhistory.disasterinputmachine.repository.ReservationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
    private MaintenanceDataRepository maintenanceDataRepository;
    @Autowired
    private ReservationDataRepository reservationDataRepository;
    @Autowired
    private DeleteDataRepository deleteDataRepository;

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
        String url = "http://localhost:" + port + "/maintenance/list";

        // 기본 url로 접근시
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void list정상작동_페이징() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/list";

        // page=5 & size=10
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("page", "5");
        param.add("size", "10");
        mockMvc.perform(get(url).params(param))
                .andExpect(status().isOk());
    }

    @Test
    public void list정상작동_검색() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/list";

        // type=middle & keyword=50
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("type", "middle");
        param.add("keyword", "50");

        mockMvc.perform(get(url).params(param))
                .andExpect(status().isOk());
    }

    @Test
    public void view정상작동_기본() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/view";

        mockMvc.perform(get(url).param("mno", "30"))
                .andExpect(status().isOk());
    }

    @Test
    public void view정상작동_페이징과검색() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/view";

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
        String url = "http://localhost:" + port + "/maintenance/register";
        예제데이터삽입();
        MaintenanceDataSaveDto dto = new MaintenanceDataSaveDto(entity);
        Long beforeSize = maintenanceDataRepository.count();
        mockMvc.perform(post(url).flashAttr("dto", dto));

        // 역순으로 해야 끝 값을 확인할 수 있음
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "mno");
        List<MaintenanceData> list = maintenanceDataRepository.findAll(maintenanceDataRepository.makePrdicate(null, null), pageable).getContent();

        // 이전보다 크기가 커졌는가?
        assertThat(maintenanceDataRepository.count()).isGreaterThan(beforeSize);
        // 테스트 데이터가 올바르게 들어갔는가?
        assertThat(list.get(0).getIndexKR()).contains("TEST");
    }

    @Test
    @Transactional
    public void 데이터수정Modify() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/modify";
        예제데이터삽입();
        MaintenanceDataModifyDto dto = new MaintenanceDataModifyDto(entity);
        dto.setMno(30l); // 1번 데이터가 수정될 예정
        log.info("DTO: " + dto);

        mockMvc.perform(post(url).flashAttr("dto", dto));

        Optional<MaintenanceData> optionalMaintenanceData = maintenanceDataRepository.findById(30l);
        MaintenanceData entity = optionalMaintenanceData.get();
        log.info("ENTITY: " + entity);

        assertThat(entity.getMno()).isEqualTo(30l);
        assertThat(entity.getIndexKR()).contains("TEST");
    }

    @Test
    @Transactional
    public void 데이터삭제() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/delete";

        Long beforeSize = maintenanceDataRepository.count();
        log.info("BEFORE SIZE: " + beforeSize);
        mockMvc.perform(post(url).param("mno", "1"));

        log.info("AFTER SIZE: " + maintenanceDataRepository.count());
        assertThat(maintenanceDataRepository.count()).isLessThan(beforeSize);
    }

    @Test
    public void search페이지_정상작동() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/search";

        // 기본 url로 접근시
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void serach페이지_POST후작동() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/search";
        SearchDto dto = new SearchDto();
        String typeOne = "index"; String keywordOne = "1";
        String typeTwo = "large"; String keywordTwo = "2";
        dto.setIndexKR(keywordOne);
        dto.setLclasKR(keywordTwo);

        MvcResult mvcResult = mockMvc.perform(post(url).flashAttr("dto", dto)).andReturn();
        String modelValue = mvcResult.getModelAndView().getModel().toString();
        assertThat(modelValue).contains("index-large").contains("1-2");
    }

    @Test
    public void listforonce페이지_작동() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/listforonce";

        // type, keyword, page, size, mno
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("type", "middle");
        param.add("keyword", "50");
        param.add("page", "5");
        param.add("size", "10");

        mockMvc.perform(get(url).params(param))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void toReservation_확인() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/toreservation";
        예제데이터삽입();
        ReservationDataSaveDto dto = new ReservationDataSaveDto(entity.toReservationData());
        log.info("DTO: " + dto);

        mockMvc.perform(post(url).flashAttr("dto", dto).param("mno", "30"));

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "rno");
        Page<ReservationData> datas = reservationDataRepository.findAll(reservationDataRepository.makePrdicates(null, null), pageable);
        ReservationData entity = datas.getContent().get(0);
        log.info("ENTITY: " + entity);
        assertThat(entity.getIndexKR()).contains(dto.getIndexKR());
    }

    @Test
    @Transactional
    public void toDelete_확인() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/todelete";
        예제데이터삽입();
        DeleteDataSaveDto dto = new DeleteDataSaveDto(entity.toDeleteData());
        log.info("DTO: " + dto);

        mockMvc.perform(post(url).flashAttr("dto", dto).param("mno", "30"));

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "dno");
        Page<DeleteData> datas = deleteDataRepository.findAll(deleteDataRepository.makePrdicates(null, null), pageable);
        DeleteData entity = datas.getContent().get(0);
        log.info("ENTITY: " + entity);
        assertThat(entity.getIndexKR()).contains(dto.getIndexKR());
    }

    @Test
    @Transactional
    public void toReservationOnce_확인() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/toreservationonce";
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "mno");
        Page<MaintenanceData> datas = maintenanceDataRepository.findAll(maintenanceDataRepository.makePrdicates(null, null), pageable);
        MaintenanceData entity1 = datas.getContent().get(0);
        MaintenanceData entity2 = datas.getContent().get(1);
        MaintenanceData entity3 = datas.getContent().get(2);
        Long[] mnoList = {entity1.getMno(), entity2.getMno(), entity3.getMno()};

        mockMvc.perform(post(url).flashAttr("mnoList", mnoList));

        pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "rno");
        List<ReservationData> reservationDatas = reservationDataRepository.findAll(reservationDataRepository.makePrdicates(null, null), pageable).getContent();

        assertThat(reservationDatas.get(0).getIndexKR()).contains(entity1.getIndexKR());
        assertThat(reservationDatas.get(1).getIndexKR()).contains(entity2.getIndexKR());
        assertThat(reservationDatas.get(2).getIndexKR()).contains(entity3.getIndexKR());
    }

    @Test
    @Transactional
    public void toDeleteOnce_확인() throws Exception {
        String url = "http://localhost:" + port + "/maintenance/todeleteonce";
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "mno");
        Page<MaintenanceData> datas = maintenanceDataRepository.findAll(maintenanceDataRepository.makePrdicates(null, null), pageable);
        MaintenanceData entity1 = datas.getContent().get(0);
        MaintenanceData entity2 = datas.getContent().get(1);
        MaintenanceData entity3 = datas.getContent().get(2);
        Long[] mnoList = {entity1.getMno(), entity2.getMno(), entity3.getMno()};

        mockMvc.perform(post(url).flashAttr("mnoList", mnoList));

        pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "dno");
        List<DeleteData> deleteDatas = deleteDataRepository.findAll(deleteDataRepository.makePrdicates(null, null), pageable).getContent();

        assertThat(deleteDatas.get(0).getIndexKR()).contains(entity1.getIndexKR());
        assertThat(deleteDatas.get(1).getIndexKR()).contains(entity2.getIndexKR());
        assertThat(deleteDatas.get(2).getIndexKR()).contains(entity3.getIndexKR());
    }

}
