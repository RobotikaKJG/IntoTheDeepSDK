package org.firstinspires.ftc.teamcode.HardwareInterface;

public interface SlideProperties {
    int getSlideMaxExtension();
    double getSlideMovementMaxSpeed();
    int getSlideExtensionStep();

    void setSlideMaxSpeed(double slideMaxSpeed);
}
