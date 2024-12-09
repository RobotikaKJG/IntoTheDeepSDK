package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideProperties;

public class IntakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    @Override
    public int getSlideMaxExtension() {
        //return 2400; // could be 2650 in theory at least
        return 2500;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideExtensionStep() {
        //return 2400;
        return 2500;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
