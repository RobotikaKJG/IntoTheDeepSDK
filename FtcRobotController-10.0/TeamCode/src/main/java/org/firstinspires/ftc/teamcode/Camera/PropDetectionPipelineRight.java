package org.firstinspires.ftc.teamcode.Camera;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.PropPos;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectionPipelineRight extends OpenCvPipeline {
    Mat rightSpot = new Mat();
    Mat rightSpotColor = new Mat();
    double rightSpotAvgF;

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
        rightSpot = input.submat(CameraConstants.rightRect);
    }

    private void extractColorChanel() {
        int extractionChanel = setExtractionChanel();

        Core.extractChannel(rightSpot, rightSpotColor, extractionChanel);
    }

    private int setExtractionChanel() {
        return isRedAlliance() ? 1 : 2;
    }

    private void calculateMaxColor(Mat input) {
        getEachMax();

        Scalar color = setAllianceColor();
        drawRectangles(input);

        topPos.setPosValues(PropPos.right, rightSpotAvgF);
    }

    private Scalar setAllianceColor() {
        if (isRedAlliance())
            return CameraConstants.red;
        return CameraConstants.blue;
    }

    private void drawRectangles(Mat input) {
        Imgproc.rectangle(input, CameraConstants.rightRect, CameraConstants.white, CameraConstants.thickness);
    }

        private void getEachMax() {
        Scalar rightSpotAvg = Core.mean(rightSpotColor);

        rightSpotAvgF = rightSpotAvg.val[0];
    }

    private void setPropPos(double maxSpot) {
        if (maxSpot == rightSpotAvgF)
            topPos.setPosValues(PropPos.left, rightSpotAvgF);
    }

    private void releaseMats() {
        rightSpot.release();
        rightSpotColor.release();
    }

    private boolean isRedAlliance() {
        return GlobalVariables.alliance == Alliance.Red;
    }

    public PropPosData getTopPos() {
        return topPos;
    }

}