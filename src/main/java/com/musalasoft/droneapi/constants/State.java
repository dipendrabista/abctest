package com.musalasoft.droneapi.constants;

public enum State {
    IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING;

    public State nextState() {
        // Get the array of all values in the enum
        State[] values = State.values();
        int nextOrdinal = this.ordinal() + 1;
        if (nextOrdinal >= values.length) {
            nextOrdinal = 0;
        }
        return values[nextOrdinal];
    }

    public State prevState() {
        State[] values = State.values();
        int prevOrdinal = this.ordinal() - 1;
        if (prevOrdinal < 0) {
            prevOrdinal = values.length - 1;
        }
        return values[prevOrdinal];
    }
}
