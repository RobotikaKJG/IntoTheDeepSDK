package org.firstinspires.ftc.teamcode.Camera;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.PropPos;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectionPipeline extends OpenCvPipeline {
    Mat leftSpot = new Mat();
    Mat leftSpotColor = new Mat();
    Mat frontSpot = new Mat();
    Mat frontSpotColor = new Mat();
    Mat rightSpot = new Mat();
    Mat rightSpotColor = new Mat();
    double leftSpotAvgF;
    double frontSpotAvgF;
    double rightSpotAvgF;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2YCrCb);

        getSubmats(input);

        extractColorChanel();

        calculateMaxColor(input);

        releaseMats();
        return input;
    }

    private void getSubmats(Mat input) {
        leftSpot = input.submat(CameraConstants.leftRect);
        frontSpot = input.submat(CameraConstants.frontRect);
        rightSpot = input.submat(CameraConstants.rightRect);
    }

    private void extractColorChanel() {
        int extractionChanel = setExtractionChanel();

        Core.extractChannel(leftSpot, leftSpotColor, extractionChanel);
        Core.extractChannel(frontSpot, frontSpotColor, extractionChanel);
        Core.extractChannel(rightSpot, rightSpotColor, extractionChanel);
    }

    private int setExtractionChanel() {
        return isRedAlliance() ? 1 : 2;
    }

    private void calculateMaxColor(Mat input) {
        getEachMax();
        double maxSpot = Math.max(Math.max(leftSpotAvgF, frontSpotAvgF), rightSpotAvgF);

        Scalar color = setAllianceColor();

        // set the rect with the highest color value to that color
        drawRectangles(input, maxSpot, color);

        setPropPos(maxSpot);
    }

    private void getEachMax() {
        Scalar leftSpotAvg = Core.mean(leftSpotColor);
        Scalar frontSpotAvg = Core.mean(frontSpotColor);
        Scalar rightSpotAvg = Core.mean(rightSpotColor);

        leftSpotAvgF = leftSpotAvg.val[0];
        frontSpotAvgF = frontSpotAvg.val[0];
        rightSpotAvgF = rightSpotAvg.val[0];
    }

    private Scalar setAllianceColor() {
        if (isRedAlliance())
            return CameraConstants.red;
        return CameraConstants.blue;
    }

    private void drawRectangles(Mat input, double maxSpot, Scalar color) {
        Imgproc.rectangle(input, CameraConstants.leftRect, maxSpot == leftSpotAvgF ? color : CameraConstants.white, CameraConstants.thickness);
        Imgproc.rectangle(input, CameraConstants.frontRect, maxSpot == frontSpotAvgF ? color : CameraConstants.white, CameraConstants.thickness);
        Imgproc.rectangle(input, CameraConstants.rightRect, maxSpot == rightSpotAvgF ? color : CameraConstants.white, CameraConstants.thickness);
    }

    private void setPropPos(double maxSpot) {
        if (maxSpot == leftSpotAvgF)
            GlobalVariables.propPos = PropPos.left;
        else if (maxSpot == frontSpotAvgF)
            GlobalVariables.propPos = PropPos.front;
        else
            GlobalVariables.propPos = PropPos.right;
    }

    private void releaseMats() {
        leftSpot.release();
        leftSpotColor.release();
        frontSpot.release();
        frontSpotColor.release();
        rightSpot.release();
        rightSpotColor.release();
    }

    private boolean isRedAlliance() {
        return GlobalVariables.alliance == Alliance.Red;
    }

}