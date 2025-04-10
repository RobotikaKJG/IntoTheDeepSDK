package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class CircleLogic {
    private final CircleControl circleControl = new CircleControl();
    private final SensorControl sensorControl;

    public CircleLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        if(dropSample()) return;
    }

    private void completeAction(){
        circleControl.update();
        ButtonStates.setCircleState(CircleStates.idle);
    }

    private boolean dropSample() {
        if(!clawClosed()) return false;
        ButtonStates.setCircleState(CircleStates.dropSample);
        completeAction();
        return true;
    }

    private boolean clawClosed() {
        return OuttakeStates.getSampleClawState() == SampleClawStates.closed;
    }

    private boolean sampleInIntake(){
        return sensorControl.getDistance() < 70;
    }

    private boolean specimenTaken(){
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }
}
