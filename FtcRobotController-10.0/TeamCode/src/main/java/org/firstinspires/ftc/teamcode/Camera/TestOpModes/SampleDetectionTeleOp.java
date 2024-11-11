package org.firstinspires.ftc.teamcode.Camera.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Camera.SampleDetection.SampleDetection;

@TeleOp(name = "Sample Detection TeleOp")
public class SampleDetectionTeleOp extends LinearOpMode {

    private SampleDetection sampleDetection;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize SampleDetection with telemetry and hardware map
        sampleDetection = new SampleDetection(telemetry, hardwareMap);

        // Initialize the camera
        sampleDetection.initializeCamera();

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Update telemetry with detected object information
            sampleDetection.updateTelemetry();

            sleep(50);  // Control loop speed
        }

        // Stop the camera when done
        sampleDetection.stopCamera();
    }
}
