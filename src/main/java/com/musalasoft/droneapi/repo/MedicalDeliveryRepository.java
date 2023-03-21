package com.musalasoft.droneapi.repo;

import com.musalasoft.droneapi.entity.MedicalDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalDeliveryRepository extends JpaRepository<MedicalDelivery, Integer> {
}
