package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousControl;
import org.firstinspires.ftc.teamcode.Autonomous.SelectStartVariables;
//import org.firstinspires.ftc.teamcode.Camera.AprilTagCameraControl;
import org.firstinspires.ftc.teamcode.Camera.PropCameraControl;
import org.firstinspires.ftc.teamcode.Camera.CameraConstants;
import org.firstinspires.ftc.teamcode.Camera.CameraInterface;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;


@Autonomous
public class GeneralAutonomous extends LinearOpMode {

    private CameraInterface cameraInterface;
    private SampleMecanumDrive drive;
    private AutonomousControl autonomousControl;
    private PropCameraControl propCameraControl;
    //private AprilTagCameraControl aprilTagCameraControl;

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
            //telemetry.update();
        }
    }

    private void initialise() {
        //noinspection unused
        SelectStartVariables selectStartVariables = new SelectStartVariables(gamepad1, telemetry);

        //Needs to be set to false for SensorControl initialisation through AutonomousDependencies
        GlobalVariables.wasAutonomous = false;
        GlobalVariables.isAutonomous = true;

        AutonomousDependencies dependencies = new AutonomousDependencies(hardwareMap, gamepad1, telemetry);

        drive = dependencies.drive;
        autonomousControl = dependencies.createAutonomousControl();

        cameraInterface = dependencies.cameraInterface;

        propCameraControl = cameraInterface.getPropCamera();
        propCameraControl.startCamera();
        //aprilTagCameraControl = cameraInterface.getAprilTagCamera();
        //aprilTagCameraControl.startCamera();
    }

    private void updateAutonData() {
        propCameraControl.calculatePropPos();
        telemetry.addData("Prop position:", GlobalVariables.propPos);
        telemetry.addData("Alliance:", GlobalVariables.alliance);
        telemetry.addData("Starting position:", GlobalVariables.startPos);
        //telemetry.addData("Current position: ",aprilTagCameraControl.getCurrentPosition());
        telemetry.update();
        // Slow down CPU cycles
        sleep(100);
    }

    private void begin() {
        //Camera can be stopped as it is no longer needed
        cameraInterface.stopCamera(CameraConstants.propCamera);
        autonomousControl.startAutonomous();

        //Update variable that autonomous happened for the driver oriented rotation after it
        GlobalVariables.wasAutonomous = true;
    }
}

