package org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class LeftTriggerLogic {

    private final IntakeStates intakeStates;
    private final OuttakeStates outtakeStates;

    public LeftTriggerLogic(IntakeStates intakeStates, OuttakeStates outtakeStates) {
        this.intakeStates = intakeStates;
        this.outtakeStates = outtakeStates;
    }

    public void update() {
        activateIntakeMotor();
        toggleIntakeMotor();
        moveSlidesDown();
    }
    private void activateIntakeMotor() {
        if(subsystemsIdle())
            ControlStates.setLeftTriggerState(LeftTriggerStates.activateIntakeMotor);
    }

    private boolean subsystemsIdle() {
        return IntakeStates.getIntakeState() == SubsystemState.Idle && OuttakeStates.getOuttakeState() == SubsystemState.Idle;
    }

    private void toggleIntakeMotor() {
        if(intakeActive())
            ControlStates.setLeftTriggerState(LeftTriggerStates.toggleIntakeMotor);
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }

    private void moveSlidesDown() {
        if(outtakeActive())
            ControlStates.setLeftTriggerState(LeftTriggerStates.moveSlidesDown);
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }
}
