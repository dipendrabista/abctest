package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.dto.mapper.MedicationMapper;
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
@Transactional(readOnly = true)
public class MedicationService {
    private DroneRepository droneRepository;
    private MedicationMapper medicationMapper;
    private MedicationRepository medicationRepository;
    private DroneLoadRepository droneLoadRepository;
    private DroneLoadService droneLoadService;


    @Autowired
    public MedicationService(
            MedicationRepository medicationRepository,
            DroneRepository droneRepository,
            MedicationMapper medicationMapper,
            DroneLoadService droneLoadService,
            DroneLoadRepository droneLoadRepository
    ) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
        this.droneLoadService = droneLoadService;
        this.droneLoadRepository = droneLoadRepository;
    }

    @Transactional
    public String loadMedication(String serialNumber, List<String> medicationCodes) {
        List<Medication> medicationList = new ArrayList<>();
        List<DroneLoad> droneLoads = new ArrayList<>();
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(() -> ResourceNotFoundException.of("messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)"));
        if (drone.getBatteryCapacity().compareTo(new Double(0.25)) < 0)
            throw new RuntimeException("Can no load battery capacity less than 25%");

        medicationCodes.forEach(code -> medicationList.add(medicationRepository.findById(code)
                .orElseThrow(() -> ResourceNotFoundException.of("Invalid Medical Code"))));
        if (droneLoadService.getTotalMedicationLoad(serialNumber) >= drone.getWeightLimit())
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
        return "";
    }
}
