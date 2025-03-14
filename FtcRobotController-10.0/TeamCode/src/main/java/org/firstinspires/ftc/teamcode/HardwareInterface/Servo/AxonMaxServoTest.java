package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "AxonMaxServoControl")
public class AxonMaxServoTest extends LinearOpMode {

    private Servo axonServo;
    private double currentAngle = 255.0;  // Initial angle (Start at 30°)
    private final double MAX_ANGLE = 0.0;  // Maximum servo angle
    private final double MIN_ANGLE = 255.0;    // Minimum servo angle
    private final double angle = 30.0;

    @Override
    public void runOpMode() {
        axonServo = hardwareMap.get(Servo.class, "outtakeRightServo"); // Ensure correct servo name

        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Increase angle when D-pad up is pressed
            if (gamepad1.dpad_up && currentAngle - angle >= MAX_ANGLE) {
                currentAngle -= angle;
                telemetry.addData("Increasing Angle", currentAngle);
                telemetry.update();
            }

            // Decrease angle when D-pad down is pressed
            if (gamepad1.dpad_down && currentAngle + angle <= MIN_ANGLE) {
                currentAngle += angle;
                telemetry.addData("Decreasing Angle", currentAngle);
                telemetry.update();
            }

            // Convert the angle to servo position (0.0 to 1.0)
            double targetPosition = currentAngle / MIN_ANGLE;
            axonServo.setPosition(targetPosition);

            telemetry.addData("Servo Position", targetPosition);
            telemetry.addData("Current Angle", currentAngle);
            telemetry.update();

            sleep(200);  // Small delay to avoid button spamming
        }
    }
}
