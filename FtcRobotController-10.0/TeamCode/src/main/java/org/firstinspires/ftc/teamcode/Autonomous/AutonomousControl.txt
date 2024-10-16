package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Main.GlobalVariables.startPos;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;

public class AutonomousControl {

    private final MotorControl motorControl;
    private final AudienceSideAuton audienceSideAuton;
    private final BacksideAuton backsideAuton;

    public AutonomousControl(MotorControl motorControl, AudienceSideAuton audienceSideAuton, BacksideAuton backsideAuton) {
        this.motorControl = motorControl;
        this.audienceSideAuton = audienceSideAuton;
        this.backsideAuton = backsideAuton;
    }

    public void startAutonomous() {
        switch (startPos) {
            case AudienceSide:
                audienceSideAuton.start();
                break;
            case Backside:
                backsideAuton.start();
                break;
        }
    }

    public void runAutonomous() {
        switch (startPos) {
            case AudienceSide:
                audienceSideAuton.run();
                break;
            case Backside:
                backsideAuton.run();
                break;
        }
        motorControl.setMotors(MotorConstants.notDrive); // drive motors are controlled by roadrunner
    }
}

