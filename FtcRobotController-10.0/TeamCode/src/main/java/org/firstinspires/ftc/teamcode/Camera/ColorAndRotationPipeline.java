package org.firstinspires.ftc.teamcode.Camera;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ColorAndRotationPipeline extends OpenCvPipeline {

    public enum TargetColor {
        RED,
        YELLOW,
        BLUE
    }

    private volatile TargetColor targetColor = TargetColor.YELLOW;
    private volatile int rotationAngle = 0;
    private volatile double objectX = -1;
    private volatile double objectY = -1;

    public void setTargetColor(TargetColor color) {
        this.targetColor = color;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public double getObjectX() {
        return objectX;
    }

    public double getObjectY() {
        return objectY;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // Define HSV bounds
        Scalar lowerBlue = new Scalar(100, 150, 50);
        Scalar upperBlue = new Scalar(140, 255, 255);
        Scalar lowerYellow = new Scalar(20, 100, 100);
        Scalar upperYellow = new Scalar(30, 255, 255);
        Scalar lowerRed1 = new Scalar(0, 120, 70);
        Scalar upperRed1 = new Scalar(10, 255, 255);
        Scalar lowerRed2 = new Scalar(170, 120, 70);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        Mat mask = new Mat();

        // Apply selected color mask
        switch (targetColor) {
            case BLUE:
                Core.inRange(hsv, lowerBlue, upperBlue, mask);
                break;
            case YELLOW:
                Core.inRange(hsv, lowerYellow, upperYellow, mask);
                break;
            case RED:
                Mat mask1 = new Mat();
                Mat mask2 = new Mat();
                Core.inRange(hsv, lowerRed1, upperRed1, mask1);
                Core.inRange(hsv, lowerRed2, upperRed2, mask2);
                Core.addWeighted(mask1, 1.0, mask2, 1.0, 0.0, mask);
                break;
        }

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        RotatedRect bestRect = null;
        objectX = -1;
        objectY = -1;

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > 500) {
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                RotatedRect rect = Imgproc.minAreaRect(contour2f);

                if (area > maxArea) {
                    maxArea = area;
                    bestRect = rect;
                }
            }
        }

        if (bestRect != null) {
            // Draw rectangle and get center
            Point[] points = new Point[4];
            bestRect.points(points);

            for (int i = 0; i < 4; i++) {
                Imgproc.line(input, points[i], points[(i + 1) % 4], new Scalar(0, 255, 0), 2);
            }

            objectX = bestRect.center.x;
            objectY = bestRect.center.y;

            // Draw center
            Imgproc.circle(input, bestRect.center, 5, getDebugColor(targetColor), -1);

            // Calculate rotation angle
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

            angleDeg = (angleDeg + 180) % 180;
            rotationAngle = (int) Math.round(angleDeg);

            // Display angle
            Imgproc.putText(input, "Angle: " + rotationAngle + "°",
                    new Point(bestRect.center.x + 10, bestRect.center.y),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.6, new Scalar(255, 0, 0), 2);
        }

        return input;
    }

    private Scalar getDebugColor(TargetColor color) {
        switch (color) {
            case RED:
                return new Scalar(0, 0, 255);
            case YELLOW:
                return new Scalar(0, 255, 255);
            case BLUE:
                return new Scalar(255, 0, 0);
            default:
                return new Scalar(255, 255, 255);
        }
    }
}
