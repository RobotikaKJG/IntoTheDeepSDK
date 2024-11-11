package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeSlideProperties;

@TeleOp
public class ManualExtendoControl extends LinearOpMode {

    private double prevTime;
    /**
     * @noinspection RedundantThrows
     */
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1, telemetry);
        IntakeSlideControl intakeSlideControl = new IntakeSlideControl(dependencies.motorControl,dependencies.sensorControl);
        IntakeSlideProperties intakeSlideProperties = new IntakeSlideProperties();
        //intakeSlideControl.setSlidePosition(-intakeSlideProperties.getSlideExtensionStep());
        //intakeSlideControl.limitSpeed(0.05);
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
            if(dependencies.edgeDetection.rising(GamepadIndexValues.circle))
            {
                slidePosition -= 50;
                intakeSlideControl.setSlidePosition(slidePosition);
            }
            if(dependencies.edgeDetection.rising(GamepadIndexValues.square))
            {
                slidePosition += 50;
                intakeSlideControl.setSlidePosition(slidePosition);
            }
        }
    }

}