package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeControl {
    private final ArmControl armControl;
    private final SampleClawControl sampleClawControl;
    private final SpecimenClawControl specimenClawControl;
    private final VerticalSlideControl verticalSlideControl;
    private final SampleReleaseButtonLogic sampleReleaseButtonLogic = new SampleReleaseButtonLogic();
    private final SampleReleaseButtonControl sampleReleaseButtonControl = new SampleReleaseButtonControl();

    public OuttakeControl(ArmControl armControl, SampleClawControl sampleClawControl, SpecimenClawControl specimenClawControl,
                          VerticalSlideControl verticalSlideControl) {
        this.armControl = armControl;
        this.sampleClawControl = sampleClawControl;
        this.specimenClawControl = specimenClawControl;
        this.verticalSlideControl = verticalSlideControl;
    }

    public void update() {
        armControl.update();
        sampleClawControl.update();
        verticalSlideControl.update();
        sampleReleaseButtonControl.update(); // order important, should go before logic to get state to update, NOTE
        sampleReleaseButtonLogic.update();

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
