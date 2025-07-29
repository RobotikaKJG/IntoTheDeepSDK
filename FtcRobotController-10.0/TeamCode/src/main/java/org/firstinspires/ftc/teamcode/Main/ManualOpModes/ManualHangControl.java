package org.firstinspires.ftc.teamcode.Main.ManualOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.Main.Dependencies;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.OuttakeSlideControl;

@TeleOp
public class ManualHangControl extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1,gamepad2, telemetry);
        OuttakeSlideControl outtakeSlideControl = new OuttakeSlideControl(dependencies.motorControl,dependencies.sensorControl);
        int slidePosition = 0;
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad prevGamepad1 = new Gamepad();
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        waitForStart();

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


            telemetry.update();
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
                outtakeSlideControl.setSlidePosition(900);
            }
            if(dependencies.edgeDetection.rising(GamepadIndexValues.dpadDown))
            {
                outtakeSlideControl.setSlidePosition(0);
            }

            if(gamepad1.right_trigger > 0) {
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontLeft, -HangConstants.motorSpeed);
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontRight, HangConstants.motorSpeed);

            }
            else if(gamepad1.left_trigger > 0) {
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontLeft, HangConstants.motorSpeed);
                dependencies.motorControl.setMotorSpeed(MotorConstants.frontRight, -HangConstants.motorSpeed);
            }
        }
    }
}
