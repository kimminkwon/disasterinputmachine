package org.koreanhistory.disasterinputmachine.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.QMaintenanceData;
import org.koreanhistory.disasterinputmachine.domain.QReservationData;
import org.koreanhistory.disasterinputmachine.domain.ReservationData;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ReservationDataRepository extends CrudRepository<ReservationData, Long>, QuerydslPredicateExecutor<ReservationData> {
    public default Predicate makePrdicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QReservationData rdata = QReservationData.reservationData;

        // bno > 0
        builder.and(rdata.rno.gt(0));

        // type if ~ else
        if(type == null)
            return builder;

        switch (type) {
            case "index":
                builder.and(rdata.indexKR.like("%" + keyword + "%"));
                break;
            case "large":
                builder.and(rdata.lclasKR.like("%" + keyword + "%"));
                break;
            case "middle":
                builder.and(rdata.mclasKR.like("%" + keyword + "%"));
                break;
            case "small":
                builder.and(rdata.sclasKR.like("%" + keyword + "%"));
                break;
            case "source":
                builder.and(rdata.sourceKR.like("%" + keyword + "%"));
                break;
            case "yearTomb":
                builder.and(rdata.yearNameOfTomb.like("%" + keyword + "%"));
                break;
            case "yearAD":
                builder.and(rdata.yearAD.eq(Integer.parseInt(type)));
                break;
            case "month":
                builder.and(rdata.month.eq(Integer.parseInt(type)));
                break;
            case "nation":
                builder.and(rdata.nationKR.like("%" + type + "%"));
                break;
            case "dynasty":
                builder.and(rdata.dynastyKR.like("%" + type + "%"));
                break;
            case "area1":
                builder.and(rdata.area1KR.like("%" + type + "%"));
                break;
            case "area2":
                builder.and(rdata.area2KR.like("%" + type + "%"));
                break;
            case "area3":
                builder.and(rdata.area3KR.like("%" + type + "%"));
                break;
            case "refer":
                builder.and(rdata.referIndex.like("%" + type + "%"));
                break;
            case "remark":
                builder.and(rdata.remark.like("%" + type + "%"));
                break;
        }

        return builder;
    }
}
