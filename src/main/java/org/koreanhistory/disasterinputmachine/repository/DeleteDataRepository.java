package org.koreanhistory.disasterinputmachine.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.koreanhistory.disasterinputmachine.domain.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

public interface DeleteDataRepository extends CrudRepository<DeleteData, Long>, QuerydslPredicateExecutor<DeleteData> {

    @Transactional
    @Modifying
    @Query("delete from DeleteData d where d.dno in :ids")
    public void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("select d from DeleteData d where d.dno in :ids")
    public List<DeleteData> findAllByIdInQuery(@Param("ids") List<Long> ids);

    public default Predicate makePrdicates(List<String> types, List<String> keywords) {
        BooleanBuilder builder = new BooleanBuilder();
        QDeleteData ddata = QDeleteData.deleteData;

        // bno > 0
        builder.and(ddata.dno.gt(0));

        if(types == null)
            return builder;

        IntStream.range(0, types.size()).forEach(
                        i -> {
                            String type = types.get(i);
                            String keyword = keywords.get(i);
                            typeSetForBuilder(ddata, builder, type, keyword);
                        }
                );

        return builder;
    }

    public default Predicate makePrdicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QDeleteData ddata = QDeleteData.deleteData;

        // bno > 0
        builder.and(ddata.dno.gt(0));

        // type if ~ else
        if(type == null)
            return builder;

        typeSetForBuilder(ddata, builder, type, keyword);

        return builder;
    }

    private void typeSetForBuilder(QDeleteData data, BooleanBuilder builder, String type, String keyword) {
        switch (type) {
            case "clasNo":
                builder.and(data.clasNo.like("%" + keyword + "%"));
                break;
            case "indexKR":
                builder.and(data.indexKR.like("%" + keyword + "%"));
                break;
            case "indexCN":
                builder.and(data.indexCN.like("%" + keyword + "%"));
                break;
            case "lclasKR":
                builder.and(data.lclasKR.like("%" + keyword + "%"));
                break;
            case "lclasCN":
                builder.and(data.lclasCN.like("%" + keyword + "%"));
                break;
            case "mclasKR":
                builder.and(data.mclasKR.like("%" + keyword + "%"));
                break;
            case "mclasCN":
                builder.and(data.mclasCN.like("%" + keyword + "%"));
                break;
            case "sclasKR":
                builder.and(data.sclasKR.like("%" + keyword + "%"));
                break;
            case "sclasCN":
                builder.and(data.sclasCN.like("%" + keyword + "%"));
                break;
            case "ltrtreClas":
                builder.and(data.ltrtreClas.like("%" + keyword + "%"));
                break;
            case "ltrtreNM":
                builder.and(data.ltrtreNM.like("%" + keyword + "%"));
                break;
            case "sourceKR":
                builder.and(data.sourceKR.like("%" + keyword + "%"));
                break;
            case "sourceCN":
                builder.and(data.sourceCN.like("%" + keyword + "%"));
                break;
            case "yearNameOfTomb":
                builder.and(data.yearNameOfTomb.like("%" + keyword + "%"));
                break;
            case "yearAD":
                builder.and(data.yearAD.like("%" + keyword + "%"));
                break;
            case "month":
                builder.and(data.month.like("%" + keyword + "%"));
                break;
            case "dynastyKR":
                builder.and(data.dynastyKR.like("%" + keyword + "%"));
                break;
            case "dynastyCN":
                builder.and(data.dynastyCN.like("%" + keyword + "%"));
                break;
            case "area1KR":
                builder.and(data.area1KR.like("%" + keyword + "%"));
                break;
            case "area2KR":
                builder.and(data.area2KR.like("%" + keyword + "%"));
                break;
            case "area3KR":
                builder.and(data.area3KR.like("%" + keyword + "%"));
                break;
            case "area1CN":
                builder.and(data.area1CN.like("%" + keyword + "%"));
                break;
            case "area2CN":
                builder.and(data.area2CN.like("%" + keyword + "%"));
                break;
            case "area3CN":
                builder.and(data.area3CN.like("%" + keyword + "%"));
                break;
            case "referIndex":
                builder.and(data.referIndex.like("%" + keyword + "%"));
                break;
            case "remark":
                builder.and(data.remark.like("%" + keyword + "%"));
                break;
        }
    }
}
