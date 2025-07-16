package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideProperties;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class OuttakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 0.5;

    @Override
    public int getSlideMaxExtension() {
        return 2100;
    }

    @Override
    public int getSlideMinExtension() {
        return 0;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideExtensionStep() {
        return 100;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
