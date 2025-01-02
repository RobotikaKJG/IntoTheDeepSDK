package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MotorControl", group = "TeleOp")
public class Testtt extends OpMode {

    private DcMotor backRightMotor;

    @Override
    public void init() {
        // Initialize the motor and map it to the name 'backRightMotor'
        backRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Read the vertical value (y-axis) of the right analog stick (gamepad1)
        double power = -gamepad1.right_stick_y; // Invert since up is negative on the stick

        // Set motor power
        backRightMotor.setPower(power);

        // Display motor power on telemetry for debugging
        telemetry.addData("Motor Power", power);
        telemetry.update();
    }
}
