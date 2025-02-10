package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;


/**
 * This class runs the loop of the autonomous period
 */
public class AutonomousControl {

    private final MotorControl motorControl;
    private final SampleAuton sampleAuton;
    private final SpecimenAuton specimenAuton;
    private final IntakeControl intakeControl;
    private final OuttakeControl outtakeControl;
    private final SensorControl sensorControl;

    public AutonomousControl(MotorControl motorControl, SampleAuton sampleAuton, SpecimenAuton specimenAuton, IntakeControl intakeControl, OuttakeControl outtakeControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.sampleAuton = sampleAuton;
        this.specimenAuton = specimenAuton;
        this.intakeControl = intakeControl;
        this.outtakeControl = outtakeControl;
        this.sensorControl = sensorControl;
        IntakeStates.setInitialStates();
        OuttakeStates.setInitialStates();
        ButtonStates.setInitialStates();
    }

    public void startAutonomous() {
        switch (GlobalVariables.autonomousMode) {
            case sampleAuton:
                sampleAuton.start();
                break;
            case specimenAuton:
                specimenAuton.start();
        }
    }

    public void runAutonomous() {
        switch (GlobalVariables.autonomousMode) {
            case sampleAuton:
                sampleAuton.run();
                break;
            case specimenAuton:
                specimenAuton.run();
                break;
        }
        updateSubsystems();
        motorControl.setMotors(MotorConstants.notDrive); // drive motors are controlled by roadrunner
    }

    public void updateSubsystems(){
        intakeControl.update();
        outtakeControl.update();
        sensorControl.updateColor();
    }
}

