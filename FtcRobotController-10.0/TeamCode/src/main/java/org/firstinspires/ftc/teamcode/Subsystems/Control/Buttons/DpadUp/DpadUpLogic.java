package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp;


import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadUpLogic {
    private final DpadUpControl dpadUpControl = new DpadUpControl();

    public void update() {
        if(reverseMotor()) return;
        if(slideStepUp()) return;
    }

    private void completeAction(){
        dpadUpControl.update();
        ButtonStates.setDpadUpState(DpadUpStates.idle);
    }

    private boolean reverseMotor() {
        if(outtakeActive()) return false;
        ButtonStates.setDpadUpState(DpadUpStates.toggleMotor);
        completeAction();
        return true;
    }

    private boolean slideStepUp() {
        if(!sampleTaken()) return false;
        ButtonStates.setDpadUpState(DpadUpStates.slideStepUp);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean sampleTaken(){
        return OuttakeStates.getClawState() == ClawStates.closed || OuttakeStates.getClawState() == ClawStates.freeMove;
    }
}
