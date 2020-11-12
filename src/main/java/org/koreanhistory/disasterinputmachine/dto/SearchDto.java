package org.koreanhistory.disasterinputmachine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchDto {

    // 분류번호
    private String clasNo;

    // 색인어 Index (한글, 한자)
    private String indexKR;
    private String indexCN;

    // 대분류 Large_classification_kr (한글, 한자)
    private String lclasKR;
    private String lclasCN;

    // 중분류 Middle_classification_kr (한글, 한자)
    private String mclasKR;
    private String mclasCN;

    // 소분류 Small_classification_kr (한글, 한자)
    private String sclasKR;
    private String sclasCN;

    // 기사 관련 Data
    @Column(columnDefinition = "TEXT")
    // 1) 기사의 개요
    private String articlSumry;
    // 2) 기사의 원문
    @Column(columnDefinition = "LONGTEXT")
    private String articlOrginl;
    // 3) 기사 문헌의 분류
    private String ltrtreClas;
    // 4) 기사 문헌의 명칭
    private String ltrtreNM;

    // 출전 (한글, 한자)
    private String sourceKR;
    private String sourceCN;

    // 연도 (모호년)
    private String yearNameOfTomb;
    // 연도 (서기)
    private String yearAD;
    // 연도 (월)
    private String month;

    // 국가, 왕조 (한국, 중국)
    private String dynastyKR;
    private String dynastyCN;

    // 지역 1, 2, 3 (한글, 한자)
    private String area1KR;
    private String area1CN;
    private String area2KR;
    private String area2CN;
    private String area3KR;
    private String area3CN;

    // 참고 색인어
    private String referIndex;
    // 비고
    @Column(columnDefinition = "TEXT")
    private String remark;

    public String getType() {

        String type = "";

        if(this.clasNo != "" && this.clasNo != null) type = strConcat(type, "clasNo");
        if(this.indexKR != "" && this.indexKR != null) type = strConcat(type, "indexKR");
        if(this.indexCN != "" && this.indexCN != null) type = strConcat(type, "indexCN");
        if(this.lclasKR != "" && this.lclasKR != null) type = strConcat(type, "lclasKR");
        if(this.lclasCN != "" && this.lclasCN != null) type = strConcat(type, "lclasCN");
        if(this.mclasKR != "" && this.mclasKR != null) type = strConcat(type, "mclasKR");
        if(this.mclasCN != "" && this.mclasCN != null) type = strConcat(type, "mclasCN");
        if(this.sclasKR != "" && this.sclasKR != null) type = strConcat(type, "sclasKR");
        if(this.sclasCN != "" && this.sclasCN != null) type = strConcat(type, "sclasCN");
        if(this.ltrtreClas != "" && this.ltrtreClas != null) type = strConcat(type, "ltrtreClas");
        if(this.ltrtreNM != "" && this.ltrtreNM != null) type = strConcat(type, "ltrtreNM");
        if(this.sourceKR != "" && this.sourceKR != null) type = strConcat(type, "sourceKR");
        if(this.sourceCN != "" && this.sourceCN != null) type = strConcat(type, "sourceCN");
        if(this.yearNameOfTomb != "" && this.yearNameOfTomb != null) type = strConcat(type, "yearNameOfTomb");
        if(this.yearAD != "" && this.yearAD != null) type = strConcat(type, "yearAD");
        if(this.month != "" && this.month != null) type = strConcat(type, "month");
        if(this.dynastyKR != "" && this.dynastyKR != null) type = strConcat(type, "dynastyKR");
        if(this.dynastyCN != "" && this.dynastyCN != null) type = strConcat(type, "dynastyCN");
        if(this.area1KR != "" && this.area1KR != null) type = strConcat(type, "area1KR");
        if(this.area2KR != "" && this.area2KR != null) type = strConcat(type, "area2KR");
        if(this.area3KR != "" && this.area3KR != null) type = strConcat(type, "area3KR");
        if(this.area1CN != "" && this.area1CN != null) type = strConcat(type, "area1CN");
        if(this.area2CN != "" && this.area2CN != null) type = strConcat(type, "area2CN");
        if(this.area3CN != "" && this.area3CN != null) type = strConcat(type, "area3CN");
        if(this.referIndex != "" && this.referIndex != null) type = strConcat(type, "referIndex");
        if(this.remark != "" && this.remark != null) type = strConcat(type, "remark");

        return type;
    }

    public String getKeyword() {
        String keyword = "";

        if(this.clasNo != "" && this.clasNo != null) keyword = strConcat(keyword, clasNo);
        if(this.indexKR != "" && this.indexKR != null) keyword = strConcat(keyword, indexKR);
        if(this.indexCN != "" && this.indexCN != null) keyword = strConcat(keyword, indexCN);
        if(this.lclasKR != "" && this.lclasKR != null) keyword = strConcat(keyword, lclasKR);
        if(this.lclasCN != "" && this.lclasCN != null) keyword = strConcat(keyword, lclasCN);
        if(this.mclasKR != "" && this.mclasKR != null) keyword = strConcat(keyword, mclasKR);
        if(this.mclasCN != "" && this.mclasCN != null) keyword = strConcat(keyword, mclasCN);
        if(this.sclasKR != "" && this.sclasKR != null) keyword = strConcat(keyword, sclasKR);
        if(this.sclasCN != "" && this.sclasCN != null) keyword = strConcat(keyword, sclasCN);
        if(this.ltrtreClas != "" && this.ltrtreClas != null) keyword = strConcat(keyword, ltrtreClas);
        if(this.ltrtreNM != "" && this.ltrtreNM != null) keyword = strConcat(keyword, ltrtreNM);
        if(this.sourceKR != "" && this.sourceKR != null) keyword = strConcat(keyword, sourceKR);
        if(this.sourceCN != "" && this.sourceCN != null) keyword = strConcat(keyword, sourceCN);
        if(this.yearNameOfTomb != "" && this.yearNameOfTomb != null) strConcat(keyword, yearNameOfTomb);
        if(this.yearAD != "" && this.yearAD != null) keyword = strConcat(keyword, yearAD);
        if(this.month != "" && this.month != null) keyword = strConcat(keyword, month);
        if(this.dynastyKR != "" && this.dynastyKR != null) keyword = strConcat(keyword, dynastyKR);
        if(this.dynastyCN != "" && this.dynastyCN != null) keyword = strConcat(keyword, dynastyCN);
        if(this.area1KR != "" && this.area1KR != null) keyword = strConcat(keyword, area1KR);
        if(this.area2KR != "" && this.area2KR != null) keyword = strConcat(keyword, area2KR);
        if(this.area3KR != "" && this.area3KR != null) keyword = strConcat(keyword, area3KR);
        if(this.area1CN != "" && this.area1CN != null) keyword = strConcat(keyword, area1CN);
        if(this.area2CN != "" && this.area2CN != null) keyword = strConcat(keyword, area2CN);
        if(this.area3CN != "" && this.area3CN != null) keyword = strConcat(keyword, area3CN);
        if(this.referIndex != "" && this.referIndex != null) keyword = strConcat(keyword, referIndex);
        if(this.remark != "" && this.remark != null) keyword = strConcat(keyword, remark);

        return keyword;
    }

    private String strConcat(String type, String col) {
        if(type.equals(""))
            type = type.concat(col);
        else
            type = type.concat("-" + col);
        return type;
    }
}
