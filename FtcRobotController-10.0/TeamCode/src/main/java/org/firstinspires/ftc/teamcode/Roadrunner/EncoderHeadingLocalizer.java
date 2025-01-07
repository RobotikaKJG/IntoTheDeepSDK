package org.firstinspires.ftc.teamcode.Roadrunner;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class EncoderHeadingLocalizer {
    private final DcMotorEx leftEncoder, rightEncoder;
    private double leftYTicks;
    private double rightYTicks;

    private int lastLeftPos = 0;
    private int lastRightPos = 0;

    public EncoderHeadingLocalizer(HardwareMap hardwareMap) {
        // Initialize encoders
        leftEncoder = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        rightEncoder = hardwareMap.get(DcMotorEx.class, "frontRightMotor");

        // Set directions (adjust based on your robot's configuration)
        leftEncoder.setDirection(DcMotorSimple.Direction.REVERSE); // Adjust if necessary
        rightEncoder.setDirection(DcMotorSimple.Direction.FORWARD); // Adjust if necessary

        // Initialize default parameters (you can adjust based on your robot design)
        leftYTicks = 0.0;
        rightYTicks = 1.0;
    }

    public void setYTicks(double leftYTicks, double rightYTicks) {
        this.leftYTicks = leftYTicks;
        this.rightYTicks = rightYTicks;
    }

    public double getHeading() {
        // Read current positions
        int leftPos = leftEncoder.getCurrentPosition();
        int rightPos = rightEncoder.getCurrentPosition();

        // Calculate deltas
        int leftDelta = leftPos - lastLeftPos;
        int rightDelta = rightPos - lastRightPos;

        // Update last positions
        lastLeftPos = leftPos;
        lastRightPos = rightPos;

        // Calculate heading (difference between left and right deltas normalized by ticks)
        return (leftDelta - rightDelta) / (leftYTicks - rightYTicks);
    }

    public void resetEncoders(){
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
