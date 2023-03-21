package com.musalasoft.droneapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class DroneLoad {
    @Id
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serialNumber", referencedColumnName = "serialNumber")
    private Drone drone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code", referencedColumnName = "code", unique = true)
    private Medication medication;

}
