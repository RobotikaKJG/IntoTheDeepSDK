/*
 * Copyright (c) 2019 OpenFTC Team
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

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class PropCameraControl implements CameraControl {

    OpenCvWebcam webcamLeft;
    OpenCvWebcam webcamRight;
    PropDetectionPipelineLeft pipelineLeft = new PropDetectionPipelineLeft();
    PropDetectionPipelineRight pipelineRight = new PropDetectionPipelineRight();
    private final HardwareMap hardwareMap;

    public PropCameraControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    @Override
    public void startCamera() {

        webcamLeft = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 2"));
        webcamRight = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));

        webcamLeft.setPipeline(pipelineLeft);
        webcamRight.setPipeline(pipelineRight);

        webcamLeft.setMillisecondsPermissionTimeout(10000);
        webcamRight.setMillisecondsPermissionTimeout(10000);
        webcamLeft.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcamLeft.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        webcamRight.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcamRight.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    @Override
    public void pauseCamera() {
        webcamLeft.stopStreaming();
        webcamRight.stopStreaming();
    }

    @Override
    public void stopCamera() {
        pauseCamera();
        webcamLeft.closeCameraDevice();
        webcamRight.closeCameraDevice();
    }

    public void calculatePropPos()
    {
        PropPosData left = pipelineLeft.getTopPos();
        PropPosData right = pipelineRight.getTopPos();

        double leftValue = left.getPosStrength();
        double rightValue = right.getPosStrength();
        if(leftValue > rightValue)
            GlobalVariables.propPos = left.getPropPos();
        else
            GlobalVariables.propPos = right.getPropPos();
    }
}
