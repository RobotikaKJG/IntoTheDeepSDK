package org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.DownServo.DownServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class HangControl {
    private final SlideLogic slideLogic;
    private final MotorControl motorControl;
    private HangStates prevHangState = IntakeStates.getHangState();

    public HangControl(SlideLogic slideLogic, MotorControl motorControl) {
        this.slideLogic = slideLogic;
        this.motorControl = motorControl;
    }

    public void update() {
        if (IntakeStates.getHangState() != prevHangState) {
            updateStates();
            prevHangState = IntakeStates.getHangState();
        }
    }


    private void updateStates() {
        switch (IntakeStates.getHangState()) {
            case pivotUp:
                pivotUp();
                break;
            case extendSlides:
                extendSlides();
                break;
            case retractSlides:
                retractSlides();
                break;
            case pivotUpFully:
                pivotUpFully();
                break;
            case idle:
                break;
        }
    }

    private void pivotUp() {
        OuttakeStates.setDownServoState(DownServoStates.down);
        OuttakeStates.setArmState(ArmStates.hangPos);
        motorControl.setMotorPos(MotorConstants.pivot, HangConstants.pivotUpHeight);
    }

    private void extendSlides() {
        slideLogic.setSlideExtensionTarget(HangConstants.slidesUpHeight);
    }

    private void retractSlides() {
        slideLogic.setSlideExtensionTarget(HangConstants.slidesDownHeight);
    }

    private void pivotUpFully() {
        motorControl.setMotorPos(MotorConstants.pivot, IntakeConstants.pivotMaxAngle);
    }

}
