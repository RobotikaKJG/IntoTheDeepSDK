package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions.DropSampleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TakeSpecimen.TakeSpecimenStates;
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
    private static SampleLockStates sampleLockState = SampleLockStates.closed;
    private static TakeSpecimenStates takeSpecimenStates = TakeSpecimenStates.idle;
    private static DropSampleStates dropSampleState = DropSampleStates.idle;


    // ExecutorService for Multithreading
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void setInitialStates() {
        outtakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        if(GlobalVariables.isAutonomous) {
            sampleClawState = SampleClawStates.closed;
            sampleLockState = SampleLockStates.open;
        }
        else {
            sampleClawState = SampleClawStates.fullyOpen;
            sampleLockState = SampleLockStates.closed;
        }
        armState = ArmStates.down;
        sampleReleaseButtonStates = SampleReleaseButtonStates.idle;
        specimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;
        specimenClawState = SpecimenClawStates.open;
        hangState = HangStates.retracted;
        takeSpecimenStates = TakeSpecimenStates.idle;
        dropSampleState = DropSampleStates.idle;
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



    // Execute outtake extension and arm flip in parallel
    public static void extendOuttakeAndIntakeAndFlipArm() {

        setSampleClawState(SampleClawStates.closed);

        // Step 1: Extend outtake slides immediately
        CompletableFuture.runAsync(() -> {
            setVerticalSlideState(VerticalSlideStates.highBasket);
        }, executor);

        // Step 2: Delay the intake & flip arm execution by 0.2s
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);  // Wait 0.2 seconds before extending intake and flipping the arm
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            setArmState(ArmStates.up);
            IntakeStates.setExtendoState(ExtendoStates.sampleExtend);
        }, executor);
    }


    // ✅ Execute sample release in a separate thread
    public static void releaseSample() {
//        // Ensure the arm is fully flipped before releasing the sample
//        if (isArmFlipped()) {
//            setSampleClawState(SampleClawStates.halfOpen);
//
//            // Wait for 0.2 seconds to let the sample fully drop
//            try {
//                Thread.sleep(1000); // 200 ms wait
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//
//        }
    }

    public static void extendOuttakeAndFlipArm() {

//        setSampleClawState(SampleClawStates.closed);

        // Step 1: Extend outtake slides immediately
        CompletableFuture.runAsync(() -> {
            setVerticalSlideState(VerticalSlideStates.highBasket);
        }, executor);

        // Step 2: Delay the intake & flip arm execution by 0.2s
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);  // Wait 0.2 seconds before extending intake and flipping the arm
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            setArmState(ArmStates.up);
        }, executor);
    }

    // ✅ Shutdown executor when not needed
    public static void shutdownExecutor() {
        executor.shutdown();
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
}
