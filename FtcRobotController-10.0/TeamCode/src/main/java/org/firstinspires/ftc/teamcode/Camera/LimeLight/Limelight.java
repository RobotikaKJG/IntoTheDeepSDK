package org.firstinspires.ftc.teamcode.Camera.LimeLight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Limelight Alignment Control", group = "TeleOp")
public class Limelight extends LinearOpMode {
    private Limelight3A limelight;
    private int targetPipeline = 1;
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    private static final double TX_TY_THRESHOLD = 0.05; // Margin of error
    private static final double KP_TX = 0.02; // Proportional constant for X-axis correction
    private static final double KP_TY = 0.02; // Proportional constant for Y-axis correction

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

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.setMsTransmissionInterval(10);

        waitForStart();

        while (opModeIsActive()) {
            // Retrieve Limelight data
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx();
                double ty = result.getTy();

                // Calculate corrections for tx and ty
                double txCorrection = -KP_TX * tx; // Negative to reduce tx towards 0
                double tyCorrection = KP_TY * ty;  // Positive to bring ty to 0

                // Check if tx and ty are within the threshold
                if (Math.abs(tx) > TX_TY_THRESHOLD || Math.abs(ty) > TX_TY_THRESHOLD) {
                    // Apply motor powers based on corrections
                    double frontLeftPower = tyCorrection + txCorrection;
                    double backLeftPower = tyCorrection - txCorrection;
                    double frontRightPower = tyCorrection - txCorrection;
                    double backRightPower = tyCorrection + txCorrection;

                    frontLeftMotor.setPower(frontLeftPower);
                    backLeftMotor.setPower(backLeftPower);
                    frontRightMotor.setPower(frontRightPower);
                    backRightMotor.setPower(backRightPower);
                } else {
                    // Stop motors when target is aligned
                    frontLeftMotor.setPower(0);
                    backLeftMotor.setPower(0);
                    frontRightMotor.setPower(0);
                    backRightMotor.setPower(0);
                }

                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("TX Correction", txCorrection);
                telemetry.addData("TY Correction", tyCorrection);
            } else {
                telemetry.addData("Limelight", "No Targets");
                // Stop motors if no valid target
                frontLeftMotor.setPower(0);
                backLeftMotor.setPower(0);
                frontRightMotor.setPower(0);
                backRightMotor.setPower(0);
            }

            telemetry.update();
        }
    }
}
