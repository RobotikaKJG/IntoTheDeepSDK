package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection.SampleEjectionControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Stall.StallControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Stall.StallLogic;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose.ManualCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;


public class IntakeControl {
    private final IntakeMotorControl intakeMotorControl;
    private final IntakeMotorLogic intakeMotorLogic;
    private final ExtendoControl extendoControl;
    private final AutoCloseControl autoCloseControl;
    private final ManualCloseControl manualCloseControl = new ManualCloseControl();
    private final AutoCloseLogic autoCloseLogic;
    private final LatchControl latchControl;
    private final SampleEjectionLogic sampleEjectionLogic = new SampleEjectionLogic();
    private final SampleEjectionControl sampleEjectionControl = new SampleEjectionControl();
    private final StallControl stallControl = new StallControl();
    private final StallLogic stallLogic = new StallLogic();



    public IntakeControl(IntakeMotorControl intakeMotorControl, IntakeMotorLogic intakeMotorLogic,
                         ExtendoControl extendoControl, AutoCloseControl autoCloseControl,
                         AutoCloseLogic autoCloseLogic, LatchControl latchControl) {
        this.intakeMotorControl = intakeMotorControl;
        this.intakeMotorLogic = intakeMotorLogic;
        this.extendoControl = extendoControl;
        this.autoCloseControl = autoCloseControl;
        this.autoCloseLogic =  autoCloseLogic;
        this.latchControl = latchControl;
    }

    public void update(){
        autoCloseLogic.update();
        autoCloseControl.update();
        manualCloseControl.update();
        intakeMotorControl.update();
//        intakeMotorLogic.update();
        extendoControl.update();
        latchControl.update();
        sampleEjectionControl.update();
        sampleEjectionLogic.update();
        stallControl.update();
        stallLogic.update();

        updateIntakeState();
        updateColorState();
    }

    private void updateColorState(){

    }

    private void updateIntakeState(){
        if(intakeMotorActive() || extendoActive() || closingActive())
            IntakeStates.setIntakeState(SubsystemState.Run);
        else
            IntakeStates.setIntakeState(SubsystemState.Idle);
    }

    private boolean extendoActive() {
        return IntakeStates.getExtendoState() != ExtendoStates.retracted;
    }

    private boolean intakeMotorActive()
    {
        return IntakeStates.getMotorState() == IntakeMotorStates.forward || IntakeStates.getMotorState() == IntakeMotorStates.backward;
    }

    private boolean closingActive(){
        return IntakeStates.getAutoCloseStates() != AutoCloseStates.idle && IntakeStates.getAutoCloseStates() != AutoCloseStates.checkColor;
    }
}
