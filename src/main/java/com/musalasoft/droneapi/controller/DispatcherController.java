package com.musalasoft.droneapi.controller;

import com.musalasoft.droneapi.dto.DroneDTO;
import com.musalasoft.droneapi.dto.MedicationDTO;
import com.musalasoft.droneapi.dto.ResponseDTO;
import com.musalasoft.droneapi.service.DroneLoadService;
import com.musalasoft.droneapi.service.DroneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author Dipendra Bista
 * @since 2023/3/10
 * <p>
 * Dispatcher controller is the controller which communicates the drones
 * </p/
 */
@Api(description = "This Controller allows client to Communicate With Drone.")


@OpenAPIDefinition(servers = {@Server(url = "http://localhost:${server.port}")},
        info = @Info(title = "Drone Rest API", version = "v1", description = "Drone Service Api which allows client to communicate with Drone"))
@Slf4j
@RestController
@RequestMapping("/api/dispatcher-controller/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor = @_(@Autowired))
public class DispatcherController {
    DroneService droneService;
    DroneLoadService droneLoadService;

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register Drone")
    public ResponseDTO register(@Validated @RequestBody DroneDTO droneDTO) {
        log.info("Registering  new Drone : ", droneDTO);
        return ResponseDTO.builder()
                .data(droneService.registerDrone(droneDTO))
                .build();
    }

    @PostMapping(path = "load-medication", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Loading a drone with medication items")
    public ResponseDTO loadMedication(String serialNumber, List<String> medicationCodes) {
        log.info("Load drone {} with medications {}", serialNumber, medicationCodes);
        return ResponseDTO.builder()
                .data(droneLoadService.loadMedication(serialNumber, medicationCodes))
                .build();
    }

    @GetMapping(path = "check-loaded-medication", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Checking loaded medication items for a given drone")
    public ResponseDTO checkLoadedMedication(String serialNumber) {
        log.info("Retrieving list of loaded medication for the given drone {}", serialNumber);
        List<MedicationDTO> medicationDTOs = droneService.findLoadedMedication(serialNumber);
        if (CollectionUtils.isEmpty(medicationDTOs)) {
            log.info("Drone {} is not loaded any medication ", serialNumber);
            return ResponseDTO.builder()
                    .data(Collections.EMPTY_LIST)
                    .build();
        }
        return ResponseDTO.builder()
                .data(medicationDTOs)
                .build();
    }


    @GetMapping(value = "check-available-drones", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Checking available drones for loading")
    public ResponseDTO checkAvailableDronesForLoading() {
        log.info("Retrieving list of available drones for loading ");
        List<DroneDTO> droneDTOS = droneService.getAvailableDrones();
        if (CollectionUtils.isEmpty(droneDTOS)) {
            return ResponseDTO.builder()
                    .data(Collections.emptyList())
                    .build();
        }
        return ResponseDTO.builder()
                .data(droneDTOS)
                .build();
    }

    @GetMapping(path = "check-battery-level", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Check drone battery level for a given drone")
    public ResponseDTO checkBatteryLevel(String serialNumber) {
        log.info("Checking battery levels of given Drone {}", serialNumber);
        return ResponseDTO.builder()
                .data(droneService.findDroneBatteryCapacity(serialNumber))
                .build();
    }


    @GetMapping(path = "/deliver", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Check drone battery level for a given drone")
    public ResponseDTO deliver(String serialNumber) {
        log.info("Checking battery levels of given Drone {}", serialNumber);
        return ResponseDTO.builder()
                .data(droneService.findDroneBatteryCapacity(serialNumber))
                .build();
    }
}
