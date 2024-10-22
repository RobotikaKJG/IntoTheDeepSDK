package org.firstinspires.ftc.teamcode.Camera.SampleDetection;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SamplePipeline extends OpenCvPipeline {

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
