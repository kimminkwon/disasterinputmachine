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
            case "index":
                builder.and(data.indexKR.like("%" + keyword + "%"));
                break;
            case "large":
                builder.and(data.lclasKR.like("%" + keyword + "%"));
                break;
            case "middle":
                builder.and(data.mclasKR.like("%" + keyword + "%"));
                break;
            case "small":
                builder.and(data.sclasKR.like("%" + keyword + "%"));
                break;
            case "source":
                builder.and(data.sourceKR.like("%" + keyword + "%"));
                break;
            case "yearTomb":
                builder.and(data.yearNameOfTomb.like("%" + keyword + "%"));
                break;
            case "yearAD":
                builder.and(data.yearAD.eq(Integer.parseInt(type)));
                break;
            case "month":
                builder.and(data.month.eq(Integer.parseInt(type)));
                break;
            case "dynasty":
                builder.and(data.dynastyKR.like("%" + type + "%"));
                break;
            case "area1":
                builder.and(data.area1KR.like("%" + type + "%"));
                break;
            case "area2":
                builder.and(data.area2KR.like("%" + type + "%"));
                break;
            case "area3":
                builder.and(data.area3KR.like("%" + type + "%"));
                break;
            case "refer":
                builder.and(data.referIndex.like("%" + type + "%"));
                break;
            case "remark":
                builder.and(data.remark.like("%" + type + "%"));
                break;
        }
    }
}
