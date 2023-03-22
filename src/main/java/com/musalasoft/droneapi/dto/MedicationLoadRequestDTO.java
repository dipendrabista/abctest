package com.musalasoft.droneapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class MedicationLoadRequestDTO {
    private String serialNumber;
    private List<String> medicationCodes;
}
