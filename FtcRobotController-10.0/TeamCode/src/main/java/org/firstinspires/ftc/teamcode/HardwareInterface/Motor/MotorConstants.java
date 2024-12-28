package org.firstinspires.ftc.teamcode.HardwareInterface.Motor;

public class MotorConstants {
    public static final int frontLeft = 0;
    public static final int backLeft = 1;
    public static final int frontRight = 2;
    public static final int backRight = 3;
    public static final int intake = 4;
    public static final int slideLeft = 5;
    public static final int slideRight = 6;
    public static final int extendo = 7;
    public static final int[][] motorConfig = {
            //separate motors
            {frontLeft},
            {backLeft},
            {frontRight},
            {backRight},
            {intake},
            {slideLeft},
            {slideRight},
            {extendo},
            //various combinations
            {frontLeft, backLeft, frontRight, backRight},
            {frontLeft, backLeft},
            {frontRight, backRight},
            {frontLeft, backRight},
            {backLeft, frontRight},
            {slideLeft, slideRight},
            {frontLeft, backLeft, frontRight, backRight, intake, slideLeft, slideRight, extendo},
            {intake, slideLeft, slideRight, extendo},
            {frontLeft, backLeft, frontRight, backRight, intake}
    };
    // motorConfig combined value names
    public static final int allDrive = 8;
    public static final int leftDrive = 9;
    public static final int rightDrive = 10;
    public static final int frontLeftBackRight = 11;
    public static final int frontRightBackLeft = 12;
    public static final int bothSlides = 13;
    public static final int all = 14;
    public static final int notDrive = 15;
    public static final int notSlide = 16;
}
