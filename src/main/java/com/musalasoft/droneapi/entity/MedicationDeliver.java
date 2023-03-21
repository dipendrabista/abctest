package com.musalasoft.droneapi.entity;

import com.musalasoft.droneapi.entity.audit.CreateUpdateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class MedicationDeliver extends CreateUpdateAudit<String> {
    @Id
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deliver_id", referencedColumnName = "id")
    private DroneLoad droneLoad;
}
