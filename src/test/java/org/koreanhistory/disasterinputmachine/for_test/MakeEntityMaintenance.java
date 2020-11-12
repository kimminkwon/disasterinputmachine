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
        String frontString = "m_";
        String createBy = frontString + "ADMIN"; String modifyBy = frontString + "ADMIN_MODIFY";
        String clasNo = frontString + "clasNo_" + subString;
        String indexKR = frontString + "indexKR_" + subString;
        String indexCN = frontString + "indexCN_" + subString;
        String lclasKR = frontString + "lclasKR_" + subString;
        String lclasCN = frontString + "lclasCN_" + subString;
        String mclasKR = frontString + "mclasKR_" + subString;
        String mclasCN = frontString + "mclasCN_" + subString;
        String sclasKR = frontString + "sclasKR_" + subString;
        String sclaeCN = frontString + "sclaeCN_" + subString;
        String articlSumry = "articlSumry: 기사의 개요, 해당 기사는 다음과 같다.";
        String articlOrginl =
                "유엔 재난위험경감사무국(UNDRR)이 최근 20년 동안 발생한 재해가 이전보다 2배 늘었다고 12일(현지시간) 발표했다. 기후변화로 인한 자연재해가 급격히 늘어난 것을 주요 원인으로 꼽았다." +
                        "\n" + "홍수가 과거 1389건에서 3254건으로 약 2배 많이 발생했고, 태풍의 발생 빈도는 과거 1457건에서 2034건으로 늘었다.";
        String ltrtreClas = frontString + "ltrtreClas_" + subString;
        String ltrtreNM = "기사 문헌의 명칭: UNUNUNUN";

        String sourceKR = frontString + "sourceKR_" + subString;
        String sourceCN = frontString + "sourceCN_" + subString;
        String yearNameOfTomb = frontString + "서기년도_" + subString;
        String yearAD = frontString + "yearAD_" + subString;
        String month = frontString + "month_" + subString;
        String dynastyKR = frontString + "dynastyKR_" + subString;
        String dynastyCN = frontString + "dynastyCN_" + subString;
        String area1KR = frontString + "area1KR_" + subString;
        String area1CN = frontString + "area1CN_" + subString;
        String area2KR = frontString + "area2KR_" + subString;
        String area2CN = frontString + "area2CN_" + subString;
        String area3KR = frontString + "area3KR_" + subString;
        String area3CN = frontString + "area3CN_" + subString;
        String[] referIndex = {"referA, referB, referC, referD, referE, referF, referG, referH, referI, referJ",
                "referD, referE, referF, referG, referH, referI, referJ",
                "referA, referB, referC, referG, referH, referI, referJ",
                "referA, referD, referE, referF, referJ",
                "referA, referB, referC, referD, referE, referI, referJ",
                "referA, referB, referC, referE, referF, referG, referH, referI, referJ",
                "referA, referE, referF, referG, referH",
                "referH, referI, referJ",
                "referA, referB, referE, referF, referH, referI, referJ",
                "referA, referD, referG, referH",
                "referC, referD, referE, referH, referI, referJ",
                "referB, referJ",
                "referA, referB, referC, referG, referJ"};
        String remark = frontString + "remark_" + subString;

        MaintenanceData entity = new MaintenanceData(
                createBy, modifyBy,
                clasNo, indexKR, indexCN,
                lclasKR, lclasCN, mclasKR, mclasCN, sclasKR, sclaeCN,
                articlSumry, articlOrginl, ltrtreClas, ltrtreNM,
                sourceKR, sourceCN,
                yearNameOfTomb, yearAD, month,
                dynastyKR, dynastyCN,
                area1KR, area1CN,
                area2KR, area2CN,
                area3KR, area3CN,
                referIndex[(int) (Math.random() * referIndex.length)], remark
        );
        return entity;
    }

}
