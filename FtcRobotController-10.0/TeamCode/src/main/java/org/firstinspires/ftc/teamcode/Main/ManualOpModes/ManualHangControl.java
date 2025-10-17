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
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.PTO.PTOControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.PTO.PTOStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.OuttakeSlideControl;

@TeleOp
public class ManualHangControl extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1,gamepad2, telemetry);
        ServoControl servoControl = new ServoControl(hardwareMap);
        OuttakeSlideControl outtakeSlideControl = new OuttakeSlideControl(dependencies.motorControl,dependencies.sensorControl);
        PTOControl ptoControl = new PTOControl(servoControl);
        int slidePosition = 0;
        int wheelPosition = 0;
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
            ptoControl.update();
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
            telemetry.addData("Servos locked", OuttakeStates.getPtoState());
            telemetry.addData("wheel pos", wheelPosition);



            telemetry.update();
            if(dependencies.edgeDetection.rising(GamepadIndexValues.cross))
            {
                switch(OuttakeStates.getPtoState()){
                    case closed:
                        OuttakeStates.setPtoState(PTOStates.open);
                        break;
                    case open:
                        OuttakeStates.setPtoState(PTOStates.locked);
                        break;
                    case locked:
                        OuttakeStates.setPtoState(PTOStates.closed);
                        break;
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
                wheelPosition += 100;
                dependencies.motorControl.setMotorPos(MotorConstants.frontWheels, wheelPosition);
                dependencies.motorControl.setMotorMode(MotorConstants.frontWheels, DcMotor.RunMode.RUN_TO_POSITION);
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontWheels,1);
                dependencies.motorControl.setMotors(MotorConstants.frontWheels);
            }
            else if(dependencies.edgeDetection.rising(GamepadIndexValues.leftTrigger)) {
                wheelPosition -= 100;
                dependencies.motorControl.setMotorPos(MotorConstants.frontWheels, wheelPosition);
                dependencies.motorControl.setMotorMode(MotorConstants.frontWheels, DcMotor.RunMode.RUN_TO_POSITION);
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontWheels,1);
                dependencies.motorControl.setMotors(MotorConstants.frontWheels);
            }


        }
    }
}
