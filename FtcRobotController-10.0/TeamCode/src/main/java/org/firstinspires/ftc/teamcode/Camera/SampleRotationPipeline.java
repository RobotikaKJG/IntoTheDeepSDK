package org.firstinspires.ftc.teamcode.Camera;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SampleRotationPipeline extends OpenCvPipeline {

    private int rotationAngle = 0;

    public int getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // Adjust based on sample color
        Scalar lower = new Scalar(20, 100, 100);  // yellow
        Scalar upper = new Scalar(30, 255, 255);
        Mat mask = new Mat();
        Core.inRange(hsv, lower, upper, mask);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        RotatedRect bestRect = null;

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                RotatedRect rect = Imgproc.minAreaRect(contour2f);
                maxArea = area;
                bestRect = rect;
            }
        }

        if (bestRect != null) {
            // Get box points and choose longest edge
            Point[] points = new Point[4];
            bestRect.points(points);

            // Find longest edge
            double maxLen = 0;
            double angleDeg = 0;

            for (int i = 0; i < 4; i++) {
                Point p1 = points[i];
                Point p2 = points[(i + 1) % 4];

                double dx = p2.x - p1.x;
                double dy = p2.y - p1.y;
                double length = Math.hypot(dx, dy);

                if (length > maxLen) {
                    maxLen = length;
                    angleDeg = Math.toDegrees(Math.atan2(dy, dx));
                }
            }

            // Normalize angle to [0, 180)
            angleDeg = (angleDeg + 180) % 180;
            rotationAngle = (int) Math.round(angleDeg);

            // Draw rectangle and angle
            for (int i = 0; i < 4; i++) {
                Imgproc.line(input, points[i], points[(i + 1) % 4], new Scalar(0, 255, 0), 2);
            }

            Imgproc.putText(input, "Angle: " + rotationAngle + "°", bestRect.center,
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(255, 0, 0), 2);
        }

        return input;
    }
}
