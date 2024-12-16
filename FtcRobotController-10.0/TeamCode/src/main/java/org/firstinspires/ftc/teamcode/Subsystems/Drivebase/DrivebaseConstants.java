package org.firstinspires.ftc.teamcode.Subsystems.Drivebase;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class DrivebaseConstants{
    public static final double driveSpeed = GlobalVariables.slowMode ? 0.5 : 0.1;
    public static double getDriveSpeed(){
        return GlobalVariables.slowMode ? 0.5 : 1;
    }
}