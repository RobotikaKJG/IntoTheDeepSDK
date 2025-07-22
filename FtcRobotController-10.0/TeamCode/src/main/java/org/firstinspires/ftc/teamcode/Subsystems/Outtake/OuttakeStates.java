package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.OuttakePivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions.DropSampleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TakeSpecimen.TakeSpecimenStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeStates {
    private static SubsystemState outtakeState = SubsystemState.Idle;
    private static VerticalSlideStates verticalSlideStates = VerticalSlideStates.closed;
    private static ClawStates ClawState = ClawStates.fullyOpen;
    private static ArmStates armState = ArmStates.down;
    private static SampleReleaseButtonStates sampleReleaseButtonStates = SampleReleaseButtonStates.idle;
    private static SpecimenReleaseButtonStates specimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;
    private static SpecimenClawStates specimenClawState = SpecimenClawStates.open;
    private static HangStates hangState = HangStates.retracted;
    private static SampleLockStates sampleLockState = SampleLockStates.closed;
    private static TakeSpecimenStates takeSpecimenStates = TakeSpecimenStates.idle;
    private static DropSampleStates dropSampleState = DropSampleStates.idle;
    private static OuttakePivotStates outtakePivotStates = OuttakePivotStates.down;


    public static void setInitialStates() {
        outtakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        if(GlobalVariables.isAutonomous) {
            ClawState = ClawStates.closed;
            sampleLockState = SampleLockStates.open;
        }
        else {
            ClawState = ClawStates.fullyOpen;
            sampleLockState = SampleLockStates.closed;
        }
        armState = ArmStates.down;
        sampleReleaseButtonStates = SampleReleaseButtonStates.idle;
        specimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;
        specimenClawState = SpecimenClawStates.open;
        hangState = HangStates.retracted;
        takeSpecimenStates = TakeSpecimenStates.idle;
        dropSampleState = DropSampleStates.idle;
        outtakePivotStates = OuttakePivotStates.down;
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

    public static ClawStates getClawState() {
        return ClawState;
    }

    public static void setClawState(ClawStates state) {
        ClawState = state;
    }

    public static ArmStates getArmState() {
        return armState;
    }

    public static void setArmState(ArmStates state) {
        armState = state;
    }

    public static SampleReleaseButtonStates getSampleReleaseButtonState() {
        return sampleReleaseButtonStates;
    }

    public static void setSampleReleaseButtonState(SampleReleaseButtonStates state) {
        sampleReleaseButtonStates = state;
    }

    public static SpecimenClawStates getSpecimenClawState() {
        return specimenClawState;
    }

    public static void setSpecimenClawState(SpecimenClawStates state) {
        specimenClawState = state;
    }

    public static SpecimenReleaseButtonStates getSpecimenReleaseButtonState() {
        return specimenReleaseButtonStates;
    }

    public static void setSpecimenReleaseButtonState(SpecimenReleaseButtonStates state) {
        specimenReleaseButtonStates = state;
    }

    public static HangStates getHangState() {
        return hangState;
    }

    public static void setHangState(HangStates state) {
        hangState = state;
    }

    // Check if the arm is flipped
//    public static boolean isArmFlipped(double currentWait) {
//        return getVerticalSlideState() == VerticalSlideStates.highBasket && getSeconds() > currentWait;
//    }

    public static SampleLockStates getSampleLockState() {
        return sampleLockState;
    }

    public static void setSampleLockState(SampleLockStates state) {
        sampleLockState = state;
    }




    public static TakeSpecimenStates getTakeSpecimenStates() {
        return takeSpecimenStates;
    }

    public static void setTakeSpecimenStates(TakeSpecimenStates state) {
        takeSpecimenStates = state;
    }

    public static DropSampleStates getDropSampleState() {
        return dropSampleState;
    }

    public static void setDropSampleState(DropSampleStates state) {
        dropSampleState = state;
    }


    public static OuttakePivotStates getPivotState() {
        return outtakePivotStates;
    }

    public static void setPivotState(OuttakePivotStates state) {
        outtakePivotStates = state;
    }
}
