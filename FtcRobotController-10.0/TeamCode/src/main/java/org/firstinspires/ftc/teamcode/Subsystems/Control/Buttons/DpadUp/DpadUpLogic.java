package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class DpadUpLogic {
    private final DpadUpControl dpadUpControl = new DpadUpControl();

    public void update() {
        if(forward()) return;
        if(backward()) return;
    }

    private void completeAction(){
        dpadUpControl.update();
        ButtonStates.setDpadUpState(DpadUpStates.idle);
    }

    private boolean forward() {
        if(intakeActive() && IntakeStates.getMotorState() == IntakeMotorStates.forward) return false;
        ButtonStates.setDpadUpState(DpadUpStates.forward);
        completeAction();
        return true;
    }

    private boolean backward() {
        if(intakeActive() && IntakeStates.getMotorState() == IntakeMotorStates.backward) return false;
        ButtonStates.setDpadUpState(DpadUpStates.backward);
        completeAction();
        return true;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }
}
