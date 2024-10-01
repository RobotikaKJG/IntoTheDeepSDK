package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.MatOfPoint;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Point;
//import org.opencv.core.Contour;

@TeleOp(name="AprilTag Detection with X Z Coordinates", group="Linear Opmode")
public class AprilTagDetectionOpMode extends LinearOpMode {

    OpenCvWebcam webcam;
    static final double FOCAL_LENGTH_MM = 4.0;
    static final int CAMERA_WIDTH = 640;
    static final int CAMERA_HEIGHT = 480;
    static final double TAG_SIZE_INCHES = 4;  // AprilTag size in inches
    static final double OBJECT_REAL_WIDTH_INCHES = TAG_SIZE_INCHES;
    static final double FOCAL_LENGTH_PIXELS = (FOCAL_LENGTH_MM / 3.6) * CAMERA_WIDTH;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Set the pipeline
        webcam.setPipeline(new AprilTagPipeline());

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera failed to open", errorCode);
            }
        });

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
            sleep(100);  // Update telemetry at regular intervals
        }
    }

    class AprilTagPipeline extends OpenCvPipeline {

        @Override
        public Mat processFrame(Mat input) {
            // Convert the frame to grayscale
            Mat gray = new Mat();
            Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);

            // Apply Gaussian blur
            Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

            // Apply adaptive thresholding
            Mat thresholded = new Mat();
            Imgproc.adaptiveThreshold(gray, thresholded, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                    Imgproc.THRESH_BINARY_INV, 11, 2);

            // Find contours
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(thresholded, contours, hierarchy, Imgproc.RETR_EXTERNAL,
                    Imgproc.CHAIN_APPROX_SIMPLE);

            // Process the largest contour, assuming it's the AprilTag
            double maxArea = 0;
            MatOfPoint largestContour = null;

            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    maxArea = area;
                    largestContour = contour;
                }
            }

            if (largestContour != null) {
                // Get bounding box
                Rect boundingBox = Imgproc.boundingRect(largestContour);
                int x = boundingBox.x;
                int y = boundingBox.y;
                int w = boundingBox.width;
                int h = boundingBox.height;

                // Draw bounding box and center point
                Imgproc.rectangle(input, new Point(x, y), new Point(x + w, y + h), new Scalar(0, 255, 0), 2);
                int centroidX = x + w / 2;
                int centroidY = y + h / 2;
                Imgproc.circle(input, new Point(centroidX, centroidY), 5, new Scalar(0, 0, 255), -1);

                // Calculate X (distance in inches)
                double distanceInInches = (OBJECT_REAL_WIDTH_INCHES * FOCAL_LENGTH_PIXELS) / w;

                // Calculate Z (horizontal offset in inches)
                double cameraCenterX = CAMERA_WIDTH / 2.0;
                double horizontalOffsetInInches = ((centroidX - cameraCenterX) * OBJECT_REAL_WIDTH_INCHES) / w;

                // Display results using telemetry
                telemetry.addData("X (Distance):", "%.2f inches", distanceInInches);
                telemetry.addData("Z (Offset):", "%.2f inches", horizontalOffsetInInches);
            }

            return input;  // Return the processed frame to the display
        }
    }
}
