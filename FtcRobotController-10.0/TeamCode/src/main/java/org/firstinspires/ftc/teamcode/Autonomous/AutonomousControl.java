package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;

public class AutonomousControl {

    private final MotorControl motorControl;
    private final SampleAuton sampleAuton;

    public AutonomousControl(MotorControl motorControl, SampleAuton sampleAuton) {
        this.motorControl = motorControl;
        this.sampleAuton = sampleAuton;
    }

    public void startAutonomous() {

    }

    public void runAutonomous() {

        motorControl.setMotors(MotorConstants.notDrive); // drive motors are controlled by roadrunner
    }
}

