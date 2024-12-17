package org.firstinspires.ftc.teamcode.TeleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.DriveTrain.DriveBase;
import org.firstinspires.ftc.teamcode.Localisation.Camera.AprilTagProcessor;

@TeleOp(name = "PathExecution", group = "TeleOp")
public class PathExecution extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize drive base
        DriveBase driveBase = new DriveBase(this);
        AprilTagProcessor aprilTagProcessor = new AprilTagProcessor();
        aprilTagProcessor.initializeCamera(hardwareMap);

        boolean tagDetected = false;

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (!tagDetected) {
                // Process AprilTags
                for (String tagInfo : aprilTagProcessor.processTags()) {
                    telemetry.addData("Tag Info", tagInfo);
                    if (tagInfo.contains("AprilTag ID: 11")) {
                        tagDetected = true;
                        stopMotors(driveBase);
                        telemetry.addData("Status", "Tag ID 11 Detected! Robot control disabled.");
                        break;
                    }
                }
            }

            if (!tagDetected) {
                // Allow driver control until tag is detected
                driveBase.updateDrive(gamepad1, this);
            } else {
                stopMotors(driveBase);
            }

            telemetry.update();
        }
    }

    private void stopMotors(DriveBase driveBase) {
        driveBase.updateDrive(gamepad1, this);
        driveBase.updateDrive(null, this);
    }
}
