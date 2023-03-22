package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.dto.MedicationLoadRequestDTO;
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

import java.util.List;
import java.util.stream.Collectors;

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
        return droneLoadRepository.findByDroneSerialNumber(serialNumber)
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
     * @param medicationLoadRequestDTO
     * @return
     */

    @Transactional
    public String loadMedication(MedicationLoadRequestDTO medicationLoadRequestDTO) {
        Drone drone = droneRepository.findById(medicationLoadRequestDTO.getSerialNumber())
                .orElseThrow(() -> ResourceNotFoundException.of("Drone " + medicationLoadRequestDTO.getSerialNumber() + " doesn't exist !"));

        if (drone.getBatteryCapacity() < 0.25)
            throw new RuntimeException("Can not load battery capacity less than 25%");

        List<Medication> medicationList = medicationLoadRequestDTO.getMedicationCodes()
                .stream()
                .map(code -> medicationRepository.findById(code)
                        .orElseThrow(() -> ResourceNotFoundException.of("Invalid Medical Code "+ code)))
                .collect(Collectors.toList());

        //Calculate projected new load and check against drone weight limit
        if (medicationList.stream().mapToDouble(medication -> medication.getWeight()).sum() + getTotalMedicationLoad(medicationLoadRequestDTO.getSerialNumber()) >= drone.getWeightLimit())
            throw new RuntimeException("Drone does not have capacity for new load");

        //Updating state to LOADING
        droneRepository.updateDroneState(State.LOADING, medicationLoadRequestDTO.getSerialNumber());

        List<DroneLoad> droneLoads = medicationList.stream()
                .map(medication -> DroneLoad.builder().drone(drone).medication(medication).build())
                .collect(Collectors.toList());

        droneLoadRepository.saveAll(droneLoads);

        droneRepository.updateDroneState(State.LOADED, medicationLoadRequestDTO.getSerialNumber());


        return "Drone Loaded Successfully";
    }
}