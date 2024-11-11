package org.firstinspires.ftc.teamcode.Camera.Limelight;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Limelight TeleOp", group = "TeleOp")
public class LimelightTeleOp extends LinearOpMode {
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // Set polling rate to 100 Hz
        limelight.pipelineSwitch(0);  // Switch to pipeline number 0
        limelight.start();            // Start the Limelight

        telemetry.setMsTransmissionInterval(11);

        waitForStart();

        while (opModeIsActive()) {
            // Retrieve the latest result from the Limelight
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                double tx = result.getTx(); // Horizontal offset from crosshair to target (degrees)
                double ty = result.getTy(); // Vertical offset from crosshair to target (degrees)
                double ta = result.getTa(); // Target area (0% to 100% of image)

                // Display the Limelight data on the telemetry
                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);

                // Implement your robot control logic here using tx, ty, and ta
                // For example, adjust robot movement to align with the target
            } else {
                telemetry.addData("Limelight", "No Targets");
            }

            telemetry.update();
        }
    }
}
