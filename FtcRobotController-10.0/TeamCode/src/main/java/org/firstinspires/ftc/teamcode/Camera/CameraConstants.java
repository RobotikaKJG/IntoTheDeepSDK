package org.firstinspires.ftc.teamcode.Camera;

import org.opencv.core.Rect;
import org.opencv.core.Scalar;

public class CameraConstants {
    public static final Scalar white = new Scalar(255.0, 255.0, 255.0);
    public static final Scalar red = new Scalar(255.0, 0.0, 0.0);
    public static final Scalar blue = new Scalar(0.0, 0.0, 255.0);
    public static final Rect leftRect = new Rect(25, 250, 125, 120);//0,250,100,50
    public static final Rect frontRect = new Rect(370, 250, 100, 100); // 300,215,80,50
    public static final Rect rightRect = new Rect(30, 145, 125, 110);//610,230,29,110
    public static final int thickness = 2;
    public static final int aprilTagCamera = 1;
    public static final int propCamera = 0;

}
