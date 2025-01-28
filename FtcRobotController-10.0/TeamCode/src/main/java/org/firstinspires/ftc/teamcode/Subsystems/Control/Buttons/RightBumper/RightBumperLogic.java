package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class RightBumperLogic {
    private final RightBumperControl rightBumperControl = new RightBumperControl();
    private final SensorControl sensorControl;

    public RightBumperLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        if(extendExtendo()) return;
    }

    private void completeAction(){
        rightBumperControl.update();
        ControlStates.setRightBumperState(RightBumperStates.idle);
    }

    private boolean extendExtendo() {
        if((!outtakeInactive() && !outtakeClosing()) || sampleInIntake()) return false;

        ControlStates.setRightBumperState(RightBumperStates.moveExtendoForward);
        completeAction();
        return true;
    }

    private boolean sampleInIntake(){
        return sensorControl.getDistance() < 70;
    }

    private boolean outtakeInactive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Idle;
    }

    private boolean outtakeClosing(){
        return OuttakeStates.getReleaseButtonState() == SampleReleaseButtonStates.waitToRetract;
    }

    private boolean extendoExtended() {
        return IntakeStates.getExtendoState() == ExtendoStates.extended;
    }
}
