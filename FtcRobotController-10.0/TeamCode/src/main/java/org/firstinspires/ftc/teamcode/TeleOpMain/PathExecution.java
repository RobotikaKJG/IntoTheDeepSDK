package org.firstinspires.ftc.teamcode.TeleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Localisation.Camera.AprilTagProcessor;
import java.util.ArrayList;

@TeleOp(name = "PathExecution", group = "TeleOp")
public class PathExecution extends OpMode {

    private DcMotor backRightMotor;
    private AprilTagProcessor aprilTagProcessor;
    private boolean tagDetected = false;

    @Override
    public void init() {
        // Initialize the motor and map it to the name 'backRightMotor'
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize the AprilTag processor
        aprilTagProcessor = new AprilTagProcessor();
        aprilTagProcessor.initializeCamera(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        if (!tagDetected) {
            // Process AprilTags
            ArrayList<String> tags = aprilTagProcessor.processTags();
            for (String tagInfo : tags) {
                telemetry.addData("Tag Info", tagInfo);
                if (tagInfo.contains("AprilTag ID: 11")) {
                    tagDetected = true;
                    stopMotors();
                    telemetry.addData("Status", "Tag ID 11 Detected! Robot control disabled.");
                    break;
                }
            }
            telemetry.update();
        } else {
            stopMotors(); // Ensures the robot doesn't move after tag detection
        }

        // Driver can control motor until the tag is detected
        if (!tagDetected) {
            double power = -gamepad1.right_stick_y;
            backRightMotor.setPower(power);
            telemetry.addData("Motor Power", power);
        }
        telemetry.update();
    }

    private void stopMotors() {
        backRightMotor.setPower(0);
    }
}
