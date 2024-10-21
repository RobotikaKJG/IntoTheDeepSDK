package org.firstinspires.ftc.teamcode.IntakeSystem.Intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Expansion Arm and Servo with Encoder Limits")
public class IntakeTest extends LinearOpMode {

    // Define the encoder limits
    private static final int MAX_EXTENSION_POSITION = 2950;  // Upper limit of the arm
    private static final int MIN_EXTENSION_POSITION = 0;     // Lower limit (fully retracted)

    @Override
    public void runOpMode() throws InterruptedException {
        // Define the expansion arm motor with encoder
        DcMotor expansionArm = hardwareMap.dcMotor.get("expansionArm");

        // Define the CRServo for intake
        CRServo intakeServo = hardwareMap.crservo.get("intakeServo");

        // Set the motor to stop when no power is applied and use an encoder
        expansionArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        expansionArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        expansionArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            int currentPosition = expansionArm.getCurrentPosition();  // Get the current encoder position

            // Control the expansion arm motor with limits
            if (gamepad1.right_bumper && currentPosition < MAX_EXTENSION_POSITION) {  // R1 is pressed and arm is below the maximum limit
                expansionArm.setPower(1.0);  // Move the motor forward at 100% power
            } else if (gamepad1.right_trigger > 0.1 && currentPosition > MIN_EXTENSION_POSITION) {  // R2 is pressed and arm is above the minimum limit
                expansionArm.setPower(-1.0);  // Move the motor backward at 100% power
            } else {
                expansionArm.setPower(0.0);  // Stop the motor if it reaches the limits or no buttons pressed
            }

            // Control the intake CRServo
            if (gamepad1.left_bumper) {  // L1 maps to left_bumper
                intakeServo.setPower(1.0);  // Rotate the CRServo in one direction
            } else if (gamepad1.left_trigger > 0.1) {  // L2 is the left trigger
                intakeServo.setPower(-1.0);  // Rotate the CRServo in the opposite direction
            } else {
                intakeServo.setPower(0.0);  // Stop the CRServo when neither L1 nor L2 is pressed
            }

            // Telemetry to monitor motor and CRServo power
            telemetry.addData("Expansion Arm Power", expansionArm.getPower());
            telemetry.addData("Expansion Arm Position", currentPosition);  // Show encoder position
            telemetry.addData("CRServo Power", intakeServo.getPower());
            telemetry.update();
        }
    }
}
