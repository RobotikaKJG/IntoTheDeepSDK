package org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideProperties;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;

public class IntakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    @Override
    public int getSlideMaxExtension() {
        return IntakeConstants.slideMaxExtention;
    }

    @Override
    public int getSlideMinExtension() {
        return IntakeConstants.slideMinExtention;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideExtensionStep() {
        return IntakeConstants.slideExtentionStep;
    }

    public int getSlideFirstExtensionStep() {
        return IntakeConstants.slideFirstExtentionStep;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
