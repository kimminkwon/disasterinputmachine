package org.koreanhistory.disasterinputmachine.dto;

import lombok.Getter;
import lombok.ToString;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@ToString
public class DeleteDataDto {

    private Long dno;

    // Created time for debug
    private Timestamp createTime;

    // Modified time for debug
    private Timestamp modifyTime;

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

    public DeleteDataDto(DeleteData entity) {
        this.dno = entity.getDno();
        this.createTime = entity.getCreateTime(); this.modifyTime = entity.getModifyTime();
        this.indexKR = entity.getIndexKR(); this.indexCN = entity.getIndexCN();
        this.lclasKR = entity.getLclasKR(); this.lclasCN = entity.getLclasCN(); this.mclasKR = entity.getMclasKR(); this.mclasCN = entity.getMclasCN(); this.sclasKR = entity.getSclasKR(); this.sclasCN = entity.getSclasCN();
        this.articlSumry = entity.getArticlSumry(); this.articlOrginl = entity.getArticlOrginl(); this.ltrtreNM = entity.getLtrtreNM(); this.sourceKR = entity.getSourceKR(); this.sourceCN = entity.getSourceCN();
        this.yearNameOfTomb = entity.getYearNameOfTomb(); this.yearAD = entity.getYearAD(); this.month = entity.getMonth();
        this.nationKR = entity.getNationKR(); this.dynastyKR = entity.getDynastyKR(); this.nationCN = entity.getNationCN(); this.dynastyCN = entity.getDynastyCN();
        this.area1KR = entity.getArea1KR(); this.area1CN = entity.getArea1CN(); this.area2KR = entity.getArea2KR(); this.area2CN = entity.getArea2CN(); this.area3KR = entity.getArea3KR(); this.area3CN = entity.getArea3CN();
        this.referIndex = entity.getReferIndex(); this.remark = entity.getRemark();
    }
}
