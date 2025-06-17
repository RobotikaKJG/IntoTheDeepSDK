package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class SampleRotationMotor extends LinearOpMode {

    OpenCvWebcam webcam;
    SampleRotationPipeline pipeline;

    DcMotor rotateMotor;

    double lastAngle = -1;
    double cumulativeAngle = 0;

    final double kSmooth = 0.008;   // Proportional gain for motor power
    final double maxPower = 0.3;   // Maximum motor power
    final double deadbandTicks = 5; // Encoder tick deadband

    @Override
    public void runOpMode() throws InterruptedException {
        // Motor setup
        rotateMotor = hardwareMap.dcMotor.get("rotateMotor");
        rotateMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rotateMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotateMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotateMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Camera setup
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                cameraMonitorViewId
        );

        pipeline = new SampleRotationPipeline();
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
                telemetry.update();
            }
        });

        waitForStart();

        while (opModeIsActive()) {
            int detectedAngle = pipeline.getRotationAngle();

            if (detectedAngle < 0 || detectedAngle > 180) {
                telemetry.addData("Warning", "Invalid angle detected");
                telemetry.update();
                continue;
            }

            // Unwrap and accumulate angle change
            if (lastAngle != -1) {
                double delta = lastAngle - detectedAngle;

                cumulativeAngle += delta;
            }

            lastAngle = detectedAngle;

            // Convert cumulative angle to motor position (encoder ticks)
            double targetAngle = cumulativeAngle;
            double motorTargetPos = targetAngle * (380 / 360.0); // 537.6 ticks per rev

            double currentMotorPos = rotateMotor.getCurrentPosition();
            double error = motorTargetPos - currentMotorPos;

            // Calculate motor power using proportional control
            double power = kSmooth * error;

            // Apply deadband
            if (Math.abs(error) < deadbandTicks) {
                power = 0;
            }

            // Clamp power
            power = Math.max(-maxPower, Math.min(maxPower, power));

            rotateMotor.setPower(power);

            // Telemetry
            telemetry.addData("Detected Angle", detectedAngle);
            telemetry.addData("Cumulative Angle", (int) cumulativeAngle);
            telemetry.addData("Motor Target Pos", (int) motorTargetPos);
            telemetry.addData("Current Motor Pos", (int) currentMotorPos);
            telemetry.addData("Motor Power", power);
            telemetry.update();
        }
    }
}
