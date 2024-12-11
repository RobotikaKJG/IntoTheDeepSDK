package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.SubsystemControl;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class IterativeController {
    private final MotorControl motorControl;
    private final Gamepad gamepad1;
    private final Gamepad currentGamepad1 = new Gamepad();
    private final Gamepad prevGamepad1 = new Gamepad();
    private final EdgeDetection edgeDetection;
    private final DrivebaseController drivebaseController;
    private final SubsystemControl subsystemControl;
    private final IntakeControl intakeControl;
    private final OuttakeControl outtakeControl;
    private final SensorControl sensorControl;

    public IterativeController(Dependencies dependencies) {
        drivebaseController = dependencies.createDrivebaseController();
        gamepad1 = dependencies.gamepad1;
        edgeDetection = dependencies.edgeDetection;
        motorControl = dependencies.motorControl;
        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);
        subsystemControl = dependencies.createSubsystemControl();
        intakeControl = dependencies.createIntakeControl();
        outtakeControl = dependencies.createOuttakeControl();
        sensorControl = dependencies.sensorControl;
        IntakeStates.setInitialStates();
        OuttakeStates.setInitialStates();
        ControlStates.setInitialStates();
    }

    public void TeleOp() {
        updateCommonValues();

        subsystemControl.update();
        intakeControl.update();
        outtakeControl.update();

        sensorControl.updateColor();
        drivebaseController.updateState();
    }


    private void updateCommonValues() {
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);
        motorControl.setMotors(MotorConstants.notSlide);
    }
}
