package com.musalasoft.droneapi.exception.object;

public enum ExceptionMessage {
    UPDATE_FAILED("Failed to update : %s"),
    SAVE_FAILED("Failed to save : %s"),
    DELETE_FAILED("FAiled to delete : %s"),
    ALREADY_EXIST("%s is already exist. Please try creating another !");
    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
