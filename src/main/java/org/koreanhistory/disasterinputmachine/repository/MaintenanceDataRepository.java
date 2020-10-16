package org.koreanhistory.disasterinputmachine.repository;

import org.koreanhistory.disasterinputmachine.domain.MaintenanceData;
import org.springframework.data.repository.CrudRepository;

public interface MaintenanceDataRepository extends CrudRepository<MaintenanceData, Long> {
}
