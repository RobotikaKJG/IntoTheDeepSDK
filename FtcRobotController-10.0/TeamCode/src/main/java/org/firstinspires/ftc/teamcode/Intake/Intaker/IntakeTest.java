package org.firstinspires.ftc.teamcode.Intake.Intaker;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Expansion Arm and CRServo Control")
public class IntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Define the expansion arm motor
        DcMotor expansionArm = hardwareMap.dcMotor.get("expansionArm");

        // Define the CRServo for intake
        CRServo intakeServo = hardwareMap.crservo.get("intakeServo");

        // Set the motor to stop when no power is applied
        expansionArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            // Control the expansion arm motor with R1 for testing
            if (gamepad1.right_bumper) {  // R1 maps to right_bumper
                expansionArm.setPower(0.2);  // Move the motor forward slowly
            } else {
                expansionArm.setPower(0.0);  // Stop the motor when R1 is not pressed
            }

            // Control the intake CRServo
            if (gamepad1.left_bumper) {  // L1 maps to left_bumper
                intakeServo.setPower(1.0);  // Rotate the CRServo in one direction
            } else if (gamepad1.left_trigger > 0.1) {  // L2 is the left trigger
                intakeServo.setPower(-1.0);  // Rotate the CRServo in the opposite direction
            } else {
                intakeServo.setPower(0.0);  // Stop the CRServo when neither L1 nor L2 is pressed
            }

            // Telemetry to monitor motor and servo power
            telemetry.addData("Expansion Arm Power", expansionArm.getPower());
            telemetry.addData("CRServo Power", intakeServo.getPower());
            telemetry.update();
        }
    }
}
