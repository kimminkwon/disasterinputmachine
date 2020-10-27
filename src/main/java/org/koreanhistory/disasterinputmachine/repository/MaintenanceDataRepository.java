package org.koreanhistory.disasterinputmachine.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.QMaintenanceData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

public interface MaintenanceDataRepository extends CrudRepository<MaintenanceData, Long>, QuerydslPredicateExecutor<MaintenanceData> {

    @Transactional
    @Modifying
    @Query("delete from MaintenanceData m where m.mno in :ids")
    public void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("select m from MaintenanceData m where m.mno in :ids")
    public List<MaintenanceData> findAllByIdInQuery(@Param("ids") List<Long> ids);

    public default Predicate makePrdicates(List<String> types, List<String> keywords) {
        BooleanBuilder builder = new BooleanBuilder();
        QMaintenanceData mdata = QMaintenanceData.maintenanceData;

        // bno > 0
        builder.and(mdata.mno.gt(0));

        if(types == null)
            return builder;

        IntStream.range(0, types.size())
                .forEach(
                        i -> {
                            String type = types.get(i);
                            String keyword = keywords.get(i);
                            typeSetForBuilder(mdata, builder, type, keyword);
                        }
                );
        return builder;
    }

    public default Predicate makePrdicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QMaintenanceData mdata = QMaintenanceData.maintenanceData;

        // bno > 0
        builder.and(mdata.mno.gt(0));

        // type if ~ else
        if(type == null)
            return builder;

        typeSetForBuilder(mdata, builder, type, keyword);

        return builder;
    }

    private void typeSetForBuilder(QMaintenanceData mdata, BooleanBuilder builder, String type, String keyword) {
        switch (type) {
            case "index":
                builder.and(mdata.indexKR.like("%" + keyword + "%"));
                break;
            case "large":
                builder.and(mdata.lclasKR.like("%" + keyword + "%"));
                break;
            case "middle":
                builder.and(mdata.mclasKR.like("%" + keyword + "%"));
                break;
            case "small":
                builder.and(mdata.sclasKR.like("%" + keyword + "%"));
                break;
            case "source":
                builder.and(mdata.sourceKR.like("%" + keyword + "%"));
                break;
            case "yearTomb":
                builder.and(mdata.yearNameOfTomb.like("%" + keyword + "%"));
                break;
            case "yearAD":
                builder.and(mdata.yearAD.eq(Integer.parseInt(type)));
                break;
            case "month":
                builder.and(mdata.month.eq(Integer.parseInt(type)));
                break;
            case "nation":
                builder.and(mdata.nationKR.like("%" + type + "%"));
                break;
            case "dynasty":
                builder.and(mdata.dynastyKR.like("%" + type + "%"));
                break;
            case "area1":
                builder.and(mdata.area1KR.like("%" + type + "%"));
                break;
            case "area2":
                builder.and(mdata.area2KR.like("%" + type + "%"));
                break;
            case "area3":
                builder.and(mdata.area3KR.like("%" + type + "%"));
                break;
            case "refer":
                builder.and(mdata.referIndex.like("%" + type + "%"));
                break;
            case "remark":
                builder.and(mdata.remark.like("%" + type + "%"));
                break;
        }
    }

}
