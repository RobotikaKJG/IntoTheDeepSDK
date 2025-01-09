package org.firstinspires.ftc.teamcode.Main.ManualOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Main.Dependencies;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.OuttakeSlideControl;

@TeleOp
public class ManualSlideControl extends LinearOpMode {

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
            telemetry.addLine("Press square to extend, press circle to retract");
            telemetry.addData("Slide position", outtakeSlideControl.getSlidePosition());
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
                outtakeSlideControl.setSlidePosition(OuttakeConstants.highBasketPos);
            }
            if(dependencies.edgeDetection.rising(GamepadIndexValues.dpadDown))
            {
                outtakeSlideControl.setSlidePosition(0);
            }
        }
    }

}