package com.musalasoft.droneapi.dto;

import com.musalasoft.droneapi.constants.State;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DroneStatus {

    @NotNull
    private State status;

    private Double batteryRemaining;
}
