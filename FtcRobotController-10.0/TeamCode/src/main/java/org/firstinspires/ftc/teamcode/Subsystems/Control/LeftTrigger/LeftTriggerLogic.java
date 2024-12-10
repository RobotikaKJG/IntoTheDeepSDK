package org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class LeftTriggerLogic {
    private final LeftTriggerControl leftTriggerControl = new LeftTriggerControl();
    private final SensorControl sensorControl;

    public LeftTriggerLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        //if(activateIntakeMotor()) return;
        if(toggleIntakeMotor()) return;
        if(moveSlidesDown()) return;
    }

    private void completeAction(){
        leftTriggerControl.update();
        ControlStates.setLeftTriggerState(LeftTriggerStates.idle);
    }

    private boolean activateIntakeMotor() {
        if(subsystemsIdle() ||  sampleInIntake()) return false;

        ControlStates.setLeftTriggerState(LeftTriggerStates.activateIntakeMotor);
        completeAction();
        return true;
    }

    private boolean subsystemsIdle() {
        return IntakeStates.getIntakeState() == SubsystemState.Idle && OuttakeStates.getOuttakeState() == SubsystemState.Idle;
    }

    private boolean sampleInIntake(){
        return sensorControl.isAllianceColor() || sensorControl.isYellow();
    }

    private boolean toggleIntakeMotor() {
        if(outtakeActive()||(sampleInIntake() && !intakeActive())) return false;

        ControlStates.setLeftTriggerState(LeftTriggerStates.toggleIntakeMotor);
        completeAction();
        return true;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }

    private boolean moveSlidesDown() {
        if(!outtakeActive()) return false;

        ControlStates.setLeftTriggerState(LeftTriggerStates.moveSlidesDown);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }
}
