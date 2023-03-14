package com.musalasoft.droneapi.service;

import com.musalasoft.droneapi.dto.MedicationDTO;
import com.musalasoft.droneapi.dto.mapper.MedicationMapper;
import com.musalasoft.droneapi.entity.Drone;
import com.musalasoft.droneapi.repo.DroneRepository;
import com.musalasoft.droneapi.repo.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private MedicationMapper medicationMapper;

    public List<MedicationDTO> findLoadedMedication(String serialNumber) {
        log.info("Retrieving drone details with serial number {}", serialNumber);
        Drone droneEntity = droneRepository.findById(serialNumber).orElseThrow(() -> new EntityNotFoundException("Drone " + serialNumber + " is not fund"));
        log.info("Drone Details {}", droneEntity);
        return medicationRepository.findAllByDrone(droneEntity)
                .stream().map(medicationMapper::from).collect(Collectors.toList());
    }
}
