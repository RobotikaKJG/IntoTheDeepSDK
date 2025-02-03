package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
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
        ControlStates.setCircleState(CircleStates.idle);
    }

    private boolean dropSample() {
        if(!outtakeActive() || !sampleInIntake() || !specimenTaken()) return false;
        ControlStates.setCircleState(CircleStates.dropSample);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean sampleInIntake(){
        return sensorControl.getDistance() < 70;
    }

    private boolean specimenTaken(){
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }
}
