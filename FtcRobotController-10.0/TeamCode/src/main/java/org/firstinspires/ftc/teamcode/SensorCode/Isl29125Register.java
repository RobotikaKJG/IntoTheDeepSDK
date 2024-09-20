package org.firstinspires.ftc.teamcode.SensorCode;


public enum Isl29125Register {
    FIRST(0),
    DEVICE_ID(0x00),
    DEVICE_RESET(0x00),
    CONFIGURATION_1(0x01),
    CONFIGURATION_2(0x02),
    CONFIGURATION_3(0x03),
    LOW_THRESHOLD_LOW_BYTE(0x04),
    LOW_THRESHOLD_HIGH_BYTE(0x05),
    HIGH_THRESHOLD_LOW_BYTE(0x06),
    HIGH_THRESHOLD_HIGH_BYTE(0x07),
    STATUS_FLAGS(0x08),
    GREEN_DATA_LOW_BYTE(0x09),
    GREEN_DATA_HIGH_BYTE(0x0A),
    RED_DATA_LOW_BYTE(0x0B),
    RED_DATA_HIGH_BYTE(0x0C),
    BLUE_DATA_LOW_BYTE(0x0D),
    BLUE_DATA_HIGH_BYTE(0x0E),
    LAST(BLUE_DATA_HIGH_BYTE.bVal);

    public int bVal;

    Isl29125Register(int bVal) {
        this.bVal = bVal;
    }
}
