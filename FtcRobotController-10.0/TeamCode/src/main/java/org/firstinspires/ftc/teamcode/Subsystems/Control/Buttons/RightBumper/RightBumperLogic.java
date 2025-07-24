package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class RightBumperLogic {
    private final RightBumperControl rightBumperControl = new RightBumperControl();
    private final SensorControl sensorControl;

    public RightBumperLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        if(extendExtendo()) return;
        if(moveExtendoForward()) return;
    }

    private void completeAction(){
        rightBumperControl.update();
        ButtonStates.setRightBumperState(RightBumperStates.idle);
    }


    private boolean extendExtendo() {
        if(clawClosed() && !outtakeClosing() && intakeActive()) return false;

        ButtonStates.setRightBumperState(RightBumperStates.extendExtendo);
        completeAction();
        return true;
    }

    private boolean moveExtendoForward() {
        if(clawClosed() && !outtakeClosing() && !intakeActive()) return false;

        ButtonStates.setRightBumperState(RightBumperStates.moveExtendoForward);
        completeAction();
        return true;
    }

    private boolean sampleInIntake(){
        return sensorControl.getDistance() < 70;
    }

    private boolean clawClosed() {
        return OuttakeStates.getClawState() == ClawStates.closed;
    }

    private boolean outtakeClosing(){
        return OuttakeStates.getSampleReleaseButtonState() == SampleReleaseButtonStates.waitToRetract;
    }

    private boolean extendoExtended() {
        return IntakeStates.getExtendoState() == ExtendoStates.extended;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }
}
