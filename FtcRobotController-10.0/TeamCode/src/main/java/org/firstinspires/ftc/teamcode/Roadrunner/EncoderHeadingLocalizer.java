package org.firstinspires.ftc.teamcode.Roadrunner;

import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;

public class EncoderHeadingLocalizer {
    //private final DcMotorEx leftEncoder, rightEncoder;
    private double leftYTicks;
    private double rightYTicks;

    private int lastLeftPos = 0;
    private int lastRightPos = 0;
    private double currentHeading = 0;

    public EncoderHeadingLocalizer(MotorControl motorControl) {
        // Initialize encoders
        //leftEncoder = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        //rightEncoder = hardwareMap.get(DcMotorEx.class, "frontRightMotor");

        // Set directions (adjust based on your robot's configuration)
        //leftEncoder.setDirection(DcMotorSimple.Direction.REVERSE); // Adjust if necessary
        //rightEncoder.setDirection(DcMotorSimple.Direction.FORWARD); // Adjust if necessary

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
        int leftPos = 0;//leftEncoder.getCurrentPosition();
        int rightPos = 0;//rightEncoder.getCurrentPosition();

        // Calculate deltas
        int leftDelta = leftPos - lastLeftPos;
        int rightDelta = rightPos - lastRightPos;

        // Update last positions
        lastLeftPos = leftPos;
        lastRightPos = rightPos;

        currentHeading += (leftDelta - rightDelta) / (leftYTicks - rightYTicks);
        return currentHeading;// - angleModifier;
    }

    public void resetEncoders(){
        resetEncoders(0);
    }
    public void resetEncoders(double targetAngle){
        currentHeading = targetAngle;
    }
}
