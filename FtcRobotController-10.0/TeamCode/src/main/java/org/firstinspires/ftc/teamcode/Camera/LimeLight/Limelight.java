package org.firstinspires.ftc.teamcode.Camera.LimeLight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Limelight Alignment Control to (tx=0, ty=10)", group = "TeleOp")
public class Limelight extends LinearOpMode {
    private Limelight3A limelight;
    private int targetPipeline = 1;
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    // Original tuning values
    private static final double TX_TY_THRESHOLD = 0.05; // Margin of error
    private static final double KP_TX = 0.02;           // Proportional constant for X-axis correction
    private static final double KP_TY = 0.02;           // Proportional constant for Y-axis correction

    // NEW: Additional tuning constants for velocity control
    private static final double SLOW_DOWN_ZONE = 5.0;   // When the ty error is below this, scale down motor power
    private static final double MAX_MOTOR_POWER = 0.5;    // Maximum motor power (adjust to prevent overspeed)

    // Desired target values for tx and ty
    private static final double DESIRED_TX = 0.0;
    private static final double DESIRED_TY = 15.0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // Set a polling rate suitable for your application
        limelight.pipelineSwitch(targetPipeline);  // Set to pipeline 1
        limelight.start();  // Start the Limelight

        // Initialize motors
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        // Reverse left motors if needed
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.setMsTransmissionInterval(10);
        waitForStart();

        while (opModeIsActive()) {
            // Retrieve the latest Limelight data
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx();
                double ty = result.getTy();

                // Calculate errors relative to desired values
                double txError = DESIRED_TX - tx;
                double tyError = DESIRED_TY - ty;

                // Check if errors are within the acceptable margin
                if (Math.abs(txError) <= TX_TY_THRESHOLD && Math.abs(tyError) <= TX_TY_THRESHOLD) {
                    // If aligned, stop all motors
                    frontLeftMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    frontRightMotor.setPower(0);
                    backRightMotor.setPower(0);
                    telemetry.addData("Status", "Aligned: Stopped");
                } else {
                    // Compute a scaling factor so that when the robot is close (small tyError)
                    // the correction (and thus speed) is reduced.
                    double scalingFactor = 1.0;
                    if (Math.abs(tyError) < SLOW_DOWN_ZONE) {
                        scalingFactor = Math.abs(tyError) / SLOW_DOWN_ZONE;
                    }

                    // Calculate corrections (applying the scaling factor)
                    double txCorrection = KP_TX * txError * scalingFactor;
                    // Removed the negative sign so that positive error results in forward motion
                    double tyCorrection = -KP_TY * tyError * scalingFactor;

                    /*
                     * A simple mix:
                     *   - Forward/backward (ty) correction drives the robot toward or away from the target.
                     *   - tx correction rotates or strafes the robot to reduce horizontal error.
                     * (Adjust the mixing strategy based on your drive train configuration.)
                     */
                    double frontLeftPower  = tyCorrection + txCorrection;
                    double backLeftPower   = tyCorrection - txCorrection;
                    double frontRightPower = tyCorrection - txCorrection;
                    double backRightPower  = tyCorrection + txCorrection;

                    // Cap the motor power to prevent overspeed
                    frontLeftPower  = Range.clip(frontLeftPower, -MAX_MOTOR_POWER, MAX_MOTOR_POWER);
                    backLeftPower   = Range.clip(backLeftPower, -MAX_MOTOR_POWER, MAX_MOTOR_POWER);
                    frontRightPower = Range.clip(frontRightPower, -MAX_MOTOR_POWER, MAX_MOTOR_POWER);
                    backRightPower  = Range.clip(backRightPower, -MAX_MOTOR_POWER, MAX_MOTOR_POWER);

                    // Apply the computed powers to the motors
                    frontLeftMotor.setPower(frontLeftPower);
                    backLeftMotor.setPower(backLeftPower);
                    frontRightMotor.setPower(frontRightPower);
                    backRightMotor.setPower(backRightPower);

                    telemetry.addData("Status", "Adjusting");
                    telemetry.addData("Scaling Factor", scalingFactor);
                }

                // Send diagnostic data to telemetry
                telemetry.addData("tx", tx);
                telemetry.addData("ty", ty);
                telemetry.addData("txError", txError);
                telemetry.addData("tyError", tyError);
            } else {
                telemetry.addData("Limelight", "No Targets");
                // If no valid target, stop the motors
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }

            telemetry.update();
        }
    }
}
