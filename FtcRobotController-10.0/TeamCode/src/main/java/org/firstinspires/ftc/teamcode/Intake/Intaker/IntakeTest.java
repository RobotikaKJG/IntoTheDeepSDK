package org.firstinspires.ftc.teamcode.Intake.Intaker;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Expansion Arm and Servo Control")
public class IntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Define the expansion arm motor
        DcMotor expansionArm = hardwareMap.dcMotor.get("expansionArm");

        // Define the intake servo
        Servo intakeServo = hardwareMap.servo.get("intakeServo");

        // Set the motor to stop when no power is applied
        expansionArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set initial position for the servo
        intakeServo.setPosition(0.5);  // Middle position (adjust as necessary for your robot)

        waitForStart();

        while (opModeIsActive()) {
            // Control the expansion arm motor
            if (gamepad1.right_bumper) {  // R1 maps to right_bumper
                expansionArm.setPower(0.2);  // Move the motor forward slowly
            } else if (gamepad1.right_trigger > 0.1) {  // R2 is the right trigger
                expansionArm.setPower(-0.2);  // Move the motor backward slowly
            } else {
                expansionArm.setPower(0.0);  // Stop the motor
            }

            // Control the intake servo
            if (gamepad1.left_bumper) {  // L1 maps to left_bumper
                intakeServo.setPosition(1.0);  // Rotate in one direction
            } else if (gamepad1.left_trigger > 0.1) {  // L2 is the left trigger
                intakeServo.setPosition(0.0);  // Rotate in the opposite direction
            }

            // Telemetry to monitor motor and servo power
            telemetry.addData("Expansion Arm Power", expansionArm.getPower());
            telemetry.addData("Servo Position", intakeServo.getPosition());
            telemetry.update();
        }
    }
}
