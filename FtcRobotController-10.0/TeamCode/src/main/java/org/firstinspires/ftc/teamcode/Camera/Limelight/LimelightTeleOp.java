package org.firstinspires.ftc.teamcode.Camera.Limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Limelight Pipeline Control", group = "TeleOp")
public class LimelightTeleOp extends LinearOpMode {
    private Limelight3A limelight;
    private int currentPipeline = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // Set a polling rate suitable for your application
        limelight.pipelineSwitch(currentPipeline);  // Start with the default pipeline
        limelight.start();  // Start the Limelight

        telemetry.setMsTransmissionInterval(10);

        waitForStart();

        while (opModeIsActive()) {
            // Check gamepad input to switch pipelines
            if (gamepad1.dpad_up) {
                currentPipeline = (currentPipeline + 1) % 10;  // Cycle forward through pipelines (0-9)
                limelight.pipelineSwitch(currentPipeline);  // Change to the new pipeline
                sleep(200);  // Small delay to avoid multiple changes on a single press
            } else if (gamepad1.dpad_down) {
                currentPipeline = (currentPipeline - 1 + 10) % 10;  // Cycle backward through pipelines
                limelight.pipelineSwitch(currentPipeline);
                sleep(200);  // Small delay for debounce
            }

            // Retrieve and display Limelight data
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx();
                double ty = result.getTy();
                double ta = result.getTa();

                telemetry.addData("Pipeline", currentPipeline);
                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);
            } else {
                telemetry.addData("Pipeline", currentPipeline);
                telemetry.addData("Limelight", "No Targets");
            }

            telemetry.update();
        }
    }
}
