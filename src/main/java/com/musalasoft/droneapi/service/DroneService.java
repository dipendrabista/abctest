package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.constants.AppConstants;
import com.musalasoft.droneapi.constants.State;
import com.musalasoft.droneapi.dto.DroneDTO;
import com.musalasoft.droneapi.dto.MedicationDTO;
import com.musalasoft.droneapi.dto.mapper.DroneMapper;
import com.musalasoft.droneapi.dto.mapper.MedicationMapper;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.exception.object.ResourceNotFoundException;
import com.musalasoft.droneapi.repo.DroneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private DroneMapper droneMapper;
    @Autowired
    private MedicationMapper medicationMapper;

    public Drone regesterDrone(DroneDTO droneDTO) {
        log.info("Converting DroneDTO to Drone Entity and persisting into Database");
        if (droneRepository.count() == AppConstants.MAX_FLEET_SIZE)
            throw new RuntimeException("Drone Fleet Size Exceeded");
        /**
         * Drone state should be in IDLE state and weight =0 initially
         */
        droneDTO.setState(State.IDLE);
        droneDTO.setWeight(0.0);
        return droneRepository.save(droneMapper.from(droneDTO));
    }

    public List<DroneDTO> getAvailableDrones() {
        log.info("Retrieving list of available drones ");
        List<State> states = new ArrayList<>();
        states.add(State.IDLE);
        states.add(State.LOADING);
        return droneRepository.findAllByStateInAndBatteryCapacityGreaterThanEqual(states, 25.0)
                .stream()
                .map(droneMapper::from)
                .collect(Collectors.toList());
    }

    public Double findDroneBatteryCapacity(String serialNumber) {
        log.info("Retrieving Battery capacity for {} drone ", serialNumber);
        if (droneRepository.getCapacityForSerial(serialNumber) != null)
            return droneRepository.getCapacityForSerial(serialNumber);
        throw ResourceNotFoundException.of("Battery Capacity for the Drone  " + serialNumber + " can not be found !");
    }

    public List<MedicationDTO> findLoadedMedication(String serialNumber) {
        log.info("Retrieving list of loaded medication for {} drone ", serialNumber);
        return droneRepository.findAllBySerialNumberAndStateIn(serialNumber, Arrays.asList(State.LOADED, State.DELIVERING, State.DELIVERED))
                .stream()
                .map(medicationMapper::from)
                .collect(Collectors.toList());
    }
}