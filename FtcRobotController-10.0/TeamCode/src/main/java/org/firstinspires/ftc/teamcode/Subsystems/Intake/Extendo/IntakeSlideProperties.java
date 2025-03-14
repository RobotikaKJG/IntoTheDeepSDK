package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideProperties;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class IntakeSlideProperties implements SlideProperties {

    private double slideMaxSpeed = 1;

    private double getSlideMaxSpeed(){
        return GlobalVariables.slowMode ? slideMaxSpeed/2 : slideMaxSpeed;
    }

    @Override
    public int getSlideMaxExtension() {
        return 600; // max is 1740, reduced for safety, 1650 was before Jonas changed
    }

    @Override
    public int getSlideMinExtension() {
        return 0;
    }

    @Override
    public double getSlideMovementMaxSpeed() {
        return getSlideMaxSpeed();
    }

    @Override
    public int getSlideExtensionStep() {
        //return 2400;
        return 150;
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
