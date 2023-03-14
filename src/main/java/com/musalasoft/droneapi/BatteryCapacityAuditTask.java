package com.musalasoft.droneapi;

import com.musalasoft.droneapi.entity.DroneAuditLog;
import com.musalasoft.droneapi.repo.DroneAuditLogRepository;
import com.musalasoft.droneapi.repo.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatteryCapacityAuditTask {

    @Autowired
    private DroneAuditLogRepository droneAuditLogRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Scheduled(fixedRateString = "${scheduler.interval}")
    public void auditBatteryCapacity() {
        droneRepository.findAll()
                .forEach(drone -> {
                    droneAuditLogRepository.save(DroneAuditLog.builder()
                            .serialNumber(drone.getSerialNumber())
                            .batteryCapacity(drone.getBatteryCapacity())
                            .build());
                    log.info("serial number - {}, capacity - {}", drone.getSerialNumber(), drone.getBatteryCapacity());
                });
    }
}
