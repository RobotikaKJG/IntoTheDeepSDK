package org.firstinspires.ftc.teamcode.HardwareInterface;

public interface SlideControl {
    int getSlidePosition();
    void setSlidePosition(int position);
    void limitSpeed(double power);
    void resetEncoders();
}
