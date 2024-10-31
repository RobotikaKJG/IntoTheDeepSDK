package org.firstinspires.ftc.teamcode.HardwareInterface;

public interface SlideProperties {
    double getSlideKP();
    double getSlideHoldKP();
    int getSlideMaxExtension();
    double getSlideMovementMaxSpeed();
    int getSlideHoldThreshold();
    int getSlideExtensionStep();

    void setSlideMaxSpeed(double slideMaxSpeed);
}
