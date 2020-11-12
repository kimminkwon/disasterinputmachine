package org.koreanhistory.disasterinputmachine.domain;

import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@Log
@SpringBootTest
public class MaintenanceDataDtoTest {

    private MaintenanceData entity;

    @Before
    public void 예제데이터삽입() {
        // given: 예제 데이터 작성
        String createBy = "ADMIN";
        String modifyBy = "ADMIN";
        String clasNo = "clasNo";
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
        String yearAD = "yearAD";
        String month = "month";
        String dynastyKR = "dynastyKR";
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
                createBy, modifyBy,
                clasNo, indexKR, indexCN,
                lclasKR, lclasCN, mclasKR, mclasCN, sclasKR, sclaeCN,
                articlSumry, articlOrginl, ltrtreNM,
                sourceKR, sourceCN,
                yearNameOfTomb, yearAD, month,
                dynastyKR, dynastyCN,
                area1KR, area1CN,
                area2KR, area2CN,
                area3KR, area3CN,
                referIndex, remark
        );
        entity.setMno(10000l);
    }

    @Test
    public void 업데이트기능확인() {
        // given: Update 용 dto 데이터 준비
        // given: 예제 데이터 작성

        String modifyBy = "업데이트 ADMIN";
        String clasNo = "업데이트 clasNo";
        String indexKR = "업데이트 indexKR";
        String indexCN = "업데이트 indexCN";
        String lclasKR = "업데이트 lclasKR";
        String lclasCN = "업데이트 lclasCN";
        String mclasKR = "업데이트 mclasKR";
        String mclasCN = "업데이트 mclasCN";
        String sclasKR = "업데이트 sclasKR";
        String sclaeCN = "업데이트 sclaeCN";
        String articlSumry = "업데이트 articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "업데이트 유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다." +
                        "\n" + "홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreNM = "업데이트 기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = "업데이트 sourceKR";
        String sourceCN = "업데이트 sourceCN";
        String yearNameOfTomb = "업데이트 서기년도";
        String yearAD = "업데이트 yearAD";
        String month = "업데이트 month";
        String dynastyKR = "업데이트 dynastyKR";
        String dynastyCN = "업데이트 dynastyCN";
        String area1KR = "업데이트 area1KR";
        String area1CN = "업데이트 area1CN";
        String area2KR = "업데이트 area2KR";
        String area2CN = "업데이트 area2CN";
        String area3KR = "업데이트 area3KR";
        String area3CN = "업데이트 area3CN";
        String referIndex = "업데이트 referIndex";
        String remark = "업데이트 remark";

        MaintenanceDataModifyDto dto = new MaintenanceDataModifyDto(
                entity.getMno(), modifyBy, clasNo, indexKR, indexCN,
                lclasKR, lclasCN, mclasKR, mclasCN, sclasKR, sclaeCN,
                articlSumry, articlOrginl, ltrtreNM,
                sourceKR, sourceCN,
                yearNameOfTomb, yearAD, month,
                dynastyKR, dynastyCN,
                area1KR, area1CN,
                area2KR, area2CN,
                area3KR, area3CN,
                referIndex, remark
        );

        // when: 업데이트 실행
        entity.update(dto);

        // then: 임의의 파라미터에서 확인
        assertThat(entity.getIndexKR()).contains("업데이트");
        assertThat(entity.getArea1KR()).contains("업데이트");
        assertThat(entity.getDynastyCN()).contains("업데이트");
        assertThat(entity.getYearNameOfTomb()).contains("업데이트");
    }

}
