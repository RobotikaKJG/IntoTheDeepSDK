package org.firstinspires.ftc.teamcode.OuttakeSystem.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Outtake Arm Motor Control", group = "TeleOp")
public class OuttakeArmMotor extends LinearOpMode {

    private DcMotor armMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the motor
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // Set motor mode
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Rotate clockwise if R2 is pressed
            if (gamepad1.right_trigger > 0.1) {
                armMotor.setPower(gamepad1.right_trigger);
            }
            // Rotate counter-clockwise if L2 is pressed
            else if (gamepad1.left_trigger > 0.1) {
                armMotor.setPower(-gamepad1.left_trigger);
            }
            // Stop the motor if neither trigger is pressed
            else {
                armMotor.setPower(0.0);
            }

            // Update telemetry for debugging
            telemetry.addData("Arm Motor Power", armMotor.getPower());
            telemetry.update();
        }
    }
}
