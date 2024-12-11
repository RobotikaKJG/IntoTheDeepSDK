package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeExtendoTrigger;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeMotorTrigger;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeServoController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeServoStates;

public class IterativeController {
    private final MotorControl motorControl;
    private final Gamepad gamepad1;
    private final Gamepad currentGamepad1 = new Gamepad();
    private final Gamepad prevGamepad1 = new Gamepad();
    private final EdgeDetection edgeDetection;
    private final DrivebaseController drivebaseController;
    private final IntakeController intakeController;
    private final OuttakeController outtakeController;
    private final SensorControl sensorControl;

    public IterativeController(Dependencies dependencies) {
        drivebaseController = dependencies.createDrivebaseController();
        gamepad1 = dependencies.gamepad1;
        edgeDetection = dependencies.edgeDetection;
        motorControl = dependencies.motorControl;
        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);
        sensorControl = dependencies.sensorControl;
    }

    public void TeleOp() {
        updateCommonValues();


    }


    private void updateCommonValues() {
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);
        motorControl.setMotors(MotorConstants.notSlide);
    }
}
