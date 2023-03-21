package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.entity.DroneLoad;
import com.musalasoft.droneapi.entity.Medication;
import com.musalasoft.droneapi.exception.object.ResourceNotFoundException;
import com.musalasoft.droneapi.repo.DroneLoadRepository;
import com.musalasoft.droneapi.repo.DroneRepository;
import com.musalasoft.droneapi.repo.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DroneLoadService {
    private DroneLoadRepository droneLoadRepository;
    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;

    @Autowired
    public DroneLoadService(
            DroneLoadRepository droneLoadRepository,
            DroneRepository droneRepository,
            MedicationRepository medicationRepository
    ) {
        this.droneLoadRepository = droneLoadRepository;
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    public Double getTotalMedicationLoad(String serialNumber) {
        log.info("Retrieving Medication Load for {} drone ", serialNumber);
        return droneLoadRepository.findByDroneSerialNumberAnd(serialNumber)
                .stream()
                .mapToDouble(droneLoad -> droneLoad.getMedication().getWeight())
                .sum();
    }

    /**
     * Drone can be loaded with medication multiple time.
     * It either loads all the medication or none since method marked as @Transactional.
     * VALIDATIONS:
     * i>Battery capacity must be greater than or equal to 25%
     * ii>Serial Number and Medication Code must valid
     * iii>Drone load should not exceed it weight limit
     *
     * @param serialNumber
     * @param medicationCodes
     * @return
     */
    @Transactional
    public String loadMedication(String serialNumber, List<String> medicationCodes) {
        List<Medication> medicationList = new ArrayList<>();
        List<DroneLoad> droneLoads = new ArrayList<>();
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(() -> ResourceNotFoundException.of("messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)"));
        if (drone.getBatteryCapacity().compareTo(new Double(0.25)) < 0)
            throw new RuntimeException("Can no load battery capacity less than 25%");

        medicationCodes.forEach(code -> medicationList.add(medicationRepository.findById(code)
                .orElseThrow(() -> ResourceNotFoundException.of("Invalid Medical Code"))));
        if (getTotalMedicationLoad(serialNumber) >= drone.getWeightLimit())
            throw new RuntimeException("Drone is Fully Loaded");

        droneRepository.updateDroneState(State.LOADING, serialNumber);
        medicationList.forEach(medication -> {
            DroneLoad droneLoad = new DroneLoad();
            droneLoad.setDrone(drone);
            droneLoad.setMedication(medication);
            droneLoads.add(droneLoad);

        });
        droneLoadRepository.saveAll(droneLoads);
        droneRepository.updateDroneState(State.LOADED, serialNumber);
        return "Drone Loaded Successfully";
    }

}