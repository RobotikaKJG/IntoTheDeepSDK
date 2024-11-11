package org.firstinspires.ftc.teamcode.Camera.SampleDetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SampleDetectionPipeline extends OpenCvPipeline {

    private String orientationDescription;

    @Override
    public Mat processFrame(Mat input) {
        // Convert to HSV color space
        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // Define the range for yellow color in HSV
        Scalar lowerYellow = new Scalar(20, 100, 100);
        Scalar upperYellow = new Scalar(30, 255, 255);

        // Create a mask for yellow color
        Mat mask = new Mat();
        Core.inRange(hsv, lowerYellow, upperYellow, mask);

        // Perform morphological operations to clean up the mask
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, kernel);

        // Find contours in the mask
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (!contours.isEmpty()) {
            // Find the largest contour
            MatOfPoint largestContour = contours.get(0);
            double maxArea = Imgproc.contourArea(largestContour);

            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    largestContour = contour;
                    maxArea = area;
                }
            }

            // Get the bounding box of the largest contour
            org.opencv.core.Rect boundingRect = Imgproc.boundingRect(largestContour);
            int w = boundingRect.width;
            int h = boundingRect.height;

            // Calculate orientation based on width and height similarity
            double similarityThreshold = 0.1;  // 10% tolerance
            if (Math.abs(w - h) / Math.max(w, h) <= similarityThreshold) {
                orientationDescription = "Square or Vertical (w=" + w + ", h=" + h + ")";
            } else {
                orientationDescription = (w < h ? "Vertical" : "Horizontal") + " (w=" + w + ", h=" + h + ")";
            }

            // Draw bounding box and orientation on the input frame
            Imgproc.rectangle(input, boundingRect, new Scalar(0, 255, 0), 2);
            Imgproc.putText(input, orientationDescription, new Point(boundingRect.x, boundingRect.y - 10),
                    Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255), 2);
        } else {
            orientationDescription = null;  // No valid object found
        }

        return input;  // Return the processed frame for display
    }

    // Get the detected orientation description
    public String getOrientationDescription() {
        return orientationDescription;
    }
}
