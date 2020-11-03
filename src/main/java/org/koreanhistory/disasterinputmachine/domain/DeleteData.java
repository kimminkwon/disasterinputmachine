package org.koreanhistory.disasterinputmachine.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.koreanhistory.disasterinputmachine.dto.DeleteDataModifyDto;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString(exclude = {"articlOrginl", "articlSumry"})
@Table(name = "DSSTRDTA_DELETE_TB")
@NoArgsConstructor
public class DeleteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Primary Key (Maintenace Number)
    private Long dno;

    @CreationTimestamp
    // Created time for debug
    private Timestamp createTime;

    @UpdateTimestamp
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

    @Builder
    public DeleteData(String indexKR, String indexCN,
                      String lclasKR, String lclasCN, String mclasKR, String mclasCN, String sclasKR, String sclasCN,
                      String articlSumry, String articlOrginl, String ltrtreNM, String sourceKR, String sourceCN,
                      String yearNameOfTomb, int yearAD, int month,
                      String dynastyKR, String dynastyCN,
                      String area1KR, String area1CN, String area2KR, String area2CN, String area3KR, String area3CN,
                      String referIndex, String remark) {
        this.indexKR = indexKR; this.indexCN = indexCN;
        this.lclasKR = lclasKR; this.lclasCN = lclasCN; this.mclasKR = mclasKR; this.mclasCN = mclasCN; this.sclasKR = sclasKR; this.sclasCN = sclasCN;
        this.articlSumry = articlSumry; this.articlOrginl = articlOrginl; this.ltrtreNM = ltrtreNM; this.sourceKR = sourceKR; this.sourceCN = sourceCN;
        this.yearNameOfTomb = yearNameOfTomb; this.yearAD = yearAD; this.month = month;
        this.dynastyKR = dynastyKR; this.dynastyCN = dynastyCN;
        this.area1KR = area1KR; this.area1CN = area1CN; this.area2KR = area2KR; this.area2CN = area2CN; this.area3KR = area3KR; this.area3CN = area3CN;
        this.referIndex = referIndex; this.remark = remark;
    }

    public void update(DeleteDataModifyDto dto) {
        this.indexKR = dto.getIndexKR(); this.indexCN = dto.getIndexCN();
        this.lclasKR = dto.getLclasKR(); this.lclasCN = dto.getLclasCN(); this.mclasKR = dto.getMclasKR(); this.mclasCN = dto.getMclasCN(); this.sclasKR = dto.getSclasKR(); this.sclasCN = dto.getSclasCN();
        this.articlSumry = dto.getArticlSumry(); this.articlOrginl = dto.getArticlOrginl(); this.ltrtreNM = dto.getLtrtreNM(); this.sourceKR = dto.getSourceKR(); this.sourceCN = dto.getSourceCN();
        this.yearNameOfTomb = dto.getYearNameOfTomb(); this.yearAD = dto.getYearAD(); this.month = dto.getMonth();
        this.dynastyKR = dto.getDynastyKR(); this.dynastyCN = dto.getDynastyCN();
        this.area1KR = dto.getArea1KR(); this.area1CN = dto.getArea1CN(); this.area2KR = dto.getArea2KR(); this.area2CN = dto.getArea2CN(); this.area3KR = dto.getArea3KR(); this.area3CN = dto.getArea3CN();
        this.referIndex = dto.getReferIndex(); this.remark = dto.getRemark();
    }

    public ReservationData toReservationData() {
        return ReservationData.builder()
                .indexKR(this.indexKR).indexCN(this.indexCN)
                .lclasKR(this.lclasKR).lclasCN(this.lclasCN)
                .mclasKR(this.mclasKR).mclasCN(this.mclasCN)
                .sclasKR(this.sclasKR).sclasCN(this.sclasCN)
                .articlSumry(this.articlSumry).articlOrginl(this.articlOrginl).ltrtreNM(this.ltrtreNM)
                .sourceKR(this.sourceKR).sourceCN(this.sourceCN)
                .yearNameOfTomb(this.yearNameOfTomb).yearAD(this.yearAD).month(this.month)
                .dynastyKR(this.dynastyKR).dynastyCN(this.dynastyCN)
                .area1KR(this.area1KR).area1CN(this.area1CN)
                .area2KR(this.area2KR).area2CN(this.area2CN)
                .area3KR(this.area3KR).area3CN(this.area3CN)
                .referIndex(this.referIndex).remark(this.remark)
                .build();
    }

    public MaintenanceData toMaintenanceData() {
        return MaintenanceData.builder()
                .indexKR(this.indexKR).indexCN(this.indexCN)
                .lclasKR(this.lclasKR).lclasCN(this.lclasCN)
                .mclasKR(this.mclasKR).mclasCN(this.mclasCN)
                .sclasKR(this.sclasKR).sclasCN(this.sclasCN)
                .articlSumry(this.articlSumry).articlOrginl(this.articlOrginl).ltrtreNM(this.ltrtreNM)
                .sourceKR(this.sourceKR).sourceCN(this.sourceCN)
                .yearNameOfTomb(this.yearNameOfTomb).yearAD(this.yearAD).month(this.month)
                .dynastyKR(this.dynastyKR).dynastyCN(this.dynastyCN)
                .area1KR(this.area1KR).area1CN(this.area1CN)
                .area2KR(this.area2KR).area2CN(this.area2CN)
                .area3KR(this.area3KR).area3CN(this.area3CN)
                .referIndex(this.referIndex).remark(this.remark)
                .build();
    }
}
