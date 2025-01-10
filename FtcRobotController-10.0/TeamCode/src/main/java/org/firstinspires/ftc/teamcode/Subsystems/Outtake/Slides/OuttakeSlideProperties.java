package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideProperties;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class OuttakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    private double getSlideMaxSpeed() {
        return GlobalVariables.slowMode ? slideMaxSpeed / 2 : slideMaxSpeed;
    }

    @Override
    public int getSlideMaxExtension() {
        // minimum extension position, first snap pos for outtakeLeft macro
        return 2000;//full 2170
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return getSlideMaxSpeed();
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
