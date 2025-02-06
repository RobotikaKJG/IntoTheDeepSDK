package org.firstinspires.ftc.teamcode.Camera.LimeLight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Limelight Alignment Control to (tx=0, ty=10)", group = "TeleOp")
public class Limelight extends LinearOpMode {
    private Limelight3A limelight;
    private int targetPipeline = 1;
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    // Tune these values for your specific needs
    private static final double TX_TY_THRESHOLD = 0.05; // Margin of error
    private static final double KP_TX = 0.03;           // Proportional constant for X-axis correction
    private static final double KP_TY = 0.03;           // Proportional constant for Y-axis correction

    // Desired target values for tx and ty
    private static final double DESIRED_TX = 0.0;
    private static final double DESIRED_TY = 0.0;

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

                // Calculate how far off we are from our desired values
                double txError = DESIRED_TX - tx;
                double tyError = DESIRED_TY - ty;

                // Check if the current tx, ty are within acceptable thresholds
                if (Math.abs(txError) <= TX_TY_THRESHOLD && Math.abs(tyError) <= TX_TY_THRESHOLD) {
                    // Stop motors when the target conditions are met
                    frontLeftMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    frontRightMotor.setPower(0);
                    backRightMotor.setPower(0);

                    telemetry.addData("Status", "Aligned: Stopped");
                } else {
                    // Calculate motor power corrections based on how far off we are
                    double txCorrection = KP_TX * txError;
                    double tyCorrection = -KP_TY * tyError;

                    /*
                     * Here is one simple approach to mix forward/backward and rotation:
                     *   - We want to drive forward/backward to fix ty (vertical offset).
                     *   - We want to rotate or strafe to fix tx (horizontal offset).
                     * Adjust the sign or mixing strategy as needed for your drive train.
                     */
                    double frontLeftPower  = tyCorrection + txCorrection;
                    double backLeftPower   = tyCorrection - txCorrection;
                    double frontRightPower = tyCorrection - txCorrection;
                    double backRightPower  = tyCorrection + txCorrection;

                    // Apply these powers to the motors
                    frontLeftMotor.setPower(frontLeftPower);
                    backLeftMotor.setPower(backLeftPower);
                    frontRightMotor.setPower(frontRightPower);
                    backRightMotor.setPower(backRightPower);

                    telemetry.addData("Status", "Adjusting");
                }

                telemetry.addData("tx", tx);
                telemetry.addData("ty", ty);
                telemetry.addData("txError", txError);
                telemetry.addData("tyError", tyError);
            } else {
                telemetry.addData("Limelight", "No Targets");
                // Optionally stop or do something else if no valid target
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }

            telemetry.update();
        }
    }
}
