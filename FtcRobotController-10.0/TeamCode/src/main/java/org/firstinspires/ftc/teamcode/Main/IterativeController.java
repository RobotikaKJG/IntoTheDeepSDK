package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
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
//    private final Gamepad gamepad2;
//    private final Gamepad currentGamepad2 = new Gamepad();
//    private final Gamepad prevGamepad2 = new Gamepad();
    private final EdgeDetection edgeDetection;
//    private final EdgeDetection gamepad2EdgeDetection;
    private final DrivebaseController drivebaseController;
    private final StandardTrackingWheelLocalizer localizer;
    private final SubsystemControl subsystemControl;
    //private final SubsystemControl subsystemControl2;
    private final IntakeControl intakeControl;
    private final OuttakeControl outtakeControl;
    private final SensorControl sensorControl;

    public IterativeController(Dependencies dependencies) {
        drivebaseController = dependencies.createDrivebaseController();
        gamepad1 = dependencies.gamepad1;
//        gamepad2 = dependencies.gamepad2;
        edgeDetection = dependencies.edgeDetection;
//        gamepad2EdgeDetection = dependencies.gamepad2EdgeDetection;
        motorControl = dependencies.motorControl;
        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);
        localizer = dependencies.localizer;
        subsystemControl = dependencies.createSubsystemControl();
        //subsystemControl2 = dependencies.createSubsystemControl2();
        intakeControl = dependencies.createIntakeControl();
        outtakeControl = dependencies.createOuttakeControl();
        sensorControl = dependencies.sensorControl;
        IntakeStates.setInitialStates();
        OuttakeStates.setInitialStates();
        ControlStates.setInitialStates();
    }

    public void TeleOp() {
        updateCommonValues();
        drivebaseController.updateState();
        subsystemControl.update();

//        if(gamepad1Active()) {
//            GlobalVariables.slowMode = false;
//        subsystemControl.update();
        //}
//        else if(gamepad2Active()) {
//            GlobalVariables.slowMode = true;
//            subsystemControl2.update();
//        }

        sensorControl.updateDistance();
        if(sensorControl.getDistance() < 70)
            sensorControl.updateColor();
        else{
            sensorControl.currentColor = 0;
        }

        intakeControl.update();
        outtakeControl.update();
    }


    private void updateCommonValues() {
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

//        prevGamepad2.copy(currentGamepad2);
//        currentGamepad2.copy(gamepad2);
//        gamepad2EdgeDetection.refreshGamepadIndex(currentGamepad2, prevGamepad2);

        motorControl.setMotors(MotorConstants.notSlide);
        localizer.update();
    }

//    private boolean gamepad1Active(){
//        return currentGamepad1.square || currentGamepad1.triangle || currentGamepad1.dpad_up || currentGamepad1.dpad_down
//                || !currentGamepad1.atRest() || currentGamepad1.left_bumper || currentGamepad1.left_trigger != 0
//                || currentGamepad1.right_bumper || currentGamepad1.right_trigger != 0;
////        return true;
//    }
//
//    private boolean gamepad2Active(){
//        return currentGamepad2.square || currentGamepad2.triangle || currentGamepad2.dpad_up || currentGamepad2.dpad_down
//                || !currentGamepad2.atRest() || currentGamepad2.left_bumper || currentGamepad2.left_trigger != 0
//                || currentGamepad2.right_bumper || currentGamepad2.right_trigger != 0;
////        return false;
//    }
}
