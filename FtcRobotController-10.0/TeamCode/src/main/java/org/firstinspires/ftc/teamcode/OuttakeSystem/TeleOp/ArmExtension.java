package org.firstinspires.ftc.teamcode.OuttakeSystem.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Arm Extension Control", group = "TeleOp")
public class ArmExtension extends LinearOpMode {

    private DcMotor armMotor;
    private static final int MAX_ENCODER = 0;
    private static final int MIN_ENCODER = -240;
    private static final double MOTOR_POWER = 0.7;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the motor
        armMotor = hardwareMap.get(DcMotor.class, "expansionArm");

        // Set motor mode to track encoder counts
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double power = 0.0;
            int currentPosition = armMotor.getCurrentPosition();

            // Rotate clockwise (extend) if R2 is pressed and within encoder limits
            if (gamepad1.right_trigger > 0.1 && currentPosition < MAX_ENCODER) {
                power = MOTOR_POWER;
                armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            // Rotate counter-clockwise (retract) if L2 is pressed and within encoder limits
            else if (gamepad1.left_trigger > 0.1 && currentPosition > MIN_ENCODER) {
                power = -MOTOR_POWER;
                armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            } else {
                // Hold the motor's position by switching to RUN_TO_POSITION mode
                armMotor.setTargetPosition(currentPosition);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                power = 0.2; // Use a low power to hold the position
            }

            // Set motor power
            armMotor.setPower(power);

            // Update telemetry
            telemetry.addData("Arm Motor Power", power);
            telemetry.addData("Encoder Position", currentPosition);
            telemetry.addData("Target Position", armMotor.getTargetPosition());
            telemetry.update();
        }
    }
}
