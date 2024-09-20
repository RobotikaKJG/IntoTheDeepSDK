package org.firstinspires.ftc.teamcode.SensorCode;


public enum TCS3472Register {
    FIRST(0),
    ENABLE(0x00),
    ATIME(0x01),
    WTIME(0x03),
    AILTL(0x04),
    AILTH(0x05),
    AIHTL(0x06),
    AIHTH(0x07),
    PERS(0x0C),
    CONFIG(0x0D),
    CONTROL(0x0F),
    ID(0x12),
    STATUS(0x13),
    CDATAL(0x14),
    CDATAH(0x15),
    RDATAL(0x16),
    RDATAH(0x17),
    GDATAL(0x18),
    GDATAH(0x19),
    BDATAL(0x1A),
    BDATAH(0x1B),
    LAST(BDATAH.bVal);

    public int bVal;

    TCS3472Register(int bVal) {
        this.bVal = bVal;
    }
}
