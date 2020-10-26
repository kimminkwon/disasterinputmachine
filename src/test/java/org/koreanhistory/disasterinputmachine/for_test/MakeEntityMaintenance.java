package org.koreanhistory.disasterinputmachine.for_test;

import lombok.NoArgsConstructor;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataSaveDto;

@NoArgsConstructor
public class MakeEntityMaintenance {

    public MaintenanceDataModifyDto getModifyDto(String subString) {
        MaintenanceData entity = getEntity(subString);
        MaintenanceDataModifyDto modifyDto = new MaintenanceDataModifyDto(entity);
        return modifyDto;
    }

    public MaintenanceDataSaveDto getSaveDto(String subString) {
        MaintenanceData entity = getEntity(subString);
        MaintenanceDataSaveDto saveDto = new MaintenanceDataSaveDto(entity);
        return saveDto;
    }
    public MaintenanceDataDto getDto(String subString) {
        MaintenanceData entity = getEntity(subString);
        MaintenanceDataDto dto = new MaintenanceDataDto(entity);
        return dto;
    }
    public MaintenanceData getEntity(String subString) {
        String indexKR = "indexKR_" + subString;
        String indexCN = "indexCN_" + subString;
        String lclasKR = "lclasKR_" + subString;
        String lclasCN = "lclasCN_" + subString;
        String mclasKR = "mclasKR_" + subString;
        String mclasCN = "mclasCN_" + subString;
        String sclasKR = "sclasKR_" + subString;
        String sclaeCN = "sclaeCN_" + subString;
        String articlSumry = "articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다." +
                        "\n" + "홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreNM = "기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = "sourceKR_" + subString;
        String sourceCN = "sourceCN_" + subString;
        String yearNameOfTomb = "서기년도_" + subString;
        int yearAD = 10;
        int month = 8;
        String nationKR = "nationKR_" + subString;
        String dynastyKR = "dynastyKR_" + subString;
        String nationCN = "nationCN_" + subString;
        String dynastyCN = "dynastyCN_" + subString;
        String area1KR = "area1KR_" + subString;
        String area1CN = "area1CN_" + subString;
        String area2KR = "area2KR_" + subString;
        String area2CN = "area2CN_" + subString;
        String area3KR = "area3KR_" + subString;
        String area3CN = "area3CN_" + subString;
        String referIndex = "referIndex_" + subString;
        String remark = "remark_" + subString;

        MaintenanceData entity = new MaintenanceData(
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
        return entity;
    }

}
