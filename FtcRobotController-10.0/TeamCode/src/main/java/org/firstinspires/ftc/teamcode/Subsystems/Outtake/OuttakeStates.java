package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeStates {
    private static SubsystemState outtakeState = SubsystemState.Idle;
    private static VerticalSlideStates verticalSlideStates = VerticalSlideStates.closed;
    private static SampleClawStates sampleClawState = SampleClawStates.fullyOpen;
    private static ArmStates armState = ArmStates.down;
    private static SampleReleaseButtonStates sampleReleaseButtonStates = SampleReleaseButtonStates.idle;
    private static SpecimenReleaseButtonStates specimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;
    private static SpecimenClawStates specimenClawState = SpecimenClawStates.open;

    public static void setInitialStates(){
        outtakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        sampleClawState = SampleClawStates.fullyOpen;
        armState = ArmStates.down;
        sampleReleaseButtonStates = SampleReleaseButtonStates.idle;
        specimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;
        specimenClawState = SpecimenClawStates.open;
    }

    public static SubsystemState getOuttakeState() {
        return outtakeState;
    }

    public static void setOuttakeState(SubsystemState state) {
        outtakeState = state;
    }


    public static VerticalSlideStates getVerticalSlideState(){
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


    public static ArmStates getArmState() {
        return armState;
    }

    public static void setArmState(ArmStates state) {
        armState = state;
    }


    public static SampleReleaseButtonStates getSampleReleaseButtonState(){
        return sampleReleaseButtonStates;
    }

    public static void setSampleReleaseButtonState(SampleReleaseButtonStates state) {
        sampleReleaseButtonStates = state;
    }


    public static SpecimenClawStates getSpecimenClawState(){
        return specimenClawState;
    }

    public static void setSpecimenClawState(SpecimenClawStates state){
        specimenClawState = state;
    }


    public static SpecimenReleaseButtonStates getSpecimenReleaseButtonState(){
        return specimenReleaseButtonStates;
    }

    public static void setSpecimenReleaseButtonState(SpecimenReleaseButtonStates state) {
        specimenReleaseButtonStates = state;
    }
}
