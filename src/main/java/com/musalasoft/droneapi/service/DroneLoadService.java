package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.repo.DroneLoadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DroneLoadService {
    private DroneLoadRepository droneLoadRepository;

    @Autowired
    public DroneLoadService(DroneLoadRepository droneLoadRepository) {
        this.droneLoadRepository = droneLoadRepository;
    }

    public Double getTotalMedicationLoad(String serialNumber) {
        log.info("Retrieving Medication Load for {} drone ", serialNumber);
        return droneLoadRepository.findByDroneSerialNumberAnd(serialNumber)
                .stream()
                .mapToDouble(droneLoad -> droneLoad.getMedication().getWeight())
                .sum();
    }

}