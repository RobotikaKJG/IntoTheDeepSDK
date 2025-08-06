package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.VerticalSlideStates;

public class IntakeStates {
    private static SubsystemState intakeState = SubsystemState.Idle;
    private static VerticalSlideStates verticalSlideStates = VerticalSlideStates.closed;
    private static SampleClawStates sampleClawState = SampleClawStates.fullyOpen;
    private static PivotStates pivotStates = PivotStates.down;

    public static void setInitialStates() {
        intakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        pivotStates = PivotStates.down;
        if(GlobalVariables.isAutonomous) {
            sampleClawState = SampleClawStates.closed;
        }
        else {
            sampleClawState = SampleClawStates.fullyOpen;
        }
    }

    public static SubsystemState getIntakeState() {
        return intakeState;
    }

    public static void setIntakeState(SubsystemState state) {
        intakeState = state;
    }

    public static VerticalSlideStates getVerticalSlideState() {
        return verticalSlideStates;
    }

    public static void setVerticalSlideState(VerticalSlideStates state) {
        verticalSlideStates = state;
    }

    public static SampleClawStates getSampleClawState() {
        return sampleClawState;
    }

    public static void setSampleClawState(SampleClawStates state) {
        sampleClawState = state;
    }

    public static PivotStates getPivotState() {
        return pivotStates;
    }

    public static void setPivotStates(PivotStates state) {pivotStates = state;}
}
