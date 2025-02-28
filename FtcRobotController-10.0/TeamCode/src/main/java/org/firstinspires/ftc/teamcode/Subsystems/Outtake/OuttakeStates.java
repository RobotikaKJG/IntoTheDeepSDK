package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
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
    private static HangStates hangState = HangStates.retracted;

    // ExecutorService for Multithreading
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void setInitialStates() {
        outtakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        sampleClawState = SampleClawStates.fullyOpen;
        armState = ArmStates.down;
        sampleReleaseButtonStates = SampleReleaseButtonStates.idle;
        specimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;
        specimenClawState = SpecimenClawStates.open;
        hangState = HangStates.retracted;
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

    // ✅ Check if the arm is flipped
    public static boolean isArmFlipped() {
        return armState == ArmStates.up;
    }

    // ✅ Execute outtake extension and arm flip in parallel
    public static void extendOuttakeAndIntakeAndFlipArm() {

//        setSampleClawState(SampleClawStates.closed);

        // ✅ Step 1: Extend outtake slides immediately
        CompletableFuture.runAsync(() -> {
            setVerticalSlideState(VerticalSlideStates.highBasket);
        }, executor);

        // ✅ Step 2: Delay the intake & flip arm execution by 0.2s
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(200);  // Wait 0.2 seconds before extending intake and flipping the arm
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            setSampleReleaseButtonState(SampleReleaseButtonStates.flipArm);
            IntakeStates.setExtendoState(ExtendoStates.fullyExtend);
        }, executor);
    }





    // ✅ Execute sample release in a separate thread
    public static void releaseSample() {
        // Ensure the arm is fully flipped before releasing the sample
        if (isArmFlipped()) {
            setSampleClawState(SampleClawStates.halfOpen);

            // Wait for 0.2 seconds to let the sample fully drop
            try {
                Thread.sleep(200); // 200 ms wait
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Activate the intake motor after releasing the sample
            IntakeStates.setMotorState(IntakeMotorStates.forward);

        }
    }



    // ✅ Shutdown executor when not needed
    public static void shutdownExecutor() {
        executor.shutdown();
    }
}
