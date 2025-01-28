package org.firstinspires.ftc.teamcode.Camera.LimeLight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Limelight Scaled Alignment", group = "TeleOp")
public class Limelight extends LinearOpMode {
    private Limelight3A limelight;
    private int targetPipeline = 1;
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    private static final double TX_TY_THRESHOLD = 0.05; // Margin of error
    private static final double KP_TX = 0.02; // Proportional constant for horizontal correction
    private static final double KP_TY = 0.02; // Proportional constant for vertical correction
    private static final double MIN_POWER = 0.1; // Minimum power to prevent stalling

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

                // Proportional corrections
                double txCorrection = KP_TX * ty;
                double tyCorrection = KP_TY * tx;

                // Apply scaling to corrections
                double scaledTxCorrection = txCorrection * Math.abs(ty);
                double scaledTyCorrection = tyCorrection * Math.abs(tx);

                // Ensure a minimum power threshold
                scaledTxCorrection = Math.copySign(Math.max(Math.abs(scaledTxCorrection), MIN_POWER), scaledTxCorrection);
                scaledTyCorrection = Math.copySign(Math.max(Math.abs(scaledTyCorrection), MIN_POWER), scaledTyCorrection);

                // Check if tx and ty are within the threshold
                if (Math.abs(tx) > TX_TY_THRESHOLD || Math.abs(ty) > TX_TY_THRESHOLD) {
                    // Apply motor powers based on scaled corrections
                    double frontLeftPower = scaledTyCorrection + scaledTxCorrection;
                    double backLeftPower = scaledTyCorrection - scaledTxCorrection;
                    double frontRightPower = scaledTyCorrection - scaledTxCorrection;
                    double backRightPower = scaledTyCorrection + scaledTxCorrection;

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

                telemetry.addData("Target X (tx)", tx);
                telemetry.addData("Target Y (ty)", ty);
                telemetry.addData("Scaled TX Correction", scaledTxCorrection);
                telemetry.addData("Scaled TY Correction", scaledTyCorrection);
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
