package org.firstinspires.ftc.teamcode.Camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

class ColorDetectionPipeline extends OpenCvPipeline {

    public enum TargetColor {
        RED,
        YELLOW,
        BLUE
    }

    private volatile TargetColor targetColor = TargetColor.BLUE;
    private volatile double objectX = -1;
    private volatile double objectY = -1;
    private final int frameWidth;

    public ColorDetectionPipeline(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public void setTargetColor(TargetColor color) {
        this.targetColor = color;
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

        // Define HSV bounds for red, yellow, and blue
        Scalar lowerBlue = new Scalar(100, 150, 50);
        Scalar upperBlue = new Scalar(140, 255, 255);

        Scalar lowerYellow = new Scalar(20, 100, 100);
        Scalar upperYellow = new Scalar(30, 255, 255);

        Scalar lowerRed1 = new Scalar(0, 120, 70);
        Scalar upperRed1 = new Scalar(10, 255, 255);
        Scalar lowerRed2 = new Scalar(170, 120, 70);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        Mat mask = new Mat();

        // Select mask based on target color
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
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        objectX = -1;
        objectY = -1;

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > 500) {
                Rect boundingRect = Imgproc.boundingRect(contour);
                double cx = boundingRect.x + boundingRect.width / 2.0;
                double cy = boundingRect.y + boundingRect.height / 2.0;

                if (area > maxArea) {
                    maxArea = area;
                    objectX = cx;
                    objectY = cy;

                    // Visual debug dot
                    Scalar drawColor = getDebugColor(targetColor);
                    Imgproc.circle(input, new Point(cx, cy), 5, drawColor, -1);
                }
            }
        }

        return input;
    }

    private Scalar getDebugColor(TargetColor color) {
        switch (color) {
            case RED:
                return new Scalar(0, 0, 255); // Red in BGR
            case YELLOW:
                return new Scalar(0, 255, 255); // Yellow
            case BLUE:
                return new Scalar(255, 0, 0); // Blue
            default:
                return new Scalar(255, 255, 255); // White fallback
        }
    }
}
