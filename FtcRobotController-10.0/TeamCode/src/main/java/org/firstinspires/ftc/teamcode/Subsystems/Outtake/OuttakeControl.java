package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeControl {
    private final SampleClawControl sampleClawControl;
    private final VerticalSlideControl verticalSlideControl;
    private final PivotControl pivotControl;

    public OuttakeControl(SampleClawControl sampleClawControl, VerticalSlideControl verticalSlideControl, PivotControl pivotControl) {
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
            OuttakeStates.setOuttakeState(SubsystemState.Run);
        else
            OuttakeStates.setOuttakeState(SubsystemState.Idle);
    }

    private boolean slidesActive() {
        return OuttakeStates.getVerticalSlideState() != VerticalSlideStates.closed;
    }
}
