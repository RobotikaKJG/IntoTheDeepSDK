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
        return 550; // change, NIGHTNOTE
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
        return 125; // change, NIGHTNOTE
    }

    @Override
    public void setSlideMaxSpeed(double slideMaxSpeed) {
        this.slideMaxSpeed = slideMaxSpeed;
    }
}
