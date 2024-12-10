package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideProperties;

public class OuttakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    @Override
    public int getSlideMaxExtension() {
        // minimum extension position, first snap pos for outtakeLeft macro
        return 4300;//full is 4356
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideExtensionStep() {
        return 500;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
