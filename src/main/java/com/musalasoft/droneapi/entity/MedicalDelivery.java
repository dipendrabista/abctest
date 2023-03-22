package com.musalasoft.droneapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "medical_delivery")
@Setter
@Getter
@NoArgsConstructor
public class MedicalDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_id")
    private Integer deliveryId;

    @Column(name = "delivery_time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime deliveryTime;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "drone_load_delivery_id", referencedColumnName = "id")
//    private List<DroneLoad> droneLoads;

}
