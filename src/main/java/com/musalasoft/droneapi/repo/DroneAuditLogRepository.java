package com.musalasoft.droneapi.repo;

import com.musalasoft.droneapi.entity.DroneAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneAuditLogRepository extends JpaRepository<DroneAuditLog, Long> {
}
