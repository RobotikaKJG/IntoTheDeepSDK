package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class IntakeStates {
    private static SubsystemState intakeState = SubsystemState.Idle;
    private static ArmSlideStates verticalSlideStates = ArmSlideStates.closed;
    private static PivotStates pivotStates = PivotStates.down;

    public static void setInitialStates() {
        intakeState = SubsystemState.Idle;
        verticalSlideStates = ArmSlideStates.closed;
        pivotStates = PivotStates.down;
    }

    public static SubsystemState getIntakeState() {
        return intakeState;
    }

    public static void setIntakeState(SubsystemState state) {
        intakeState = state;
    }

    public static ArmSlideStates getVerticalSlideState() {
        return verticalSlideStates;
    }

    public static void setVerticalSlideState(ArmSlideStates state) {
        verticalSlideStates = state;
    }

    public static PivotStates getPivotState() {
        return pivotStates;
    }

    public static void setPivotStates(PivotStates state) {pivotStates = state;}
}
