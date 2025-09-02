package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class RightTriggerControl {
    public void update() {
        switch (ButtonStates.getRightTriggerState()) {
            case manualRetract:
                manualRetract();
                break;
            case armUp:
                armUp();
                break;
            case release:
                release();
                break;
            case cycleAutoCloseStates:
                cycleAutoCloseStates();
                break;
            case idle:
                break;
        }
    }

    private void cycleAutoCloseStates() {
        switch (IntakeStates.getAutoCloseState()){
            case waitForRetractConfirmation:
                IntakeStates.setAutoCloseState(AutoCloseStates.waitToRetract);
                break;
            case waitForReleaseConfirmation:
                IntakeStates.setAutoCloseState(AutoCloseStates.openLatch);
                break;
        }
    }

    private void manualRetract() {
//        IntakeStates.setPivotState(PivotStates.upSlightly);
//        IntakeStates.setArmSlideState(ArmSlideStates.close);
//        IntakeStates.setMotorState(IntakeMotorStates.idle);
        IntakeStates.setAutoCloseState(AutoCloseStates.waitToRetract);
    }

    private void armUp() {
        IntakeStates.setPivotState(PivotStates.up);
    }

    private void release() {
        IntakeStates.setLatchState(LatchStates.open);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
    }
}
