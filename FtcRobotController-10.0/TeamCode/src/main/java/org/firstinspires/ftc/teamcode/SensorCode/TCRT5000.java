package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.configuration.annotations.AnalogSensorType;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;

@AnalogSensorType
@DeviceProperties(name = "TCRT5000 Reflectance sensor", xmlTag = "TCRT5000")

public class TCRT5000 extends AnalogInput {
    @Override
    public Manufacturer getManufacturer() {

        return Manufacturer.Other;
    }

    @Override
    public String getDeviceName() {

        return "TCRT5000 Reflectance sensor";
    }

    public TCRT5000(AnalogInputController analogInputController, int port) {
        super(analogInputController, port);
    }

    public double readVoltage() {
        return super.getVoltage();
    }
}