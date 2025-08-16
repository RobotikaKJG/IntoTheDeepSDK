package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class RightTriggerLogic {
    private final RightTriggerControl rightTriggerControl = new RightTriggerControl();
    private final SensorControl sensorControl;

    public RightTriggerLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        if (retract()) return;
        if (armUp()) return;
        if (release()) return;
        return;
    }

    private void completeAction(){
        rightTriggerControl.update();
        ButtonStates.setRightTriggerState(RightTriggerStates.idle);
    }


    private boolean retract() {
        if(IntakeStates.getArmSlideState() == ArmSlideStates.closed) return false;
        ButtonStates.setRightTriggerState(RightTriggerStates.retract);
        completeAction();
        return true;
    }

    private boolean armUp() {
        if(IntakeStates.getArmSlideState() != ArmSlideStates.closed) return false;
        ButtonStates.setRightTriggerState(RightTriggerStates.armUp);
        completeAction();
        return true;
    }

    private boolean release() {
        if(IntakeStates.getPivotState() != PivotStates.up) return false;
        ButtonStates.setRightTriggerState(RightTriggerStates.release);
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
