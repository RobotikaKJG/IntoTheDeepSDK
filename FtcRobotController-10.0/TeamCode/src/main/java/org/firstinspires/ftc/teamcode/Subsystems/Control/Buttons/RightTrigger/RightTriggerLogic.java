package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;


import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseStates;
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
//        if (armUp()) return;
//        if (release()) return;
        if(cycleAutoCLoseStates()) return;
        if (manualRetract()) return;
        return;
    }

    private boolean cycleAutoCLoseStates() {
        if(!waitingForConfirmation()) return false;
        ButtonStates.setRightTriggerState(RightTriggerStates.cycleAutoCloseStates);

        completeAction();
        return true;
    }

    private boolean waitingForConfirmation() {
        return IntakeStates.getAutoCloseState() == AutoCloseStates.waitForRetractConfirmation ||
                IntakeStates.getAutoCloseState() == AutoCloseStates.waitForReleaseConfirmation;
    }

    private void completeAction(){
        rightTriggerControl.update();
        ButtonStates.setRightTriggerState(RightTriggerStates.idle);
    }


    private boolean manualRetract() {
        System.out.println("start");
        System.out.println(sensorControl.isLimitSwitchPressed(LimitSwitches.slides));
        System.out.println(closingActive());
        if(sensorControl.isLimitSwitchPressed(LimitSwitches.slides) || closingActive()) return false;
        System.out.println("active");
        ButtonStates.setRightTriggerState(RightTriggerStates.manualRetract);
        completeAction();
        return true;
    }

    private boolean closingActive() {
        return IntakeStates.getAutoCloseState() != AutoCloseStates.idle &&
                IntakeStates.getAutoCloseState() != AutoCloseStates.checkColor;
    }

    private boolean armUp() {
        if(IntakeStates.getArmSlideState() != ArmSlideStates.closed && IntakeStates.getPivotState() == PivotStates.up) return false;
        System.out.println("a");
        ButtonStates.setRightTriggerState(RightTriggerStates.armUp);
        completeAction();
        return true;
    }

    private boolean release() {
        if(IntakeStates.getPivotState() != PivotStates.up) return false;
        System.out.println("r");
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
