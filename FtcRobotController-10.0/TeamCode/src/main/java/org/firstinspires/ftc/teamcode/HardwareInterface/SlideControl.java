package org.firstinspires.ftc.teamcode.HardwareInterface;

import com.qualcomm.robotcore.hardware.DcMotor;

public interface SlideControl {
    int getSlidePosition();
    void setSlidePosition(int position);
    void setSlideMode(DcMotor.RunMode mode);
    void limitSpeed(double power);
    void resetEncoders();
    boolean isLimitSwitchPressed();
}
