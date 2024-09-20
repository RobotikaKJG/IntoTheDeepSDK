package org.firstinspires.ftc.teamcode.Camera;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.PropPos;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectionPipelineLeft extends OpenCvPipeline {
    Mat leftSpot = new Mat();
    Mat leftSpotColor = new Mat();
    Mat frontSpot = new Mat();
    Mat frontSpotColor = new Mat();
    double leftSpotAvgF;
    double frontSpotAvgF;

    private final PropPosData topPos = new PropPosData();

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
    }

    private void extractColorChanel() {
        int extractionChanel = setExtractionChanel();

        Core.extractChannel(leftSpot, leftSpotColor, extractionChanel);
        Core.extractChannel(frontSpot, frontSpotColor, extractionChanel);
    }

    private int setExtractionChanel() {
        return isRedAlliance() ? 1 : 2;
    }

    private void calculateMaxColor(Mat input) {
        getEachMax();
        double maxSpot = Math.max(leftSpotAvgF, frontSpotAvgF);

//        Scalar color = setAllianceColor();
//        drawRectangles(input, maxSpot, color);

        setPropPos(maxSpot);
    }

    private Scalar setAllianceColor() {
        if (isRedAlliance())
            return CameraConstants.red;
        return CameraConstants.blue;
    }

    private void getEachMax() {
        Scalar leftSpotAvg = Core.mean(leftSpotColor);
        Scalar frontSpotAvg = Core.mean(frontSpotColor);

        leftSpotAvgF = leftSpotAvg.val[0];
        frontSpotAvgF = frontSpotAvg.val[0];
    }

    private void setPropPos(double maxSpot) {
        if (maxSpot == leftSpotAvgF)
            topPos.setPosValues(PropPos.left, leftSpotAvgF);
        else
            topPos.setPosValues(PropPos.front, frontSpotAvgF);
    }

    private void drawRectangles(Mat input, double maxSpot, Scalar color) {
        Imgproc.rectangle(input, CameraConstants.leftRect, maxSpot == leftSpotAvgF ? color : CameraConstants.white, CameraConstants.thickness);
        Imgproc.rectangle(input, CameraConstants.frontRect, maxSpot == frontSpotAvgF ? color : CameraConstants.white, CameraConstants.thickness);
    }

    private void releaseMats() {
        leftSpot.release();
        leftSpotColor.release();
        frontSpot.release();
        frontSpotColor.release();
    }

    private boolean isRedAlliance() {
        return GlobalVariables.alliance == Alliance.Red;
    }

    public PropPosData getTopPos() {
        return topPos;
    }

}