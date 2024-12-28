package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeControl {
    private final ArmControl armControl;
    private final ClawControl clawControl;
    private final VerticalSlideControl verticalSlideControl;
    private final ReleaseButtonLogic releaseButtonLogic = new ReleaseButtonLogic();
    private final ReleaseButtonControl releaseButtonControl = new ReleaseButtonControl();

    public OuttakeControl(ArmControl armControl, ClawControl clawControl,
                          VerticalSlideControl verticalSlideControl) {
        this.armControl = armControl;
        this.clawControl = clawControl;
        this.verticalSlideControl = verticalSlideControl;
    }

    public void update() {
        armControl.update();
        clawControl.update();
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
