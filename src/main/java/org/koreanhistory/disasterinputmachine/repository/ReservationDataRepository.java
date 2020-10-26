package org.koreanhistory.disasterinputmachine.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.QMaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ReservationDataRepository extends CrudRepository<ReservationData, Long>, QuerydslPredicateExecutor<ReservationData> {
    public default Predicate makePrdicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QMaintenanceData mdata = QMaintenanceData.maintenanceData;

        // bno > 0
        builder.and(mdata.mno.gt(0));

        // type if ~ else
        if(type == null)
            return builder;

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

        return builder;
    }
}
