package org.firstinspires.ftc.teamcode.OuttakeSystem.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Outtake Arm Motor Control", group = "TeleOp")
public class OuttakeArmMotor extends LinearOpMode {

    private DcMotor armMotor;
    private static final double MOTOR_POWER = 0.5; // 50% fixed power

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
            double power = 0.0;

            // Rotate clockwise at 50% power if R2 is pressed
            if (gamepad1.right_trigger > 0.1) {
                power = MOTOR_POWER;
            }
            // Rotate counter-clockwise at 50% power if L2 is pressed
            else if (gamepad1.left_trigger > 0.1) {
                power = -MOTOR_POWER;
            }

            // Set motor power
            armMotor.setPower(power);

            // Update telemetry for debugging
            telemetry.addData("Arm Motor Power", power);
            telemetry.update();
        }
    }
}
