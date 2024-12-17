package org.firstinspires.ftc.teamcode.TeleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.DriveTrain.DriveBase;
import org.firstinspires.ftc.teamcode.Localisation.Camera.AprilTagProcessor;
import org.openftc.apriltag.AprilTagDetection;

@TeleOp(name = "PathExecution", group = "TeleOp")
public class PathExecution extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize drive base
        DriveBase driveBase = new DriveBase(this);
        AprilTagProcessor aprilTagProcessor = new AprilTagProcessor();
        aprilTagProcessor.initializeCamera(hardwareMap);

        double repositionPower = 0.15; // 15% power

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double offsetZ = 0; // Initialize offset value

            // Process AprilTags to find ID 11
            for (AprilTagDetection detection : aprilTagProcessor.getLatestDetections()) {
                if (detection.id == 11) {
                    offsetZ = detection.pose.x; // Get the offset
                    telemetry.addData("Status", "Tag ID 11 Detected! Repositioning...");
                    telemetry.addData("Offset Z", offsetZ);

                    // Reposition to align Z offset to 0
                    double powerLeft = repositionPower;
                    double powerRight = repositionPower;
                    if (offsetZ > 0.5) {
                        powerLeft = -repositionPower; // Adjust motors for left repositioning
                    } else if (offsetZ < -0.5) {
                        powerRight = -repositionPower; // Adjust motors for right repositioning
                    } else {
                        powerLeft = 0;
                        powerRight = 0;
                        telemetry.addData("Status", "Reposition Complete.");
                    }

                    driveBase.setMotorPowers(powerLeft, powerLeft, powerRight, powerRight);
                    break;
                }
            }
            telemetry.update();
        }
    }
}
