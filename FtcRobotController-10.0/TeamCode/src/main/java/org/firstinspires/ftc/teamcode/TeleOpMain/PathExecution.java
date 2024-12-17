package org.firstinspires.ftc.teamcode.TeleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

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
        double repositionPower = 0.05; // 5% power

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double offsetZ = 0; // Initialize offset value

            if (!tagDetected) {
                // Process AprilTags
                for (String tagInfo : aprilTagProcessor.processTags()) {
                    telemetry.addData("Tag Info", tagInfo);
                    if (tagInfo.contains("AprilTag ID: 11")) {
                        tagDetected = true;
                        offsetZ = extractOffset(tagInfo);
                        telemetry.addData("Status", "Tag ID 11 Detected! Repositioning...");
                        break;
                    }
                }
            }

            if (tagDetected) {
                // Reposition to align Z offset to 0
                if (Math.abs(offsetZ) > 0.5) { // Threshold for precision
                    if (offsetZ > 0) {
                        driveBase.updateDrive(repositionGamepad(-repositionPower, 0, 0), this);
                    } else {
                        driveBase.updateDrive(repositionGamepad(repositionPower, 0, 0), this);
                    }
                } else {
                    driveBase.updateDrive(new FakeGamepad(), this);
                    telemetry.addData("Status", "Reposition Complete.");
                }
            } else {
                // Normal driver control
                if (gamepad1 != null) {
                    driveBase.updateDrive(gamepad1, this);
                }
            }

            telemetry.update();
        }
    }

    private double extractOffset(String tagInfo) {
        // Extract Z Offset value from tag information string
        String[] parts = tagInfo.split(",");
        for (String part : parts) {
            if (part.contains("Z (Offset)")) {
                return Double.parseDouble(part.replaceAll("[^0-9.-]", ""));
            }
        }
        return 0;
    }

    private Gamepad repositionGamepad(double leftStickY, double leftStickX, double rightStickX) {
        Gamepad gamepad = new FakeGamepad();
        gamepad.left_stick_y = (float) leftStickY;
        gamepad.left_stick_x = (float) leftStickX;
        gamepad.right_stick_x = (float) rightStickX;
        return gamepad;
    }

    private static class FakeGamepad extends com.qualcomm.robotcore.hardware.Gamepad {
        public FakeGamepad() {
            left_stick_y = 0;
            left_stick_x = 0;
            right_stick_x = 0;
        }
    }
}
