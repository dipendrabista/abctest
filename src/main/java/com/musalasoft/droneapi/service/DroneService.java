package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.AppConstants;
import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.dto.DroneDTO;
import com.musalasoft.droneapi.dto.DroneStatus;
import com.musalasoft.droneapi.dto.MedicationDTO;
import com.musalasoft.droneapi.dto.mapper.DroneMapper;
import com.musalasoft.droneapi.dto.mapper.MedicationMapper;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.entity.Medication;
import com.musalasoft.droneapi.exception.object.AlreadyExistException;
import com.musalasoft.droneapi.exception.object.InvalidRequestException;
import com.musalasoft.droneapi.exception.object.ResourceNotFoundException;
import com.musalasoft.droneapi.repo.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class DroneService {
    private DroneMapper droneMapper;
    private DroneRepository droneRepository;
    private MedicationMapper medicationMapper;

    @Autowired
    public DroneService(
            DroneRepository droneRepository,
            DroneMapper droneMapper,
            MedicationMapper medicationMapper

    ) {
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
        this.medicationMapper = medicationMapper;

    }

    public Drone registerDrone(DroneDTO droneDTO) {
        log.info("Converting DroneDTO to Drone Entity and persisting into Database");
        if (droneRepository.count() == AppConstants.MAX_FLEET_SIZE)
            throw new RuntimeException("Drone Fleet Size Exceeded");
        droneRepository.findById(droneDTO.getSerialNumber())
                .ifPresent((drone) -> {
                    throw AlreadyExistException.of("Drone " + drone.getSerialNumber() + " already exist !");
                });
        /**
         * Drone state set to IDLE state initially regardless of the state passed from the client
         */
        droneDTO.setState(State.IDLE);
        return droneRepository.save(droneMapper.from(droneDTO));
    }

//
//    public List<MedicationDTO> findLoadedMedication(String serialNumber) {
//        return findMedication(serialNumber)
//                .stream()
//                .map(medicationMapper::from)
//                .collect(Collectors.toList());
//
//    }

    public List<MedicationDTO> findLoadedMedication(String serialNumber) {
        log.info("Retrieving drone details with serial number {}", serialNumber);
        Drone droneEntity = getDrone(serialNumber);
        log.info("Drone Details {}", droneEntity);
        return droneEntity.getDroneLoads()
                .stream()
                .map(droneLoad -> {
                    MedicationDTO medicationDTO = medicationMapper.from(droneLoad.getMedication());
                    medicationDTO.setQuantity(droneLoad.getQuantity());
                    return medicationDTO;
                })
                .collect(Collectors.toList());

    }

    public List<DroneDTO> getAvailableDrones() {
        log.info("Retrieving list of available drones ");
        List<State> states = new ArrayList<>();
        states.add(State.IDLE);
        states.add(State.LOADED);
        return droneRepository
                .findAllByStateInAndBatteryCapacityGreaterThanEqual(states, 25.0)
                .stream()
                .filter(drone -> drone.getDroneLoads().stream().mapToDouble(droneLoad -> droneLoad.getMedication().getWeight()).sum() < drone.getWeightLimit())
                .map(droneMapper::from).collect(Collectors.toList());
    }

    public Double findDroneBatteryCapacity(String serialNumber) {
        log.info("Retrieving Battery capacity for {} drone ", serialNumber);
        return getDrone(serialNumber).getBatteryCapacity();
    }

    public Drone changeDroneStatus(String serialNumber, DroneStatus droneStatus) {
//        log.info("Retrieving Battery capacity for {} drone ", serialNumber);
        Drone drone = this.getDrone(serialNumber);
        State nextState = drone.getState().nextState();
        if (nextState != droneStatus.getStatus()) {
           throw InvalidRequestException.of(String.format("Drone State con only be changed to %s", nextState));
        }
        droneRepository.updateDroneStateAndBatteryCapacity(
                serialNumber,
                droneStatus.getStatus(),
                droneStatus.getBatteryRemaining()
        );
        drone.setBatteryCapacity(droneStatus.getBatteryRemaining());
        drone.setState(droneStatus.getStatus());
        return drone;
    }


    private Drone getDrone(String serialNumber) {
        return droneRepository.findById(serialNumber)
                .orElseThrow(() -> new EntityNotFoundException("Drone " + serialNumber + " Does not Exist "));
    }
}