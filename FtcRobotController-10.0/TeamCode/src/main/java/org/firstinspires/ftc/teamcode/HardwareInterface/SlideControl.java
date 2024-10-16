package org.firstinspires.ftc.teamcode.HardwareInterface;

public interface SlideControl {
    int getSlidePosition();
    void setSlides(double power);
    void limitSpeed(double power);
}
