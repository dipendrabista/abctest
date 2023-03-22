package com.musalasoft.droneapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DroneLoad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serialNumber", referencedColumnName = "serialNumber")
    private Drone drone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "code", referencedColumnName = "code", unique = true)
    private Medication medication;

}
