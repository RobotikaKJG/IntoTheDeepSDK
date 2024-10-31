package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideProperties;

public class IntakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    @Override
    public int getSlideMaxExtension() {
        return 2200;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideExtensionStep() {
        return 550;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
