package org.firstinspires.ftc.teamcode.DriveTrain.MotorSpeed;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TractionControl {

    // Configuration for the traction control system
    private static final double ACCELERATION_RATE = 0.05;  // Rate at which to increase speed
    private static final long ACCELERATION_DELAY = 5;  // Time between increments in milliseconds

    /**
     * Gradually adjusts the current speed towards the target speed.
     * Skips gradual deceleration if the target speed is zero.
     *
     * @param currentSpeed The current speed of the motor.
     * @param targetSpeed The desired speed (based on controller input).
     * @param opMode The LinearOpMode instance, used for sleep().
     * @return The new speed after applying gradual acceleration.
     */
    public double gradualAcceleration(double currentSpeed, double targetSpeed, LinearOpMode opMode) {
        // Skip gradual deceleration if target speed is 0 (analog stick released)
        if (targetSpeed == 0) {
            return 0.0;
        }

        // Gradually increase the speed towards the target
        if (currentSpeed < targetSpeed) {
            currentSpeed += ACCELERATION_RATE;
            currentSpeed = Math.min(currentSpeed, targetSpeed);  // Ensure it does not exceed the target
        }
        // Gradually decrease the speed towards the target
        else if (currentSpeed > targetSpeed) {
            currentSpeed -= ACCELERATION_RATE;
            currentSpeed = Math.max(currentSpeed, targetSpeed);  // Ensure it does not drop below the target
        }

        // Use the LinearOpMode's sleep method to introduce the delay
        opMode.sleep(ACCELERATION_DELAY);

        return currentSpeed;
    }
}
