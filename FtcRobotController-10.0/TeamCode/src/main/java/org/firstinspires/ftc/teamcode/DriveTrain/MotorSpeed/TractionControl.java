package org.firstinspires.ftc.teamcode.DriveTrain.MotorSpeed;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TractionControl {

    // Configuration for the traction control system
    private static final double ACCELERATION_RATE = 0.05;  // Rate at which to increase speed
    private static final long ACCELERATION_DELAY = 50;  // Time between increments in milliseconds

    /**
     * Gradually adjusts the current speed towards the target speed.
     * @param currentSpeed The current speed of the motor.
     * @param targetSpeed The desired speed (based on controller input).
     * @param opMode The LinearOpMode instance, used for sleep().
     * @return The new speed after applying gradual acceleration.
     */
    public double gradualAcceleration(double currentSpeed, double targetSpeed, LinearOpMode opMode) {
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

        // Use the LinearOpMode's sleep method
        opMode.sleep(ACCELERATION_DELAY);

        return currentSpeed;
    }
}
