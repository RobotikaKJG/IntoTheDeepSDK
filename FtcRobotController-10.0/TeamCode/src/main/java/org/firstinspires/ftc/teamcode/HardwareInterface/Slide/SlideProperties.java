package org.firstinspires.ftc.teamcode.HardwareInterface.Slide;

public interface SlideProperties {
    int getSlideMaxExtension();
    double getSlideMovementMaxSpeed();
    int getSlideExtensionStep();

    void setSlideMaxSpeed(double slideMaxSpeed);
}
