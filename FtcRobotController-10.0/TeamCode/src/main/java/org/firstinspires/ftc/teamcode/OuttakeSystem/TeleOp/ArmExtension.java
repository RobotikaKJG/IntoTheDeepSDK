package org.firstinspires.ftc.teamcode.OuttakeSystem.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Arm Extension Control", group = "TeleOp")
public class ArmExtension extends LinearOpMode {

    private DcMotor armMotor;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the motor
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // Set motor mode to track encoder counts
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double power = 0.0;

            // Rotate clockwise at 50% power if R2 is pressed
            if (gamepad1.right_trigger > 0.1) {
                power = 0.5;
            }
            // Rotate counter-clockwise at 50% power if L2 is pressed
            else if (gamepad1.left_trigger > 0.1) {
                power = -0.5;
            }

            // Set the motor power
            armMotor.setPower(power);

            // Update telemetry with motor power and encoder position
            telemetry.addData("Arm Motor Power", power);
            telemetry.addData("Encoder Position", armMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
