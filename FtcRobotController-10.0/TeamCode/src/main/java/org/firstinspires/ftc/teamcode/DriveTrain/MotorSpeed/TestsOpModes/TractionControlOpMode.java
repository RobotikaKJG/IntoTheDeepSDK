package org.firstinspires.ftc.teamcode.DriveTrain.MotorSpeed.TestsOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Traction Control Test", group = "Linear Opmode")
public class TractionControlOpMode extends LinearOpMode {

    // Declare motor
    private DcMotor motor;

    // Configuration for the traction control system
    private static final double ACCELERATION_RATE = 0.05;  // Rate at which to increase speed (adjust this value)
    private static final long ACCELERATION_DELAY = 50;  // Time between increments in milliseconds (adjust as needed)

    @Override
    public void runOpMode() {
        // Initialize the motor
        motor = hardwareMap.get(DcMotor.class, "motor");

        // Wait for the start button to be pressed
        waitForStart();

        // Variable to store the current motor power
        double currentSpeed = 0.0;

        // Main loop to control motor speed using traction control
        while (opModeIsActive()) {
            // Get the target speed from the controller (e.g., right trigger)
            double targetSpeed = gamepad1.right_trigger;  // Values range from 0.0 to 1.0

            // Gradually adjust the motor speed towards the target speed
            currentSpeed = gradualAcceleration(currentSpeed, targetSpeed);

            // Set the motor power to the current speed
            motor.setPower(currentSpeed);

            // Telemetry feedback
            telemetry.addData("Motor Power", currentSpeed);
            telemetry.addData("Target Speed", targetSpeed);
            telemetry.update();

            sleep(20);  // Small sleep to prevent flooding the telemetry
        }
    }

    /**
     * Gradually adjusts the current speed towards the target speed.
     * @param currentSpeed The current speed of the motor.
     * @param targetSpeed The desired speed (based on controller input).
     * @return The new speed after applying gradual acceleration.
     */
    private double gradualAcceleration(double currentSpeed, double targetSpeed) {
        // If the current speed is less than the target, increase it gradually
        if (currentSpeed < targetSpeed) {
            currentSpeed += ACCELERATION_RATE;
            currentSpeed = Math.min(currentSpeed, targetSpeed);  // Ensure it does not exceed the target
        }
        // If the current speed is more than the target, decrease it gradually
        else if (currentSpeed > targetSpeed) {
            currentSpeed -= ACCELERATION_RATE;
            currentSpeed = Math.max(currentSpeed, targetSpeed);  // Ensure it does not drop below the target
        }

        // Wait for a short duration before adjusting speed again to create a smooth transition
        sleep(ACCELERATION_DELAY);

        return currentSpeed;
    }
}

