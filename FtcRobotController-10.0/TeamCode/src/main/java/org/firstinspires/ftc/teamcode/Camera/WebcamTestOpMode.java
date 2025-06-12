package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.OpenCvPipeline;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
@TeleOp(name="Webcam Test Proportional", group="Camera")
public class WebcamTestOpMode extends LinearOpMode {

    OpenCvWebcam webcam;

    @Override
    public void runOpMode() {

        // Define the motors
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        int frameWidth = 320;
        BlueTrackingPipeline pipeline = new BlueTrackingPipeline(frameWidth);
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(frameWidth, 240);
            }

            @Override
            public void onError(int errorCode) {}
        });

        waitForStart();

        double centerX = frameWidth / 2.0;
        double tolerance = 10; // How close is "centered"
        double kP = 0.002; // Tuning value for proportional scaling

        while (opModeIsActive()) {
            double x = pipeline.getObjectX();

            if (x != -1) {
                double error = x - centerX;

                if (Math.abs(error) < tolerance) {
                    telemetry.addLine("Centered — Stopping");
                    stopDrive(frontLeft, frontRight, backLeft, backRight);
                } else {
                    // Proportional control
                    double power = kP * Math.abs(error);
                    power = Math.min(0.40, Math.max(0.15, power)); // Clamp between 0.15 and 0.5

                    if (error < 0) {
                        telemetry.addData("Blue Position", "Left → Strafing Left");
                        strafeLeft(frontLeft, frontRight, backLeft, backRight, power);
                    } else {
                        telemetry.addData("Blue Position", "Right → Strafing Right");
                        strafeRight(frontLeft, frontRight, backLeft, backRight, power);
                    }

                    telemetry.addData("Power", power);
                }
            } else {
                telemetry.addLine("No Blue Detected — Stopping");
                stopDrive(frontLeft, frontRight, backLeft, backRight);
            }

            telemetry.addData("Object X", x);
            telemetry.addData("Center X", centerX);
            telemetry.update();

            sleep(50);
        }
    }

    void strafeLeft(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br, double power) {
        fl.setPower(-power);
        fr.setPower(power);
        bl.setPower(power);
        br.setPower(-power);
    }

    void strafeRight(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br, double power) {
        fl.setPower(power);
        fr.setPower(-power);
        bl.setPower(-power);
        br.setPower(power);
    }

    void stopDrive(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }
}
