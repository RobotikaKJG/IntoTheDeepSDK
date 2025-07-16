package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class CircleLogic {
    private final CircleControl circleControl = new CircleControl();
    private final SensorControl sensorControl;

    public CircleLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        stepDown();
        return;
    }

    private void completeAction(){
        circleControl.update();
        ButtonStates.setCircleState(CircleStates.idle);
    }

    private void stepDown() {
        ButtonStates.setCircleState(CircleStates.stepDown);
        completeAction();
    }

    private boolean clawClosed() {
        return OuttakeStates.getSampleClawState() == SampleClawStates.closed;
    }
}
