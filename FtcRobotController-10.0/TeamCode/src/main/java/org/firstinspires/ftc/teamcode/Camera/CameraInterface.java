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

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class CameraInterface {

    CameraControl[] webcams;

    public CameraInterface(HardwareMap hardwareMap, Telemetry telemetry) {
        webcams = new CameraControl[]{
                new PropCameraControl(hardwareMap),
                new AprilTagCameraControl(hardwareMap,telemetry)
        };
    }

    public void startCamera(int index) {
        webcams[index].startCamera();
    }

    public void pauseCamera(int index) {webcams[index].pauseCamera();}

    public void stopCamera(int index) {
        webcams[index].stopCamera();
    }

    public AprilTagCameraControl getAprilTagCamera() {
        return (AprilTagCameraControl) webcams[CameraConstants.aprilTagCamera];
    }

    public PropCameraControl getPropCamera() {
        return (PropCameraControl) webcams[CameraConstants.propCamera];
    }
}
