package org.koreanhistory.disasterinputmachine.dto;

import lombok.*;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.koreanhistory.disasterinputmachine.mapping.AreaInGoryeoMapping;
import org.koreanhistory.disasterinputmachine.mapping.AreaInJosunMapping;
import org.koreanhistory.disasterinputmachine.mapping.ClasMapping;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDataModifyDto {

    private Long rno;

    private String modifyBy;

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
    // 연도 (연호)
    private String yearAge;
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

    public ReservationDataModifyDto(ReservationData entity) {
        this.modifyBy = entity.getModifyBy();
        this.clasNo = entity.getClasNo(); this.indexKR = entity.getIndexKR(); this.indexCN = entity.getIndexCN();
        this.lclasKR = entity.getLclasKR(); this.lclasCN = entity.getLclasCN(); this.mclasKR = entity.getMclasKR(); this.mclasCN = entity.getMclasCN(); this.sclasKR = entity.getSclasKR(); this.sclasCN = entity.getSclasCN();
        this.articlSumry = entity.getArticlSumry(); this.articlOrginl = entity.getArticlOrginl(); this.ltrtreClas = entity.getLtrtreClas(); this.ltrtreNM = entity.getLtrtreNM(); this.sourceKR = entity.getSourceKR(); this.sourceCN = entity.getSourceCN();
        this.yearNameOfTomb = entity.getYearNameOfTomb(); this.yearAge = entity.getYearAge(); this.yearAD = entity.getYearAD(); this.month = entity.getMonth();
        this.dynastyKR = entity.getDynastyKR(); this.dynastyCN = entity.getDynastyCN();
        this.area1KR = entity.getArea1KR(); this.area1CN = entity.getArea1CN(); this.area2KR = entity.getArea2KR(); this.area2CN = entity.getArea2CN(); this.area3KR = entity.getArea3KR(); this.area3CN = entity.getArea3CN();
        this.referIndex = entity.getReferIndex(); this.remark = entity.getRemark();

        setMappingDatas();
    }

    public void setMappingDatas() {
        String[] clasDatas = ClasMapping.getInstance().getClasDatas(clasNo);
        this.lclasKR = clasDatas[0]; this.lclasCN = clasDatas[1]; this.mclasKR = clasDatas[2]; this.mclasCN = clasDatas[3]; this.sclasKR = clasDatas[4]; this.sclasCN = clasDatas[5];

        if(dynastyKR != null && dynastyKR.equals("조선")) {
            this.area1CN = area1KR.equals("") || area1KR == null ? "" : AreaInJosunMapping.getInstance().getAreaOfChina(area1KR);
            this.area2CN = area2KR.equals("") || area2KR == null ? "" : AreaInJosunMapping.getInstance().getAreaOfChina(area2KR);
        } else if(dynastyKR != null && dynastyKR.equals("고려")) {
            this.area1CN = area1KR.equals("") || area1KR == null ? "" : AreaInGoryeoMapping.getInstance().getAreaOfChina(area1KR);
            this.area2CN = area2KR.equals("") || area2KR == null ? "" : AreaInGoryeoMapping.getInstance().getAreaOfChina(area2KR);
        }
    }
}
