package org.firstinspires.ftc.teamcode.Localisation.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Localisation.Camera.AprilTagProcessor;

import java.util.ArrayList;

@TeleOp(name = "AprilTag Detection Test", group = "Linear Opmode")
public class AprilTagProcessorOpModeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Create instance of AprilTagProcessor
        AprilTagProcessor tagProcessor = new AprilTagProcessor();

        // Initialize camera
        tagProcessor.initializeCamera(hardwareMap);

        // Wait for start
        waitForStart();

        // Main loop
        while (opModeIsActive()) {
            // Process tags and get telemetry data
            ArrayList<String> tagData = tagProcessor.processTags();

            // Output data to telemetry
            for (String data : tagData) {
                telemetry.addLine(data);
            }
            telemetry.update();

            sleep(20);  // Small sleep to prevent flooding the telemetry
        }
    }
}
