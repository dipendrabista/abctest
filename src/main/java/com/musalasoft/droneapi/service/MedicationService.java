package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.dto.MedicationDTO;
import com.musalasoft.droneapi.dto.mapper.MedicationMapper;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.entity.Medication;
import com.musalasoft.droneapi.exception.object.ResourceNotFoundException;
import com.musalasoft.droneapi.repo.DroneRepository;
import com.musalasoft.droneapi.repo.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.musalasoft.droneapi.constants.AppConstants.WEIGHT_LIMIT;

@Service
@Slf4j
public class MedicationService {
    private DroneRepository droneRepository;
    private MedicationMapper medicationMapper;
    private MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(
            MedicationRepository medicationRepository,
            DroneRepository droneRepository,
            MedicationMapper medicationMapper
    ) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    public List<MedicationDTO> findLoadedMedication(String serialNumber) {
        log.info("Retrieving drone details with serial number {}", serialNumber);
        Drone droneEntity = droneRepository.findById(serialNumber).orElseThrow(() -> new EntityNotFoundException("Drone " + serialNumber + " is not fund"));
        log.info("Drone Details {}", droneEntity);
        return medicationRepository.findAllByDrone(droneEntity)
                .stream().map(medicationMapper::from).collect(Collectors.toList());
    }

    public String loadMedication(String serialNumber, List<String> medicationCodes) {
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(() -> ResourceNotFoundException.of("messageCreator.createMessage(DRONE_SERIAL_NUMBER_NOT_FOUND)"));
        medicationCodes.forEach(mC -> {
            Medication m = medicationRepository.findById(mC).orElseThrow(() -> ResourceNotFoundException.of("messageCreator.createMessage(MEDICATION_CODE_NOT_FOUND)"));
            m.setDrone(drone);
            medicationRepository.saveAndFlush(m);
            double newWeight = drone.getWeightLimit() + m.getWeight();
            if (newWeight < WEIGHT_LIMIT) {
                drone.setWeightLimit(newWeight);
                drone.setState(State.LOADING);
            }
            if (newWeight == WEIGHT_LIMIT) {
                drone.setWeightLimit(newWeight);
                drone.setState(State.LOADED);
            }
            if (newWeight > WEIGHT_LIMIT)
                // we don't set status as LOADED as we might try to load a lighter medication
                ResourceNotFoundException.of("Medication overloaded");
//                ClientException.of(messageCreator.createMessage(MEDICATION_OVERLOAD)) ;
        });
        droneRepository.saveAndFlush(drone);
        return "";
//                messageCreator.createMessage(DRONE_LOADED);
    }
}
