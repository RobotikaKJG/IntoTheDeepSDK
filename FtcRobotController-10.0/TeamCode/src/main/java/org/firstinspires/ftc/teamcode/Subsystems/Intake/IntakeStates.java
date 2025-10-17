package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection.SampleEjectionStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Stall.StallStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose.ManualCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class IntakeStates {
    private static SubsystemState intakeState = SubsystemState.Idle;
    private static IntakeMotorStates motorState = IntakeMotorStates.idleWasForward;
    private static ExtendoStates extendoState = ExtendoStates.retracted;
    private static AutoCloseStates autoCloseStates = AutoCloseStates.idle;
    private static ManualCloseStates manualCloseStates = ManualCloseStates.idle;
    private static LatchStates latchState = LatchStates.closed;
    private static SampleEjectionStates sampleEjectionState = SampleEjectionStates.idle;
    private static StallStates stallState = StallStates.idle;

    public static void setInitialStates(){
        intakeState = SubsystemState.Idle;
        motorState = IntakeMotorStates.idleWasForward;
        extendoState = ExtendoStates.retracted;
        autoCloseStates = AutoCloseStates.idle;
        manualCloseStates = ManualCloseStates.idle;
        latchState = LatchStates.closed;
        sampleEjectionState = SampleEjectionStates.idle;
    }

    public static SubsystemState getIntakeState() {
        return intakeState;
    }

    public static void setIntakeState(SubsystemState state) {
        intakeState = state;
    }


    public static IntakeMotorStates getMotorState() {
        return motorState;
    }

    public static void setMotorState(IntakeMotorStates state) {
        motorState = state;
    }


    public static ExtendoStates getExtendoState() { return extendoState; }

    public static void setExtendoState(ExtendoStates state) { extendoState = state; }


    public static AutoCloseStates getAutoCloseStates() { return autoCloseStates; }

    public static void setAutoCloseStates(AutoCloseStates state) { autoCloseStates = state; }


    public static ManualCloseStates getManualCloseStates() { return manualCloseStates; }

    public static void setManualCloseStates(ManualCloseStates state) { manualCloseStates = state; }

    public static LatchStates getLatchState () {
        return latchState;
    }

    public static void setLatchState (LatchStates state) {
        latchState = state;
    }

    public static SampleEjectionStates getSampleEjectionState() {
        return sampleEjectionState;
    }

    public static void setSampleEjectionState(SampleEjectionStates state) {
        sampleEjectionState = state;
    }

    public static StallStates getStallState() {
        return stallState;
    }

    public static void setStallState(StallStates state) {
        IntakeStates.stallState = state;
    }
}
