package org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class DropSampleControl {
    private DropSampleStates prevDropSampleStates;

    public void update() {
        if (prevDropSampleStates != OuttakeStates.getDropSampleState()) {
            updateStates();
            prevDropSampleStates = OuttakeStates.getDropSampleState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getDropSampleState()) {
            case raiseSlides:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowRung);
                break;
            case flipArm:
                OuttakeStates.setArmState(ArmStates.drop);
                break;
            case release:
                OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
                break;
            case flipBack:
                OuttakeStates.setArmState(ArmStates.down);
                break;
            case retractSlides:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                break;
            case waitToRetract:
                break;
            case idle:
                break;
        }
    }
}
