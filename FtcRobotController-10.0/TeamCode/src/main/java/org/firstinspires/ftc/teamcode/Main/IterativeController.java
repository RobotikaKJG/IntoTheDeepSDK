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
    private final IntakeExtendoTrigger intakeExtendoTrigger = new IntakeExtendoTrigger();
    private final IntakeMotorTrigger intakeMotorTrigger = new IntakeMotorTrigger();
    private final OuttakeServoController outtakeServoController;

    public IterativeController(Dependencies dependencies) {
        drivebaseController = dependencies.createDrivebaseController();
        intakeController = dependencies.createIntakeController();
        outtakeController = dependencies.createOuttakeController();
        outtakeServoController = dependencies.outtakeServoController;

        gamepad1 = dependencies.gamepad1;
        edgeDetection = dependencies.edgeDetection;
        motorControl = dependencies.motorControl;
        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);
        sensorControl = dependencies.sensorControl;
    }

    public void TeleOp() {
        updateCommonValues();

        if(intakeCanRun())
            intakeController.updateState();

        if(outtakeCanRun())
            outtakeController.updateState();

        if(edgeDetection.rising(GamepadIndexValues.dpadLeft))
            outtakeServoController.setServoState(OuttakeServoStates.downClose);

        drivebaseController.updateState(outtakeController.getState()); //either replace idle with outtakeLeft for an outtakeLeft speed reduction or remove it
    }

    private boolean intakeCanRun() {
        if (outtakeController.getState() == SubsystemState.Idle)
            if(intakeController.getState() == SubsystemState.Idle && intakeRisingEdge())
                return !isColorMatch();
            else
                return true;
        return false;
    }

    private boolean isColorMatch()
    {
        return sensorControl.isColorMatch(IntakeConstants.yellow, IntakeConstants.threshold) || sensorControl.isColorMatch(IntakeConstants.allianceColor, IntakeConstants.threshold);
    }

    private boolean intakeRisingEdge()
    {
        return edgeDetection.rising(intakeMotorTrigger.getTrigger()) || edgeDetection.rising(intakeExtendoTrigger.getTrigger());
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
