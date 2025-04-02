package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
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
        ButtonStates.setRightBumperState(RightBumperStates.idle);
    }

    private boolean extendExtendo() {
        if((!outtakeInactive() && !outtakeClosing()) || sampleInIntake()) return false;

        ButtonStates.setRightBumperState(RightBumperStates.moveExtendoForward);
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
        return OuttakeStates.getSampleReleaseButtonState() == SampleReleaseButtonStates.waitToRetract;
    }
}
