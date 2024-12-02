package org.firstinspires.ftc.teamcode.IntakeSystem.Intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Intake Test - Continuous")
public class IntakeTest extends LinearOpMode {

    private CRServo intakeServo;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the servo
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Control servo direction with R1 and L1 buttons
            if (gamepad1.right_bumper) {
                intakeServo.setPower(1.0);  // Spin clockwise indefinitely
            } else if (gamepad1.left_bumper) {
                intakeServo.setPower(-1.0); // Spin anti-clockwise indefinitely
            } else {
                intakeServo.setPower(0.0);  // Stop spinning when no button is pressed
            }

            // Update telemetry for debugging
            telemetry.addData("Servo Power", intakeServo.getPower());
            telemetry.update();

            sleep(50);  // Control loop speed
        }
    }
}
