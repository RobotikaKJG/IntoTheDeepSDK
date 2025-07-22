package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose.ManualCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;


public class IntakeControl {
    private final IntakeMotorControl intakeMotorControl;
    private final IntakeMotorLogic intakeMotorLogic;
    private final ExtendoControl extendoControl;
    private final CloseControl closeControl;
    private final ManualCloseControl manualCloseControl = new ManualCloseControl();
    private final CloseLogic closeLogic;
    private final EjectionServoControl ejectionServoControl;
    private final SampleEjectionLogic sampleEjectionLogic;
    private final PivotControl pivotControl;

    public IntakeControl(IntakeMotorControl intakeMotorControl, IntakeMotorLogic intakeMotorLogic,
                         ExtendoControl extendoControl, CloseControl closeControl,
                         CloseLogic closeLogic, EjectionServoControl ejectionServoControl,
                         SampleEjectionLogic sampleEjectionLogic, PivotControl pivotControl) {
        this.intakeMotorControl = intakeMotorControl;
        this.intakeMotorLogic = intakeMotorLogic;
        this.extendoControl = extendoControl;
        this.closeControl = closeControl;
        this.closeLogic =  closeLogic;
        this.ejectionServoControl = ejectionServoControl;
        this.sampleEjectionLogic = sampleEjectionLogic;
        this.pivotControl = pivotControl;
    }

    public void update(){
        closeLogic.update();
        closeControl.update();
//        manualCloseControl.update();
        intakeMotorControl.update();
//        intakeMotorLogic.update();
        extendoControl.update();
//        ejectionServoControl.update();
        sampleEjectionLogic.update();
        pivotControl.update();

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
        return IntakeStates.getCloseStates() != CloseStates.idle && IntakeStates.getCloseStates() != CloseStates.checkColor;
    }
}
