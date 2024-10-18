package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Drone.DroneController;
import org.firstinspires.ftc.teamcode.Subsystems.Hang.HangController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;
import org.firstinspires.ftc.teamcode.Subsystems.OldIntake.OldIntakeController;

public class IterativeController {
    OldIntakeController oldIntakeController;
    HangController hangController;
    DroneController droneController;
    private final MotorControl motorControl;
    private final Gamepad gamepad1;
    private final Gamepad currentGamepad1 = new Gamepad();
    private final Gamepad prevGamepad1 = new Gamepad();
    private final EdgeDetection edgeDetection;
    private final DrivebaseController drivebaseController;
    private IntakeController intakeController;

    public IterativeController(Dependencies dependencies) {
        //oldIntakeController = dependencies.createIntakeController();
//        outtakeController = dependencies.createOuttakeController();
//        hangController = dependencies.createHangController();
        droneController = dependencies.createDroneController();
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
        //intake and outtake need to be mutually exclusive to prevent breaking
//        if (intakeCanRun())
//            oldIntakeController.updateState();
//
//        if (outtakeCanRun())
//            outtakeController.updateState();
//
//        if(hangCanRun())
//            hangController.updateState();
        intakeController.updateState();


        //Drone can run with anything else, as it is isolated
//        droneController.updateState();
        drivebaseController.updateState(SubsystemState.Idle);
    }

//    private boolean intakeCanRun() {
//        return outtakeController.getOuttakeState() == SubsystemState.Idle && hangController.getHangState() == SubsystemState.Idle;
//    }

    private boolean outtakeCanRun() {
        return oldIntakeController.getIntakeState() == SubsystemState.Idle;// && hangController.getHangState() == SubsystemState.Idle;
    }

    private boolean hangCanRun() {
        return oldIntakeController.getIntakeState() == SubsystemState.Idle;// && outtakeController.getOuttakeState() == SubsystemState.Idle;
    }

    private void updateCommonValues() {
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);
        motorControl.setMotors(MotorConstants.notSlide);
    }
}
