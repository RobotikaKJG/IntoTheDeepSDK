package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose.ManualCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeServoControl;


public class IntakeControl {
    private final IntakeMotorControl intakeMotorControl;
    private final IntakeServoControl intakeServoControl;
    private final ExtendoControl extendoControl;
    private final AutoCloseControl autoCloseControl;
    private final ManualCloseControl manualCloseControl = new ManualCloseControl();
    private final AutoCloseLogic autoCloseLogic;
    private final EjectionServoControl ejectionServoControl;
    private final SampleEjectionLogic sampleEjectionLogic;

    public IntakeControl(IntakeMotorControl intakeMotorControl, IntakeServoControl intakeServoControl,
                         ExtendoControl extendoControl, AutoCloseControl autoCloseControl,
                         AutoCloseLogic autoCloseLogic, EjectionServoControl ejectionServoControl,
                         SampleEjectionLogic sampleEjectionLogic) {
        this.intakeMotorControl = intakeMotorControl;
        this.intakeServoControl = intakeServoControl;
        this.extendoControl = extendoControl;
        this.autoCloseControl = autoCloseControl;
        this.autoCloseLogic =  autoCloseLogic;
        this.ejectionServoControl = ejectionServoControl;
        this.sampleEjectionLogic = sampleEjectionLogic;
    }

    public void update(){
        autoCloseLogic.update();
        autoCloseControl.update();
        manualCloseControl.update();
        intakeMotorControl.update();
        intakeServoControl.update();
        extendoControl.update();
        ejectionServoControl.update();
        sampleEjectionLogic.update();

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
        return IntakeStates.getMotorState() == IntakeRotationStates.forward || IntakeStates.getMotorState() == IntakeRotationStates.backward;
    }

    private boolean closingActive(){
        return IntakeStates.getAutoCloseStates() != AutoCloseStates.idle && IntakeStates.getAutoCloseStates() != AutoCloseStates.checkColor;
    }
}
