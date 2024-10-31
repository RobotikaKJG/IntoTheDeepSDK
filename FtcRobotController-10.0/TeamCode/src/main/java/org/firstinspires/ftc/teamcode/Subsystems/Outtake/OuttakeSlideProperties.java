package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideProperties;

public class OuttakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    @Override
    public int getSlideMaxExtension() {
        // minimum extension position, first snap pos for outtake macro
        return 3000;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideExtensionStep() {
        return 1500;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
