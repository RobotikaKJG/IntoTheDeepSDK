package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class LeftTriggerLogic {
    private final LeftTriggerControl rightTriggerControl = new LeftTriggerControl();
    private final SensorControl sensorControl;

    public LeftTriggerLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        if(takeSample()) return;
    }

    private void completeAction(){
        rightTriggerControl.update();
        ButtonStates.setLeftTriggerState(LeftTriggerStates.idle);
    }

    private boolean takeSample() {
        if(IntakeStates.getArmSlideState() == ArmSlideStates.closed || IntakeStates.getArmSlideState() == ArmSlideStates.closing || IntakeStates.getPivotState() == PivotStates.down) return false;
        ButtonStates.setLeftTriggerState(LeftTriggerStates.takeSample);
        completeAction();
        return true;
    }

    private boolean clawClosed() {
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }

    private boolean sampleInIntake() {
        return sensorControl.getDistance() < 70;
    }
}
