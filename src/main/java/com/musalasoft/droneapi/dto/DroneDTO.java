package com.musalasoft.droneapi.dto;

import com.musalasoft.droneapi.constants.Model;
import com.musalasoft.droneapi.constants.State;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Data
public class DroneDTO {
    @Size(min = 1, max = 100)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    @Range(min = 0, max = 500, message = "Drone Wight must be between 0-500gm !")
    private Double weight;
    @Range(min = 0, max = 100, message = "Drone Battery Capacity must be between 0-500gm !")
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State state;
}
