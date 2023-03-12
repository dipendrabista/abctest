package com.musalasoft.droneapi.entity;

import com.musalasoft.droneapi.constants.Model;
import com.musalasoft.droneapi.constants.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Drone {
    @Id
    @Size(min = 1, max = 100)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    @Min(0)
    @Max(500)
    private Double weight;
    @Min(0)
    @Max(100)
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private State state;
}
