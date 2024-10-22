package org.firstinspires.ftc.teamcode.Camera.SampleDetection;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.List;

public class SampleObjectDetection extends OpMode {

    OpenCvCamera camera;
    SamplePipeline pipeline;

    @Override
    public void init() {
        // Initialize camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Set the pipeline
        pipeline = new SamplePipeline();
        camera.setPipeline(pipeline);

        // Open the camera
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
                telemetry.update();
            }
        });
    }

    @Override
    public void loop() {
        // Output the detected corners to telemetry
        if (pipeline.getCorners() != null) {
            for (int i = 0; i < pipeline.getCorners().length; i++) {
                telemetry.addData("Corner " + i, pipeline.getCorners()[i]);
            }
        } else {
            telemetry.addData("No corners detected", "");
        }
        telemetry.update();
    }

    // Pipeline class for processing camera frames
    class SamplePipeline extends OpenCvPipeline {

        private Point[] corners;

        @Override
        public Mat processFrame(Mat input) {
            Mat gray = new Mat();
            Mat blurred = new Mat();
            Mat thresholded = new Mat();

            // Convert to grayscale
            Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);

            // Blur the image to reduce noise
            Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0);

            // Apply a threshold to isolate the black object
            Imgproc.threshold(blurred, thresholded, 30, 255, Imgproc.THRESH_BINARY_INV);

            // Find contours
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(thresholded, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            // If contours are found, process the largest one
            if (!contours.isEmpty()) {
                MatOfPoint largestContour = contours.get(0);
                double maxArea = Imgproc.contourArea(largestContour);

                // Iterate over all contours to find the largest
                for (MatOfPoint contour : contours) {
                    double area = Imgproc.contourArea(contour);
                    if (area > maxArea) {
                        largestContour = contour;
                        maxArea = area;
                    }
                }

                // Approximate the contour to find the corners (assuming the object is rectangular)
                MatOfPoint2f contour2f = new MatOfPoint2f(largestContour.toArray());
                MatOfPoint2f approx = new MatOfPoint2f();
                Imgproc.approxPolyDP(contour2f, approx, 0.02 * Imgproc.arcLength(contour2f, true), true);

                // If the contour has four points, it's likely the rectangle
                if (approx.total() == 4) {
                    corners = approx.toArray();
                } else {
                    corners = null;  // No valid rectangular object found
                }
            }

            // Return the processed image
            return input;
        }

        // Get the detected corners
        public Point[] getCorners() {
            return corners;
        }
    }
}
