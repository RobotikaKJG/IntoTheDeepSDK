package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Main.ManualOpModes.HangConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.PTO.PTOStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class HangControl {
    private final SlideLogic slideLogic;
    private final MotorControl motorControl;
    private HangStates prevHangState = OuttakeStates.getHangState();

    public HangControl(SlideLogic slideLogic, MotorControl motorControl) {
        this.slideLogic = slideLogic;
        this.motorControl = motorControl;
    }

    public void update() {
        if (OuttakeStates.getHangState() != prevHangState) {
            updateStates();
            prevHangState = OuttakeStates.getHangState();
        }
    }


    private void updateStates() {
        switch (OuttakeStates.getHangState()) {
            case releasePTO:
                releasePTO();
                break;
            case lockPTO:
                lockPTO();
                break;
            case extendSlides:
                extendSlides();
                break;
            case retractSlides:
                retractSlides();
                break;
            case hangOnHooks:
                hangOnHooks();
                break;
            case retracted:
                break;
            case idle:
                break;
        }
    }

    private void releasePTO() {
        OuttakeStates.setPtoState(PTOStates.open);
    }

    private void lockPTO() {
        OuttakeStates.setPtoState(PTOStates.locked);
    }

    private void extendSlides() {
        slideLogic.setSlideExtensionTarget(HangConstants.slidesUpHeight);
    }

    private void retractSlides() {
        slideLogic.setSlideExtensionTarget(HangConstants.slidesDownHeight);
    }

    private void hangOnHooks() {
        GlobalVariables.hangActive = true;
        motorControl.setMotorMode(MotorConstants.frontWheels, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorControl.setMotorPos(MotorConstants.frontLeft, HangConstants.leftHookPos);
        motorControl.setMotorPos(MotorConstants.frontRight, HangConstants.rightHookPos);

        motorControl.setMotorMode(MotorConstants.frontWheels, DcMotor.RunMode.RUN_TO_POSITION);
        motorControl.setMotorSpeed(MotorConstants.frontWheels, HangConstants.motorSpeed);
    }

}
