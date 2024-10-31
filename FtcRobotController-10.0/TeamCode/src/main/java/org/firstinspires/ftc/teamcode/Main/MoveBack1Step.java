package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeSlideProperties;

@TeleOp
public class MoveBack1Step extends LinearOpMode {

    private double prevTime;
    /**
     * @noinspection RedundantThrows
     */
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1, telemetry);
        IntakeSlideControl intakeSlideControl = new IntakeSlideControl(dependencies.motorControl);
        IntakeSlideProperties intakeSlideProperties = new IntakeSlideProperties();
        intakeSlideControl.setSlidePosition(-intakeSlideProperties.getSlideExtensionStep());
        intakeSlideControl.limitSpeed(0.05);

        waitForStart();

        if (isStopRequested()) return;
    }

}