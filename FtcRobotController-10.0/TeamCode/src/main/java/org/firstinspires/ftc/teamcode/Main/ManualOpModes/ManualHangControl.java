package org.firstinspires.ftc.teamcode.Main.ManualOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Main.Dependencies;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.LockServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.OuttakeSlideControl;

@TeleOp
public class ManualHangControl extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1,gamepad2, telemetry);
        ServoControl servoControl = new ServoControl(hardwareMap);
        OuttakeSlideControl outtakeSlideControl = new OuttakeSlideControl(dependencies.motorControl,dependencies.sensorControl);
        int slidePosition = 0;
        int wheelPosition = 0;
        LockServoStates lockServoState = LockServoStates.down;
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad prevGamepad1 = new Gamepad();
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        waitForStart();

        servoControl.setServoPos(ServoConstants.PTORight, 0.06);
        servoControl.setServoPos(ServoConstants.PTOLeft, 0.99);
//        dependencies.motorControl.setMotorSpeed(MotorConstants.frontWheels, HangConstants.motorSpeed);
//        dependencies.motorControl.setMotors(MotorConstants.frontWheels);
//        dependencies.motorControl.setZeroPowerBehavior(MotorConstants.frontWheels, DcMotor.ZeroPowerBehavior.BRAKE);

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            if(gamepad1.triangle) break;
            dependencies.edgeDetection.refreshGamepadIndex(currentGamepad1,prevGamepad1);
            telemetry.addLine("Press DPAD UP to fully extend the slides");
            telemetry.addLine("Press DPAD DOWN to fully retract the slides");
            telemetry.addLine();
            telemetry.addLine("Press RIGHT TRIGGER to move the front wheels forward");
            telemetry.addLine("Press LEFT TRIGGER to move the front wheels backward");
            telemetry.addLine();
            telemetry.addData("Left (looking from back) motor pos", dependencies.motorControl.getMotorPosition(MotorConstants.frontLeft));
            telemetry.addData("Right (looking from back) motor pos", dependencies.motorControl.getMotorPosition(MotorConstants.frontRight));
            telemetry.addLine();
            telemetry.addLine("Press CIRCLE to move slides down a bit");
            telemetry.addLine("Press CIRCLE to move slides up a bit");
            telemetry.addLine();
            telemetry.addLine("Press CROSS to lock/unlock servos");
            telemetry.addData("Servos locked", lockServoState);
            telemetry.addData("wheel pos", wheelPosition);



            telemetry.update();
            if(dependencies.edgeDetection.rising(GamepadIndexValues.cross))
            {
                if(lockServoState == LockServoStates.down) {
                    servoControl.setServoPos(ServoConstants.PTORight, 1);
                    servoControl.setServoPos(ServoConstants.PTOLeft, 0.05);
                    lockServoState = LockServoStates.up;
                }
                else if(lockServoState == LockServoStates.up){
                    servoControl.setServoPos(ServoConstants.PTORight, 0.53);
                    servoControl.setServoPos(ServoConstants.PTOLeft, 0.52);
                    lockServoState = LockServoStates.half;
                }
                else if(lockServoState == LockServoStates.half){
                    servoControl.setServoPos(ServoConstants.PTORight, 0.06);
                    servoControl.setServoPos(ServoConstants.PTOLeft, 0.99);
                    lockServoState = LockServoStates.down;
                }
            }


            if(dependencies.edgeDetection.rising(GamepadIndexValues.circle))
            {
                slidePosition -= 50;
                outtakeSlideControl.setSlidePosition(slidePosition);
            }
            if(dependencies.edgeDetection.rising(GamepadIndexValues.square))
            {
                slidePosition += 50;
                outtakeSlideControl.setSlidePosition(slidePosition);
            }

            if(dependencies.edgeDetection.rising(GamepadIndexValues.dpadUp))
            {
                outtakeSlideControl.setSlidePosition(HangConstants.slidesUpHeight);
            }
            if(dependencies.edgeDetection.rising(GamepadIndexValues.dpadDown))
            {
                outtakeSlideControl.setSlidePosition(HangConstants.slidesDownHeight);
            }

            if(dependencies.edgeDetection.rising(GamepadIndexValues.rightTrigger)) {
                wheelPosition += 200;
                dependencies.motorControl.setMotorPos(MotorConstants.frontWheels, wheelPosition);
                dependencies.motorControl.setMotorMode(MotorConstants.frontWheels, DcMotor.RunMode.RUN_TO_POSITION);
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontWheels,0.5);
                dependencies.motorControl.setMotors(MotorConstants.frontWheels);
            }
            else if(dependencies.edgeDetection.rising(GamepadIndexValues.leftTrigger)) {
                wheelPosition -= 200;
                dependencies.motorControl.setMotorPos(MotorConstants.frontWheels, wheelPosition);
                dependencies.motorControl.setMotorMode(MotorConstants.frontWheels, DcMotor.RunMode.RUN_TO_POSITION);
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontWheels,0.5);
                dependencies.motorControl.setMotors(MotorConstants.frontWheels);
            }


        }
    }
}
