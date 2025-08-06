package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleClaw.SampleClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.VerticalSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.VerticalSlideStates;

public class IntakeControl {
    private final SampleClawControl sampleClawControl;
    private final VerticalSlideControl verticalSlideControl;
    private final PivotControl pivotControl;

    public IntakeControl(SampleClawControl sampleClawControl, VerticalSlideControl verticalSlideControl, PivotControl pivotControl) {
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
        return IntakeStates.getVerticalSlideState() != VerticalSlideStates.closed;
    }
}
