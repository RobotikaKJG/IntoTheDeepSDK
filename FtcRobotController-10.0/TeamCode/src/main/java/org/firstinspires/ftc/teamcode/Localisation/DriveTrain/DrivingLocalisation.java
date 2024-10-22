package org.firstinspires.ftc.teamcode.Localisation.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DrivingLocalisation {

    // Robot's current position
    private double xPosition = 0.0;  // X-coordinate (left/right)
    private double zPosition = 0.0;  // Z-coordinate (forward/backward)

    // Motor positions from encoders (initial)
    private int frontLeftLastPosition = 0;
    private int frontRightLastPosition = 0;
    private int backLeftLastPosition = 0;
    private int backRightLastPosition = 0;

    // Constants for movement calculation
    private static final double TICKS_PER_REV = 1440;  // Encoder ticks per revolution (adjust based on your motor)
    private static final double WHEEL_CIRCUMFERENCE = 4 * Math.PI;  // Assuming 4-inch diameter wheels
    private static final double TICKS_PER_INCH = TICKS_PER_REV / WHEEL_CIRCUMFERENCE;

    // Timer to track time between position updates
    private ElapsedTime timer = new ElapsedTime();

    // Initialize motor references
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    // Constructor to initialize motors
    public DrivingLocalisation(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        this.frontLeftMotor = frontLeft;
        this.frontRightMotor = frontRight;
        this.backLeftMotor = backLeft;
        this.backRightMotor = backRight;

        resetEncoders();
    }

    // Reset encoder values to start fresh
    public void resetEncoders() {
        frontLeftLastPosition = frontLeftMotor.getCurrentPosition();
        frontRightLastPosition = frontRightMotor.getCurrentPosition();
        backLeftLastPosition = backLeftMotor.getCurrentPosition();
        backRightLastPosition = backRightMotor.getCurrentPosition();
        timer.reset();
    }

    // Update the robot's position based on encoder changes
    public void updatePosition() {
        int frontLeftCurrent = frontLeftMotor.getCurrentPosition();
        int frontRightCurrent = frontRightMotor.getCurrentPosition();
        int backLeftCurrent = backLeftMotor.getCurrentPosition();
        int backRightCurrent = backRightMotor.getCurrentPosition();

        // Calculate the change in position for each motor
        int frontLeftDelta = frontLeftCurrent - frontLeftLastPosition;
        int frontRightDelta = frontRightCurrent - frontRightLastPosition;
        int backLeftDelta = backLeftCurrent - backLeftLastPosition;
        int backRightDelta = backRightCurrent - backRightLastPosition;

        // Update the last known positions
        frontLeftLastPosition = frontLeftCurrent;
        frontRightLastPosition = frontRightCurrent;
        backLeftLastPosition = backLeftCurrent;
        backRightLastPosition = backRightCurrent;

        // Calculate the robot's movement (simplified 2D motion)
        double forwardMovement = (frontLeftDelta + frontRightDelta + backLeftDelta + backRightDelta) / 4.0;
        double strafeMovement = (-frontLeftDelta + frontRightDelta + backLeftDelta - backRightDelta) / 4.0;

        // Convert from ticks to inches
        forwardMovement /= TICKS_PER_INCH;
        strafeMovement /= TICKS_PER_INCH;

        // Update the robot's X and Z coordinates
        zPosition += forwardMovement;  // Forward/backward is Z-axis
        xPosition += strafeMovement;   // Left/right is X-axis
    }

    // Get the robot's current X position
    public double getXPosition() {
        return xPosition;
    }

    // Get the robot's current Z position
    public double getZPosition() {
        return zPosition;
    }

    // Reset the robot's position to (0, 0)
    public void resetPosition() {
        xPosition = 0.0;
        zPosition = 0.0;
    }
}
