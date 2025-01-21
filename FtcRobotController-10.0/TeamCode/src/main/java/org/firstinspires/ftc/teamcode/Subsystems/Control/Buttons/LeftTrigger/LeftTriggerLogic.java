package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class LeftTriggerLogic {
    private final LeftTriggerControl leftTriggerControl = new LeftTriggerControl();
    private final SensorControl sensorControl;

    public LeftTriggerLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        if(toggleIntakeMotor()) return;
        if(moveSlidesDown()) return;
    }

    private void completeAction(){
        leftTriggerControl.update();
        ControlStates.setLeftTriggerState(LeftTriggerStates.idle);
    }

    private boolean sampleInIntake(){
        return sensorControl.getDistance() < 70;
    }

    private boolean toggleIntakeMotor() {
        if((!outtakeInactive() && !outtakeClosing())||(sampleInIntake() && !intakeActive())) return false;

        ControlStates.setLeftTriggerState(LeftTriggerStates.toggleIntakeMotor);
        completeAction();
        return true;
    }

    private boolean outtakeClosing(){
        return OuttakeStates.getReleaseButtonState() == ReleaseButtonStates.waitToRetract;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }

    private boolean moveSlidesDown() {
        if(outtakeInactive()) return false;

        ControlStates.setLeftTriggerState(LeftTriggerStates.moveSlidesDown);
        completeAction();
        return true;
    }

    private boolean outtakeInactive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Idle;
    }
}
