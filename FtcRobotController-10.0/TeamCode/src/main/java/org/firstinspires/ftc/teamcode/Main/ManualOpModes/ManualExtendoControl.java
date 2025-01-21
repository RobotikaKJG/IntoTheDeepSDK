package org.firstinspires.ftc.teamcode.Main.ManualOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.Main.Dependencies;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.IntakeSlideControl;

@TeleOp
public class ManualExtendoControl extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1,gamepad2, telemetry);
        IntakeSlideControl intakeSlideControl = new IntakeSlideControl(dependencies.motorControl,dependencies.sensorControl);
        int slidePosition = 0;
        double startTime = 0;
        double duration = 0;
        boolean wasIf = false;
        boolean retracting = true;
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
            telemetry.addData("Slide position", intakeSlideControl.getSlidePosition());
            telemetry.addData("Duration", duration);
            telemetry.addData("Current", dependencies.motorControl.getMotorCurrent(MotorConstants.extendo));
            //telemetry.addData("Slide inner target: ", intakeSlideControl.);
            telemetry.update();
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

            if(dependencies.edgeDetection.rising(GamepadIndexValues.dpadUp))
            {
                intakeSlideControl.setSlidePosition(1650);//1740 max physical
                slidePosition = 1650;
                startTime = System.currentTimeMillis();
                wasIf = false;
            }
            if(dependencies.edgeDetection.rising(GamepadIndexValues.dpadDown))
            {
                intakeSlideControl.setSlidePosition(20);
                retracting = true;
            }

            if(retracting)
            {
                if(intakeSlideControl.isLimitSwitchPressed())
                    retracting = false;
            }

            if(intakeSlideControl.getSlidePosition() > slidePosition && !wasIf)
            {
                duration = System.currentTimeMillis() - startTime;
                wasIf = true;
            }
        }
    }

}