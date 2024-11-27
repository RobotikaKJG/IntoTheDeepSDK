package org.firstinspires.ftc.teamcode.IntakeSystem.ExpansionArm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Expansion Arm Test")
public class ExpansionArmTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Define the expansion arm motor
        DcMotor expansionArm = hardwareMap.dcMotor.get("expansionArm");

        // Set the motor to stop when no power is applied
        expansionArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            // Check if R1 (right bumper) is pressed to move the motor forward
            if (gamepad1.right_bumper) {  // R1 maps to right_bumper
                expansionArm.setPower(0.2);  // Move the motor forward slowly
            }
            // Check if R2 (right trigger) is pressed to move the motor backward
            else if (gamepad1.right_trigger > 0.1) {  // R2 is the right trigger (use a small threshold)
                expansionArm.setPower(-0.2);  // Move the motor backward slowly
            }
            // If neither is pressed, stop the motor
            else {
                expansionArm.setPower(0.0);
            }

            // Telemetry to monitor motor power
            telemetry.addData("Expansion Arm Power", expansionArm.getPower());
            telemetry.update();
        }
    }
}
