package org.firstinspires.ftc.teamcode.Camera;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Point;
class BlueTrackingPipeline extends OpenCvPipeline {
    private volatile double objectX = -1;
    private final int frameWidth;

    public BlueTrackingPipeline(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public double getObjectX() {
        return objectX;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        Scalar lowerBlue = new Scalar(100, 150, 50);
        Scalar upperBlue = new Scalar(140, 255, 255);

        Mat mask = new Mat();
        Core.inRange(hsv, lowerBlue, upperBlue, mask);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double maxArea = 0;
        objectX = -1;  // Default: not found

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > 500) {
                Rect boundingRect = Imgproc.boundingRect(contour);
                double cy = boundingRect.y + boundingRect.width / 2.0;

                if (area > maxArea) {
                    maxArea = area;
                    objectX = cy;

                    // Draw a dot for debugging
                    Point center = new Point(cy, boundingRect.y + boundingRect.height / 2.0);
                    Imgproc.circle(input, center, 5, new Scalar(255, 0, 0), -1);
                }
            }
        }

        return input;
    }
}