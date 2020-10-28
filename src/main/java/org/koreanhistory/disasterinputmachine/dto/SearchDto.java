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
    // 3) 기사 문헌의 명칭
    private String ltrtreNM;

    // 출전 (한글, 한자)
    private String sourceKR;
    private String sourceCN;

    // 연도 (모호년)
    private String yearNameOfTomb;
    // 연도 (서기)
    private int yearAD;
    // 연도 (월)
    private int month;

    // 국가, 왕조 (한글, 한자)
    private String nationKR;
    private String dynastyKR;
    private String nationCN;
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

    public SearchDto(DeleteData entity) {
        this.indexKR = entity.getIndexKR(); this.indexCN = entity.getIndexCN();
        this.lclasKR = entity.getLclasKR(); this.lclasCN = entity.getLclasCN(); this.mclasKR = entity.getMclasKR(); this.mclasCN = entity.getMclasCN(); this.sclasKR = entity.getSclasKR(); this.sclasCN = entity.getSclasCN();
        this.articlSumry = entity.getArticlSumry(); this.articlOrginl = entity.getArticlOrginl(); this.ltrtreNM = entity.getLtrtreNM(); this.sourceKR = entity.getSourceKR(); this.sourceCN = entity.getSourceCN();
        this.yearNameOfTomb = entity.getYearNameOfTomb(); this.yearAD = entity.getYearAD(); this.month = entity.getMonth();
        this.nationKR = entity.getNationKR(); this.dynastyKR = entity.getDynastyKR(); this.nationCN = entity.getNationCN(); this.dynastyCN = entity.getDynastyCN();
        this.area1KR = entity.getArea1KR(); this.area1CN = entity.getArea1CN(); this.area2KR = entity.getArea2KR(); this.area2CN = entity.getArea2CN(); this.area3KR = entity.getArea3KR(); this.area3CN = entity.getArea3CN();
        this.referIndex = entity.getReferIndex(); this.remark = entity.getRemark();
    }

    public String getType() {

        String type = "";

        if(this.indexKR != "" && this.indexKR != null) type = strConcat(type, "index");
        if(this.lclasKR != "" && this.lclasKR != null) type = strConcat(type, "large");
        if(this.mclasKR != "" && this.mclasKR != null) type = strConcat(type, "middle");
        if(this.sclasKR != "" && this.sclasKR != null) type = strConcat(type, "small");
        if(this.sourceKR != "" && this.sourceKR != null) type = strConcat(type, "source");
        if(this.yearNameOfTomb != "" && this.yearNameOfTomb != null) type = strConcat(type, "yearTomb");
        if(this.yearAD != 0) type = strConcat(type, "yearAD");
        if(this.month != 0) type = strConcat(type, "month");
        if(this.nationKR != "" && this.nationKR != null) type = strConcat(type, "nation");
        if(this.dynastyKR != "" && this.dynastyKR != null) type = strConcat(type, "dynasty");
        if(this.area1KR != "" && this.area1KR != null) type = strConcat(type, "area1");
        if(this.area2KR != "" && this.area2KR != null) type = strConcat(type, "area2");
        if(this.area3KR != "" && this.area3KR != null) type = strConcat(type, "area3");
        if(this.referIndex != "" && this.referIndex != null) type = strConcat(type, "refer");
        if(this.remark != "" && this.remark != null) type = strConcat(type, "remark");

        return type;
    }

    public String getKeyword() {
        String keyword = "";

        if(this.indexKR != "" && this.indexKR != null) keyword = strConcat(keyword, indexKR);
        if(this.lclasKR != "" && this.lclasKR != null) keyword = strConcat(keyword, lclasKR);
        if(this.mclasKR != "" && this.mclasKR != null) keyword = strConcat(keyword, mclasKR);
        if(this.sclasKR != "" && this.sclasKR != null) keyword = strConcat(keyword, sclasKR);
        if(this.sourceKR != "" && this.sourceKR != null) keyword = strConcat(keyword, sourceKR);
        if(this.yearNameOfTomb != "" && this.yearNameOfTomb != null) strConcat(keyword, yearNameOfTomb);
        if(this.yearAD != 0) keyword = strConcat(keyword, String.valueOf(yearAD));
        if(this.month != 0) keyword = strConcat(keyword, String.valueOf(month));
        if(this.nationKR != "" && this.nationKR != null) keyword = strConcat(keyword, nationKR);
        if(this.dynastyKR != "" && this.dynastyKR != null) keyword = strConcat(keyword, dynastyKR);
        if(this.area1KR != "" && this.area1KR != null) keyword = strConcat(keyword, area1KR);
        if(this.area2KR != "" && this.area2KR != null) keyword = strConcat(keyword, area2KR);
        if(this.area3KR != "" && this.area3KR != null) keyword = strConcat(keyword, area3KR);
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
