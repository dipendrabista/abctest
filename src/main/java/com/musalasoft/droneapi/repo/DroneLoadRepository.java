package com.musalasoft.droneapi.repo;

import com.musalasoft.droneapi.entity.DroneLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneLoadRepository extends JpaRepository<DroneLoad, Integer> {
    List<DroneLoad> findByDroneSerialNumberAnd(String serialNumber);
}
