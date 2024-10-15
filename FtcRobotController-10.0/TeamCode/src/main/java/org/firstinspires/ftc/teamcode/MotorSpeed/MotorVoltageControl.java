package org.firstinspires.ftc.teamcode.MotorSpeed;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Motor Voltage Speed Control", group = "Linear Opmode")
public class MotorVoltageControl extends LinearOpMode {

    // Declare motor
    private DcMotor motor;

    @Override
    public void runOpMode() {
        // Initialize the motor
        motor = hardwareMap.get(DcMotor.class, "motor");

        // Wait for the start button to be pressed
        waitForStart();

        // Main loop to control motor speed by "voltage" approximation
        while (opModeIsActive()) {
            // Example: Control motor speed based on gamepad input
            double voltage = gamepad1.left_stick_y;  // Simulating "voltage" with gamepad input

            // Convert voltage (-1.0 to 1.0) to motor power (-1.0 to 1.0)
            double motorPower = voltage;

            // Set motor power
            motor.setPower(motorPower);

            // Telemetry feedback
            telemetry.addData("Motor Power", motorPower);
            telemetry.update();
        }
    }
}
