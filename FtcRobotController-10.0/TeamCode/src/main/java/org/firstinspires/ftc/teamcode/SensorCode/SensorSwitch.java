package org.firstinspires.ftc.teamcode.SensorCode;


public class SensorSwitch {

    private final int sensorCount;
    private final PCA9548A multiplexer;

    public SensorSwitch(int sensorCount, PCA9548A multiplexer) {
        this.sensorCount = sensorCount;
        this.multiplexer = multiplexer;
    }


    public void selectOutput(int sensorNumber) {
        byte selectedOutput = (byte) (1 << sensorNumber);
        multiplexer.writeNoAddress(selectedOutput);
    }
}
