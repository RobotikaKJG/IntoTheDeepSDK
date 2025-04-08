package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
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
        ButtonStates.setRightBumperState(RightBumperStates.idle);
    }

    private boolean extendExtendo() {
        if((clawClosed() && !outtakeClosing())) return false;

        ButtonStates.setRightBumperState(RightBumperStates.moveExtendoForward);
        completeAction();
        return true;
    }

    private boolean sampleInIntake(){
        return sensorControl.getDistance() < 70;
    }

    private boolean clawClosed() {
        return OuttakeStates.getSampleClawState() == SampleClawStates.closed;
    }

    private boolean outtakeClosing(){
        return OuttakeStates.getSampleReleaseButtonState() == SampleReleaseButtonStates.waitToRetract;
    }

    private boolean extendoExtended() {
        return IntakeStates.getExtendoState() == ExtendoStates.extended;
    }
}
