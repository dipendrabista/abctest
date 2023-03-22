package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.entity.DroneLoad;
import com.musalasoft.droneapi.entity.MedicalDelivery;
import com.musalasoft.droneapi.repo.DroneLoadRepository;
import com.musalasoft.droneapi.repo.DroneRepository;
import com.musalasoft.droneapi.repo.MedicalDeliveryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class MedicalDeliveryService {
    private MedicalDeliveryRepository medicalDeliveryRepository;
    private DroneLoadRepository droneLoadRepository;
    private DroneRepository droneRepository;

    @Autowired
    public MedicalDeliveryService(
            MedicalDeliveryRepository medicalDeliveryRepository,
            DroneLoadRepository droneLoadRepository,
            DroneRepository droneRepository
    ) {
        this.medicalDeliveryRepository = medicalDeliveryRepository;
        this.droneLoadRepository = droneLoadRepository;
        this.droneRepository = droneRepository;
    }


    public String deliver(String serialNumber) {
        log.info("Delivering Drone's load");
        droneRepository.updateDroneState(State.DELIVERING, serialNumber);
        List<DroneLoad> droneLoads = droneLoadRepository
                .findByDroneSerialNumber(serialNumber);
        if (CollectionUtils.isEmpty(droneLoads))
            throw new RuntimeException("Drone specifies is not loaded or does not exist");
        MedicalDelivery medicalDelivery = new MedicalDelivery();
        medicalDelivery.setDroneLoads(droneLoads);
        medicalDelivery.setDeliveryTime(java.time.LocalDateTime.now());
        medicalDeliveryRepository.save(medicalDelivery);
        droneRepository.updateDroneState(State.DELIVERED, serialNumber);
        return "Drone's Load Delivered Successfully";
    }
}