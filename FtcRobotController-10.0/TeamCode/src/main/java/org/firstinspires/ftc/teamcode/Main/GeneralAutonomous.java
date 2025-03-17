package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//import org.firstinspires.ftc.teamcode.Autonomous.AutonomousControl;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousControl;
import org.firstinspires.ftc.teamcode.Autonomous.SelectStartVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;


@Autonomous
public class GeneralAutonomous extends LinearOpMode {

    private SampleMecanumDrive drive;
    private AutonomousControl autonomousControl;

    @Override
    public void runOpMode() {

        initialise();

        while (!isStarted() && !isStopRequested()) {
            updateAutonData();
        }

        begin();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //Emergency stop
            if (gamepad1.triangle)
                break;
            autonomousControl.runAutonomous();
            drive.update();
            telemetry.update();
        }
    }

    private void initialise() {
        //noinspection unused
        SelectStartVariables selectStartVariables = new SelectStartVariables(gamepad1, telemetry);

        //Needs to be set to false for SensorControl initialisation through AutonomousDependencies
        GlobalVariables.wasAutonomous = false;
        GlobalVariables.isAutonomous = true;

        AutonomousDependencies dependencies = new AutonomousDependencies(hardwareMap, gamepad1,gamepad2, telemetry);

        drive = dependencies.drive;
        autonomousControl = dependencies.autonomousControl;

        dependencies.servoControl.setServoStartPos();
    }

    private void updateAutonData() {
        telemetry.addData("Auton:", GlobalVariables.autonomousMode);
        //telemetry.addData("Current position: ",aprilTagCameraControl.getCurrentPosition());
        telemetry.update();
        // Slow down CPU cycles
        sleep(100);
    }

    private void begin() {
        //Camera can be stopped as it is no longer needed
        autonomousControl.startAutonomous();

        //Update variable that autonomous happened for the driver oriented rotation after it
        GlobalVariables.wasAutonomous = true;
    }
}

