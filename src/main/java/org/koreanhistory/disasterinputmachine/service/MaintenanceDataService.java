package org.koreanhistory.disasterinputmachine.service;

import lombok.RequiredArgsConstructor;
import org.koreanhistory.disasterinputmachine.repository.MaintenanceDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceDataService {
    private final MaintenanceDataRepository repository;
}
