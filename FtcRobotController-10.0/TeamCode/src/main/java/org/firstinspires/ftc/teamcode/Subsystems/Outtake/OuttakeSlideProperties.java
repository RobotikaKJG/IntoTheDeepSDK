package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideProperties;

public class OuttakeSlideProperties implements SlideProperties {


    @Override
    public double getSlideKP() {
        //proportional gain
        return 0.0023;
    }

    @Override
    public double getSlideHoldKP() {
        //slide hold proportional gain
        return 0.002;
    }

    @Override
    public int getSlideMaxExtension() {
        // minimum extension position, first snap pos for outtake macro
        return 950;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        //(reduced from 3000 due to no current need above line 2) true limit around 3080 PPR, lower limit for safety
        return 2840;
    }

    @Override
    public int getSlideHoldThreshold() {
        //hold controller active below this encoder distance to target
        return 75;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {

    }
}
