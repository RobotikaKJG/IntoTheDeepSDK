package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideProperties;

public class IntakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed;
    @Override
    public double getSlideKP() {
        //proportional gain
        return 0.001;
    }

    @Override
    public double getSlideHoldKP() {
        //slide hold proportional gain
        return 0.001;
    }

    @Override
    public int getSlideMaxExtension() {
        return 3710;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return slideMaxSpeed;
    }

    @Override
    public int getSlideHoldThreshold() {
        //hold controller active below this encoder distance to target
        return 100;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
