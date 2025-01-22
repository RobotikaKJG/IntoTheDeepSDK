package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeControl {
    private final ArmControl armControl;
    private final SampleClawControl sampleClawControl;
    private final VerticalSlideControl verticalSlideControl;
    private final ReleaseButtonLogic releaseButtonLogic = new ReleaseButtonLogic();
    private final ReleaseButtonControl releaseButtonControl = new ReleaseButtonControl();

    public OuttakeControl(ArmControl armControl, SampleClawControl sampleClawControl,
                          VerticalSlideControl verticalSlideControl) {
        this.armControl = armControl;
        this.sampleClawControl = sampleClawControl;
        this.verticalSlideControl = verticalSlideControl;
    }

    public void update() {
        armControl.update();
        sampleClawControl.update();
        verticalSlideControl.update();
        releaseButtonControl.update(); // order important, should go before logic to get state to update, NOTE
        releaseButtonLogic.update();

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
