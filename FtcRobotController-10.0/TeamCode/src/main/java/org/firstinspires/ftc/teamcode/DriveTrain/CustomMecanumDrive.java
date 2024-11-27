package org.firstinspires.ftc.teamcode.DriveTrain;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;


public class CustomMecanumDrive extends MecanumDrive {
    private final List<DcMotorEx> motors;

    public CustomMecanumDrive(HardwareMap hardwareMap) {
        // Update the constructor to match the MecanumDrive base class
        super(hardwareMap, new Pose2d(0, 0, 0)); // Replace this with the actual constructor call for MecanumDrive

        // Initialize motors
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        motors = Arrays.asList(frontLeft, frontRight, backLeft, backRight);

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }

        // Reverse directions if needed
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    }

    @Override
    public void setDrivePower(Pose2d drivePower) {
        // Access Pose2d attributes using getters
        double frontLeftPower = drivePower.getX() + drivePower.getY() + drivePower.getHeading();
        double frontRightPower = drivePower.getX() - drivePower.getY() - drivePower.getHeading();
        double backLeftPower = drivePower.getX() - drivePower.getY() + drivePower.getHeading();
        double backRightPower = drivePower.getX() + drivePower.getY() - drivePower.getHeading();

        // Normalize motor powers
        double maxPower = Math.max(1.0, Math.max(
                Math.abs(frontLeftPower),
                Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)))));

        frontLeftPower /= maxPower;
        frontRightPower /= maxPower;
        backLeftPower /= maxPower;
        backRightPower /= maxPower;

        // Set motor powers
        motors.get(0).setPower(frontLeftPower);
        motors.get(1).setPower(frontRightPower);
        motors.get(2).setPower(backLeftPower);
        motors.get(3).setPower(backRightPower);
    }

    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                (double) motors.get(0).getCurrentPosition(),
                (double) motors.get(1).getCurrentPosition(),
                (double) motors.get(2).getCurrentPosition(),
                (double) motors.get(3).getCurrentPosition()
        );
    }
}
