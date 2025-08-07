package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class RightTriggerLogic {
    private final RightTriggerControl rightTriggerControl = new RightTriggerControl();
    private final SensorControl sensorControl;

    public RightTriggerLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        stepDown();
        return;
    }

    private void completeAction(){
        rightTriggerControl.update();
        ButtonStates.setRightTriggerState(RightTriggerStates.idle);
    }

    private void stepDown() {
        ButtonStates.setRightTriggerState(RightTriggerStates.stepDown);
        completeAction();
    }

    private boolean clawClosed() {
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }
}
