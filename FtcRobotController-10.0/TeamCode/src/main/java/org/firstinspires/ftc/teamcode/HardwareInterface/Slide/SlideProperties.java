package org.firstinspires.ftc.teamcode.HardwareInterface.Slide;

public interface SlideProperties {
    int getSlideMaxExtension();
    int getSlideMinExtension();
    double getSlideMovementMaxSpeed();
    int getSlideExtensionStep();
    void setSlideMaxSpeed(double slideMaxSpeed);
}
