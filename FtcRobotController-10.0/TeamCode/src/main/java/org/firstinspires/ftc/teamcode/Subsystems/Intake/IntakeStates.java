package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class IntakeStates {
    private static SubsystemState intakeState = SubsystemState.Idle;
    private static LatchStates latchState = LatchStates.closed;
    private static IntakeMotorStates motorState = IntakeMotorStates.idle;
    private static ArmSlideStates armSlideStates = ArmSlideStates.closed;
    private static PivotStates pivotStates = PivotStates.down;
    private static AutoCloseStates autoCloseStates = AutoCloseStates.idle;
    private static AutoEjectStates autoEjectStates = AutoEjectStates.idle;
    private static AutoTakeStates autoTakeStates = AutoTakeStates.idle;
    private static HangStates hangStates = HangStates.idle;

    public static void setInitialStates() {
        intakeState = SubsystemState.Idle;
        latchState = LatchStates.closed;
        motorState = IntakeMotorStates.idle;
        armSlideStates = ArmSlideStates.closed;
        pivotStates = PivotStates.down;
        autoCloseStates = AutoCloseStates.idle;
        autoEjectStates = AutoEjectStates.idle;
        autoTakeStates = AutoTakeStates.idle;
        hangStates = HangStates.idle;
    }

    public static SubsystemState getIntakeState() {
        return intakeState;
    }

    public static void setIntakeState(SubsystemState state) {
        intakeState = state;
    }

    public static LatchStates getLatchState() {
        return latchState;
    }

    public static void setLatchState(LatchStates state) {
        latchState = state;
    }

    public static IntakeMotorStates getMotorState() {
        return motorState;
    }

    public static void setMotorState(IntakeMotorStates state) {
        motorState = state;
    }

    public static ArmSlideStates getArmSlideState() {
        return armSlideStates;
    }

    public static void setArmSlideState(ArmSlideStates state) {
        armSlideStates = state;
    }

    public static PivotStates getPivotState() {
        return pivotStates;
    }

    public static void setPivotState(PivotStates state) {pivotStates = state;}

    public static AutoCloseStates getAutoCloseState() {
        return autoCloseStates;
    }

    public static void setAutoCloseState(AutoCloseStates state) {autoCloseStates = state;}

    public static AutoEjectStates getAutoEjectState() {
        return autoEjectStates;
    }

    public static void setAutoEjectState(AutoEjectStates state) {autoEjectStates = state;}

    public static HangStates getHangState() {
        return hangStates;
    }

    public static void setHangState(HangStates state) {hangStates = state;}
}
