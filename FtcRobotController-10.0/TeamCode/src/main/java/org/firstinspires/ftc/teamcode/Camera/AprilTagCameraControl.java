/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.Camera;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;


public class AprilTagCameraControl implements CameraControl {
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private OpenCvWebcam webcam;
    private AprilTagDetectionPipeline aprilTagDetectionPipeline;
    private ArrayList<AprilTagDetection> detections;

    private static final double FEET_PER_METER = 3.28084;

    private int numFramesWithoutDetection = 0;

    public AprilTagCameraControl(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void startCamera() {
        if(GlobalVariables.alliance == Alliance.Red)
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));//, viewportContainerIds[CameraConstants.aprilTagCamera]);
        else
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 2"));//, viewportContainerIds[CameraConstants.aprilTagCamera]);
        // Lens intrinsics
        // UNITS ARE PIXELS
        // official calibration data for C270 webcam at 640x480
        double fx = 822.317;
        double fy = 822.317;
        double cx = 319.495;
        double cy = 242.502;
        // UNITS ARE METERS
        double tagSize = 0.05;
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagSize, fx, fy, cx, cy);

        webcam.setPipeline(aprilTagDetectionPipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    @Override
    public void pauseCamera(){
        webcam.stopStreaming();
    }

    @Override
    public void stopCamera() {
        webcam.stopStreaming();
        webcam.closeCameraDevice();
    }


    @SuppressLint("DefaultLocale")
    public void detectAprilTag() {
        // Calling getDetectionsUpdate() will only return an object if there was a new frame
        // processed since the last time we called it. Otherwise, it will return null. This
        // enables us to only run logic when there has been a new frame, as opposed to the
        // getLatestDetections() method which will always return an object.
        detections = aprilTagDetectionPipeline.getDetectionsUpdate();

        // If there's been a new frame...
        if (detections != null) {


            // If we don't see any tags
            if (detections.isEmpty()) {
                numFramesWithoutDetection++;

                // If we haven't seen a tag for a few frames, lower the decimation
                // so we can hopefully pick one up if we're e.g. far back
                int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;
                if (numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                    float DECIMATION_LOW = 2;
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }
            }
            // We do see tags!
            else {
                numFramesWithoutDetection = 0;

                // If the target is within 1 meter, turn on high decimation to
                // increase the frame rate
                float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
                if (detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                    float DECIMATION_HIGH = 3;
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                for (AprilTagDetection detection : detections) {
                    Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

                        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
                        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
//                        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
                        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
                        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", rot.firstAngle));
//                        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", rot.secondAngle));
//                        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", rot.thirdAngle));
                        telemetry.update();
                }
            }
        }
    }

    public Pose2d getCurrentPosition()
    {
        AprilTagDetection referenceAprilTag = getRelevantTag();

        if(referenceAprilTag == null)
            return null;

        double relevantYOffset = GlobalVariables.alliance == Alliance.Red ? AprilTagConstants.leftCameraYOffset : AprilTagConstants.rightCameraYOffset;

        Vector2d referenceTagVector = calculateAngleAdjustedVector(referenceAprilTag,relevantYOffset);

        Orientation referenceAprilTagRotation = Orientation.getOrientation(referenceAprilTag.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
        Pose2d robotPose = getRobotPose(referenceAprilTag, referenceTagVector, referenceAprilTagRotation);
        telemetry.addData("Robot Pose: ",robotPose);
        return robotPose;
    }

    private Pose2d getRobotPose(AprilTagDetection referenceAprilTag, Vector2d referenceTagVector, Orientation referenceAprilTagRotation) {
        Pose2d robotPose = null;
        switch(referenceAprilTag.id) {
            case 1:
                robotPose = calculatePosition(AprilTagConstants.blueAprilTagPose1, referenceTagVector, referenceAprilTagRotation);
                break;
            case 2:
                robotPose = calculatePosition(AprilTagConstants.blueAprilTagPose2, referenceTagVector, referenceAprilTagRotation);
                break;
            case 3:
                robotPose = calculatePosition(AprilTagConstants.blueAprilTagPose3, referenceTagVector, referenceAprilTagRotation);
                break;
            case 4:
                robotPose = calculatePosition(AprilTagConstants.redAprilTagPose1, referenceTagVector, referenceAprilTagRotation);
                break;
            case 5:
                robotPose = calculatePosition(AprilTagConstants.redAprilTagPose2, referenceTagVector, referenceAprilTagRotation);
                break;
            case 6:
                robotPose = calculatePosition(AprilTagConstants.redAprilTagPose3, referenceTagVector, referenceAprilTagRotation);
                break;
        }
        return robotPose;
    }

    private Pose2d calculatePosition(Pose2d referenceTagLocation, Vector2d referenceTagVector, Orientation referenceAprilTagRotation)
    {
        double MtoIn = 1/0.0254;
        
        double currentX = referenceTagLocation.getX() - (referenceTagVector.getX() * MtoIn);
        double currentY = referenceTagLocation.getY() - (referenceTagVector.getY() * MtoIn);
        double currentAngle = referenceTagLocation.getHeading() - Math.toRadians(referenceAprilTagRotation.firstAngle);
        return new Pose2d(currentX, currentY, currentAngle);
    }

    private AprilTagDetection getRelevantTag()
    {
        AprilTagDetection aprilTag = null;
        switch (GlobalVariables.alliance) {
            case Red:
                aprilTag = getIDBasedTag(4); //should be 4 or 5
                if(aprilTag == null)
                    aprilTag = getIDBasedTag(5);
                if(aprilTag == null)
                    aprilTag = getIDBasedTag(6);
                break;
            case Blue:
                aprilTag = getIDBasedTag(3);
                if(aprilTag == null)
                    aprilTag = getIDBasedTag(2);
                if(aprilTag == null)
                    aprilTag = getIDBasedTag(1);
                break;
        }
        //telemetry.addData("Tag: ",aprilTag);
        return aprilTag;
    }

    private AprilTagDetection getIDBasedTag(int id)
    {
        detectAprilTag();
        if (detections == null)
            return null;
        for (AprilTagDetection detection : detections) {
            if (detection.id == id)
                return detection;
        }
        return null; //should never get here
    }

    private Vector2d calculateAngleAdjustedVector(AprilTagDetection referenceAprilTag, double cameraYOffset)
    {
        Orientation referenceAprilTagRotation = Orientation.getOrientation(referenceAprilTag.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.RADIANS);
        double a = referenceAprilTag.pose.z;// * Math.cos(30); //does cos 30 work?????
        double b = referenceAprilTag.pose.x;
        double f = referenceAprilTagRotation.firstAngle; //radians

        double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
        double g = Math.atan(b / a); //radians
        double h = f - g;

        double x1 = c * Math.cos(h);
        double y1 = c * Math.sin(h);

        double i = Math.PI / 2 - f;
        double j = Math.PI / 2 - i;
        double k = Math.PI / 2 - j;

        double x2 = AprilTagConstants.cameraXOffset * Math.cos(j);
        double y2 = AprilTagConstants.cameraXOffset * Math.sin(j);

        double x3 = cameraYOffset * Math.cos(k);
        double y3 = cameraYOffset * Math.sin(k);

        double x = x1 + x2 - x3;
        double y = y1 + y2 + y3;

        return new Vector2d(x,y);
    }
}

