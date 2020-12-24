package org.koreanhistory.disasterinputmachine.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.koreanhistory.disasterinputmachine.dto.MaintenanceDataModifyDto;
import org.koreanhistory.disasterinputmachine.mapping.AreaInGoryeoMapping;
import org.koreanhistory.disasterinputmachine.mapping.AreaInJosunMapping;
import org.koreanhistory.disasterinputmachine.mapping.ClasMapping;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString(exclude = {"articlOrginl", "articlSumry"})
@Table(name = "DSSTRDTA_MNTNC_TB")
@NoArgsConstructor
public class MaintenanceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Primary Key (Maintenace Number)
    private Long mno;

    private String createBy;
    private String modifyBy;

    @CreationTimestamp
    // Created time for debug
    private Timestamp createTime;

    @UpdateTimestamp
    // Modified time for debug
    private Timestamp modifyTime;

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

    @Builder
    public MaintenanceData(String createBy, String modifyBy,
                           String clasNo, String indexKR, String indexCN,
                           String lclasKR, String lclasCN, String mclasKR, String mclasCN, String sclasKR, String sclasCN,
                           String articlSumry, String articlOrginl, String ltrtreClas, String ltrtreNM, String sourceKR, String sourceCN,
                           String yearNameOfTomb, String yearAge, String yearAD, String month,
                           String dynastyKR, String dynastyCN,
                           String area1KR, String area1CN, String area2KR, String area2CN, String area3KR, String area3CN,
                           String referIndex, String remark) {
        this.createBy = createBy; this.modifyBy = modifyBy;
        this.clasNo = clasNo; this.indexKR = indexKR; this.indexCN = indexCN;
        this.lclasKR = lclasKR; this.lclasCN = lclasCN; this.mclasKR = mclasKR; this.mclasCN = mclasCN; this.sclasKR = sclasKR; this.sclasCN = sclasCN;
        this.articlSumry = articlSumry; this.articlOrginl = articlOrginl; this.ltrtreClas = ltrtreClas; this.ltrtreNM = ltrtreNM; this.sourceKR = sourceKR; this.sourceCN = sourceCN;
        this.yearNameOfTomb = yearNameOfTomb; this.yearAge = yearAge; this.yearAD = yearAD; this.month = month;
       this.dynastyKR = dynastyKR; this.dynastyCN = dynastyCN;
        this.area1KR = area1KR; this.area1CN = area1CN; this.area2KR = area2KR; this.area2CN = area2CN; this.area3KR = area3KR; this.area3CN = area3CN;
        this.referIndex = referIndex; this.remark = remark;

        setMappingDatas();
    }

    public void update(MaintenanceDataModifyDto dto) {
        this.modifyBy = dto.getModifyBy();
        this.clasNo = dto.getClasNo(); this.indexKR = dto.getIndexKR(); this.indexCN = dto.getIndexCN();
        this.lclasKR = dto.getLclasKR(); this.lclasCN = dto.getLclasCN(); this.mclasKR = dto.getMclasKR(); this.mclasCN = dto.getMclasCN(); this.sclasKR = dto.getSclasKR(); this.sclasCN = dto.getSclasCN();
        this.articlSumry = dto.getArticlSumry(); this.articlOrginl = dto.getArticlOrginl(); this.ltrtreClas = dto.getLtrtreClas(); this.ltrtreNM = dto.getLtrtreNM(); this.sourceKR = dto.getSourceKR(); this.sourceCN = dto.getSourceCN();
        this.yearNameOfTomb = dto.getYearNameOfTomb(); this.yearAge = dto.getYearAge(); this.yearAD = dto.getYearAD(); this.month = dto.getMonth();
        this.dynastyKR = dto.getDynastyKR(); this.dynastyCN = dto.getDynastyCN();
        this.area1KR = dto.getArea1KR(); this.area1CN = dto.getArea1CN(); this.area2KR = dto.getArea2KR(); this.area2CN = dto.getArea2CN(); this.area3KR = dto.getArea3KR(); this.area3CN = dto.getArea3CN();
        this.referIndex = dto.getReferIndex(); this.remark = dto.getRemark();

        setMappingDatas();
    }

    public ReservationData toReservationData() {
        return ReservationData.builder()
                .createBy(this.createBy).modifyBy(this.modifyBy)
                .clasNo(this.clasNo).indexKR(this.indexKR).indexCN(this.indexCN)
                .lclasKR(this.lclasKR).lclasCN(this.lclasCN)
                .mclasKR(this.mclasKR).mclasCN(this.mclasCN)
                .sclasKR(this.sclasKR).sclasCN(this.sclasCN)
                .articlSumry(this.articlSumry).articlOrginl(this.articlOrginl).ltrtreClas(this.ltrtreClas).ltrtreNM(this.ltrtreNM)
                .sourceKR(this.sourceKR).sourceCN(this.sourceCN)
                .yearNameOfTomb(this.yearNameOfTomb).yearAge(this.yearAge).yearAD(this.yearAD).month(this.month)
                .dynastyKR(this.dynastyKR).dynastyCN(this.dynastyCN)
                .area1KR(this.area1KR).area1CN(this.area1CN)
                .area2KR(this.area2KR).area2CN(this.area2CN)
                .area3KR(this.area3KR).area3CN(this.area3CN)
                .referIndex(this.referIndex).remark(this.remark)
                .build();
    }

    public DeleteData toDeleteData() {
        return DeleteData.builder()
                .createBy(this.createBy).modifyBy(this.modifyBy)
                .clasNo(this.clasNo).indexKR(this.indexKR).indexCN(this.indexCN)
                .lclasKR(this.lclasKR).lclasCN(this.lclasCN)
                .mclasKR(this.mclasKR).mclasCN(this.mclasCN)
                .sclasKR(this.sclasKR).sclasCN(this.sclasCN)
                .articlSumry(this.articlSumry).articlOrginl(this.articlOrginl).ltrtreClas(this.ltrtreClas).ltrtreNM(this.ltrtreNM)
                .sourceKR(this.sourceKR).sourceCN(this.sourceCN)
                .yearNameOfTomb(this.yearNameOfTomb).yearAge(this.yearAge).yearAD(this.yearAD).month(this.month)
                .dynastyKR(this.dynastyKR).dynastyCN(this.dynastyCN)
                .area1KR(this.area1KR).area1CN(this.area1CN)
                .area2KR(this.area2KR).area2CN(this.area2CN)
                .area3KR(this.area3KR).area3CN(this.area3CN)
                .referIndex(this.referIndex).remark(this.remark)
                .build();
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
