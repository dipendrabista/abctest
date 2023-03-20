package com.musalasoft.droneapi.repo;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    List<Drone> findAllByStateInAndBatteryCapacityGreaterThanEqual(List<State> stateList, Double batteryCapacity);

    @Query("SELECT  d.batteryCapacity FROM Drone d WHERE d.serialNumber = :serialNumber")
    Optional<Double> getCapacityForSerial(@Param("serialNumber") String serialNumber);

    List<Medication> findAllBySerialNumberAndStateIn(String serialNumber, List<State> stateList);

    @Modifying
    @Query(value = "update Drone d set d.state = :state where  d.serialNumber= :serialno")
    void updateDroneState(@Param("state") State state, @Param("serialno") String serialno);

}
