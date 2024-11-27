package org.firstinspires.ftc.teamcode.DriveTrain.MotorSpeed;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TractionControl {

    // Default acceleration rate, adjustable with D-pad
    private double accelerationRate = 0.05;

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
            currentSpeed += accelerationRate;
            currentSpeed = Math.min(currentSpeed, targetSpeed);  // Ensure it does not exceed the target
        }
        // Gradually decrease the speed towards the target
        else if (currentSpeed > targetSpeed) {
            currentSpeed -= accelerationRate;
            currentSpeed = Math.max(currentSpeed, targetSpeed);  // Ensure it does not drop below the target
        }

        // Use the LinearOpMode's sleep method to introduce the delay
        opMode.sleep(5);  // Delay between increments

        return currentSpeed;
    }

    /**
     * Adjust the acceleration rate.
     * @param increase True to increase, False to decrease.
     */
    public void adjustAccelerationRate(boolean increase) {
        if (increase) {
            accelerationRate = Math.min(accelerationRate + 0.01, 0.1);  // Increase up to 0.1
        } else {
            accelerationRate = Math.max(accelerationRate - 0.01, 0.01);  // Decrease down to 0.01
        }
    }

    public double getAccelerationRate() {
        return accelerationRate;
    }
}
