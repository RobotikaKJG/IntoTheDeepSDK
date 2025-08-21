package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class IntakeControl {
    private final AutoCloseLogic autoCloseLogic;
    private final AutoCloseControl autoCloseControl;
    private final AutoEjectLogic autoEjectLogic;
    private final AutoEjectControl autoEjectControl;
    private final ArmSlideControl armSlideControl;
    private final PivotControl pivotControl;
    private final IntakeMotorControl intakeMotorControl;
    private final LatchControl latchControl;

    private final HangControl hangControl;
    private final HangLogic hangLogic;
    private final SampleEjectionLogic sampleEjectionLogic;

    public IntakeControl(AutoCloseLogic autoCloseControl, AutoCloseControl autoCloseControl1,
                         AutoEjectLogic autoEjectLogic, AutoEjectControl autoEjectControl,
                         ArmSlideControl armSlideControl, PivotControl pivotControl,
                         IntakeMotorControl intakjeMotorControl, LatchControl latchControl,
                         HangControl hangControl, HangLogic hangLogic, SampleEjectionLogic sampleEjectionLogic) {
        this.autoCloseLogic = autoCloseControl;
        this.autoCloseControl = autoCloseControl1;
        this.autoEjectLogic = autoEjectLogic;
        this.autoEjectControl = autoEjectControl;
        this.armSlideControl = armSlideControl;
        this.pivotControl = pivotControl;
        this.intakeMotorControl = intakjeMotorControl;
        this.latchControl = latchControl;
        this.hangControl = hangControl;
        this.hangLogic = hangLogic;
        this.sampleEjectionLogic = sampleEjectionLogic;
    }

    public void update() {
        autoCloseLogic.update();
        autoCloseControl.update();
//        autoEjectLogic.update();
//        autoEjectControl.update();
        armSlideControl.update();
        pivotControl.update();
        intakeMotorControl.update();
        latchControl.update();
        hangControl.update();
        hangLogic.update();
        sampleEjectionLogic.update();

        updateOuttakeState();
    }

    private void updateOuttakeState(){
        if(slidesActive())
            IntakeStates.setIntakeState(SubsystemState.Run);
        else
            IntakeStates.setIntakeState(SubsystemState.Idle);
    }

    private boolean slidesActive() {
        return IntakeStates.getArmSlideState() != ArmSlideStates.close;
    }
}
