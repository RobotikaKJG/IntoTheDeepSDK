package org.firstinspires.ftc.teamcode.Subsystems.Drivebase;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mecanum Drive Test", group="TeleOp")
public class DriveTrainTest extends OpMode {

    private DcMotorEx frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor;

    @Override
    public void init() {
        // Initialize motors
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");

        // Reverse left side motors so they drive correctly
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set motors to brake when power is zero
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // Gamepad control
        double y = -gamepad1.left_stick_y;  // Forward/backward
        double x = gamepad1.left_stick_x * 1.1;  // Strafe (adjusted for imperfect strafing)
        double rotation = gamepad1.right_stick_x; // Rotation

        // Apply dead zones to prevent accidental small movements
        if (Math.abs(y) < 0.05) y = 0;
        if (Math.abs(x) < 0.05) x = 0;
        if (Math.abs(rotation) < 0.05) rotation = 0;

        // Mecanum Drive Calculation
        double frontLeftPower = y + x + rotation;
        double frontRightPower = y - x - rotation;
        double backLeftPower = y - x + rotation;
        double backRightPower = y + x - rotation;

        // Normalize power values to ensure they don’t exceed 1.0
        double max = Math.max(1.0, Math.abs(frontLeftPower));
        max = Math.max(max, Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        frontLeftPower /= max;
        frontRightPower /= max;
        backLeftPower /= max;
        backRightPower /= max;

        // Set power to motors
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);

        // Telemetry for debugging
        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Back Left Power", backLeftPower);
        telemetry.addData("Back Right Power", backRightPower);
        telemetry.update();
    }
}

