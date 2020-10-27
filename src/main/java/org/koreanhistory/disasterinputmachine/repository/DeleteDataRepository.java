package org.koreanhistory.disasterinputmachine.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.koreanhistory.disasterinputmachine.domain.DeleteData;
import org.koreanhistory.disasterinputmachine.domain.QDeleteData;
import org.koreanhistory.disasterinputmachine.domain.QReservationData;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DeleteDataRepository extends CrudRepository<DeleteData, Long>, QuerydslPredicateExecutor<DeleteData> {
    public default Predicate makePrdicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QDeleteData ddata = QDeleteData.deleteData;

        // bno > 0
        builder.and(ddata.dno.gt(0));

        // type if ~ else
        if(type == null)
            return builder;

        switch (type) {
            case "index":
                builder.and(ddata.indexKR.like("%" + keyword + "%"));
                break;
            case "large":
                builder.and(ddata.lclasKR.like("%" + keyword + "%"));
                break;
            case "middle":
                builder.and(ddata.mclasKR.like("%" + keyword + "%"));
                break;
            case "small":
                builder.and(ddata.sclasKR.like("%" + keyword + "%"));
                break;
            case "source":
                builder.and(ddata.sourceKR.like("%" + keyword + "%"));
                break;
            case "yearTomb":
                builder.and(ddata.yearNameOfTomb.like("%" + keyword + "%"));
                break;
            case "yearAD":
                builder.and(ddata.yearAD.eq(Integer.parseInt(type)));
                break;
            case "month":
                builder.and(ddata.month.eq(Integer.parseInt(type)));
                break;
            case "nation":
                builder.and(ddata.nationKR.like("%" + type + "%"));
                break;
            case "dynasty":
                builder.and(ddata.dynastyKR.like("%" + type + "%"));
                break;
            case "area1":
                builder.and(ddata.area1KR.like("%" + type + "%"));
                break;
            case "area2":
                builder.and(ddata.area2KR.like("%" + type + "%"));
                break;
            case "area3":
                builder.and(ddata.area3KR.like("%" + type + "%"));
                break;
            case "refer":
                builder.and(ddata.referIndex.like("%" + type + "%"));
                break;
            case "remark":
                builder.and(ddata.remark.like("%" + type + "%"));
                break;
        }

        return builder;
    }
}
