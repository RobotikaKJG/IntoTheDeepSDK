package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeStates {
    private static SubsystemState outtakeState = SubsystemState.Idle;
    private static VerticalSlideStates verticalSlideStates = VerticalSlideStates.closed;
    private static SampleClawStates sampleClawState = SampleClawStates.fullyOpen;
    private static PivotStates pivotStates = PivotStates.down;

    public static void setInitialStates() {
        outtakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        pivotStates = PivotStates.down;
        if(GlobalVariables.isAutonomous) {
            sampleClawState = SampleClawStates.closed;
        }
        else {
            sampleClawState = SampleClawStates.fullyOpen;
        }
    }

    public static SubsystemState getOuttakeState() {
        return outtakeState;
    }

    public static void setOuttakeState(SubsystemState state) {
        outtakeState = state;
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
