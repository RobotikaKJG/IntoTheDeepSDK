package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;

public class IterativeController {
    private final MotorControl motorControl;
    private final Gamepad gamepad1;
    private final Gamepad currentGamepad1 = new Gamepad();
    private final Gamepad prevGamepad1 = new Gamepad();
    private final EdgeDetection edgeDetection;
    private final DrivebaseController drivebaseController;
    private final IntakeController intakeController;

    public IterativeController(Dependencies dependencies) {
        drivebaseController = dependencies.createDrivebaseController();
        intakeController = dependencies.createIntakeController();

        gamepad1 = dependencies.gamepad1;
        edgeDetection = dependencies.edgeDetection;
        motorControl = dependencies.motorControl;
        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);
    }

    public void TeleOp() {
        updateCommonValues();

        intakeController.updateState();

        drivebaseController.updateState(SubsystemState.Idle); //either replace idle with outtake for an outtake speed reduction or remove it
                return !isColorMatch();
    }

//    private boolean intakeCanRun() {
//        return outtakeController.getOuttakeState() == SubsystemState.Idle;
//    }
    private boolean isColorMatch()
    {
        return sensorControl.isColorMatch(IntakeConstants.yellow, IntakeConstants.threshold) || sensorControl.isColorMatch(IntakeConstants.allianceColor, IntakeConstants.threshold);
    }

    private boolean outtakeCanRun() {
        return intakeController.getState() == SubsystemState.Idle;
    }

    private void updateCommonValues() {
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);
        motorControl.setMotors(MotorConstants.notSlide);
    }
}
