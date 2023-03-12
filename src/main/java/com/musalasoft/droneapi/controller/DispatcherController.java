package com.musalasoft.droneapi.controller;

import com.musalasoft.droneapi.exception.object.ResponseDTO;
import com.musalasoft.droneapi.service.DroneService;
import com.musalasoft.droneapi.service.MedicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dipendra Bista
 * @since 2023/3/10
 * <p>
 * Dispatcher controller is the controller which communicates the drones
 * </p/
 */
@Api(description = "This Controller allows client to Communicate With Drone.")
@Slf4j
@RestController
@RequestMapping("/api/dispatcher-controller/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor = @_(@Autowired))
public class DispatcherController {
    DroneService droneService;
    MedicationService medicationService;

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register Drone")
    public ResponseDTO regester() {
        return ResponseDTO.builder()
                .data(null)
                .build();
    }

    @PostMapping(path = "load-medication", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Loading a drone with medication items")
    public ResponseDTO loadMedication() {
        return ResponseDTO.builder()
                .data(null)
                .build();
    }

    @GetMapping(path = "check-loaded-medication", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Checking loaded medication items for a given drone")
    public ResponseDTO checkLoadedMedication() {
        return ResponseDTO.builder()
                .data(null)
                .build();
    }

    @GetMapping(path = "check-battery-level", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Check drone battery level for a given drone")
    public ResponseDTO checkBatteryLevel(String serialID) {
        return ResponseDTO.builder()
                .data(null)
                .build();
    }

    @GetMapping(value = "check-available-drones", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Checking available drones for loading")
    public ResponseDTO checkAvailableDronesForLoading() {
        return ResponseDTO.builder()
                .data(null)
                .build();
    }
}
