package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class IntakeControl {
    private final SpecimenClawControl sampleClawControl;
    private final ArmSlideControl verticalSlideControl;
    private final PivotControl pivotControl;

    public IntakeControl(SpecimenClawControl sampleClawControl, ArmSlideControl verticalSlideControl, PivotControl pivotControl) {
        this.sampleClawControl = sampleClawControl;
        this.verticalSlideControl = verticalSlideControl;
        this.pivotControl = pivotControl;
    }

    public void update() {
        sampleClawControl.update();
        verticalSlideControl.update();
        pivotControl.update();

        updateOuttakeState();
    }

    private void updateOuttakeState(){
        if(slidesActive())
            IntakeStates.setIntakeState(SubsystemState.Run);
        else
            IntakeStates.setIntakeState(SubsystemState.Idle);
    }

    private boolean slidesActive() {
        return IntakeStates.getVerticalSlideState() != ArmSlideStates.closed;
    }
}
