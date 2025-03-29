package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.extendOuttakeAndIntakeAndFlipArm;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setArmState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleClawState;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleAuton implements Auton {

    private final SampleMecanumDrive drive;
    private final SampleTrajectories trajectories;
    private SampleAutonState sampleAutonState;
    private double currentWait = 0;

    public SampleAuton(SampleMecanumDrive drive) {
        this.drive = drive;
        trajectories = new SampleTrajectories(drive);
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.preloadTrajectory());

        extendOuttakeAndIntakeAndFlipArm();
        setSampleClawState(SampleClawStates.closed);
        sampleAutonState = SampleAutonState.waitForFlip;
    }

    @Override
    public void run() {
        switch (sampleAutonState) {
            case waitForFlip:
                if (!waitForFlipThen(SampleAutonState.releaseSample, 1.0)) return;
                break;

            case releaseSample:
                if (!waitThenRelease(SampleAutonState.driveToSecondSample, 0.2)) return;
                break;

            case driveToSecondSample:
                if (!driveAndGo(trajectories.follow2ndSamplePath(), SampleAutonState.startIntake)) return;
                break;

            case startIntake:
                startIntake(SampleAutonState.checkSamplePickup);
                break;

            case checkSamplePickup:
                if (!checkSamplePickup(SampleAutonState.retractOuttake)) return;
                break;

            case retractOuttake:
                if (!handleRetractOuttake(SampleAutonState.prepareNextCycle)) return;
                break;

            case prepareNextCycle:
                if (!prepareNextCycle(SampleAutonState.waitForFlipSecondSample)) return;
                break;

            case waitForFlipSecondSample:
                if (!waitForFlipThen(SampleAutonState.releaseSecondSample, 1.0)) return;
                break;

            case releaseSecondSample:
                if (!waitThenRelease(SampleAutonState.thirdSampleIntakePath, 0.2)) return;
                break;

            case thirdSampleIntakePath:
                if (!driveAndGo(trajectories.followThirdSampleIntakePath(), SampleAutonState.startIntakeForThirdSample)) return;
                break;

            case startIntakeForThirdSample:
                startIntake(SampleAutonState.checkSamplePickupForThirdSample);
                break;

            case checkSamplePickupForThirdSample:
                if (!checkSamplePickup(SampleAutonState.retractOuttakeForThirdSample)) return;
                break;

            case retractOuttakeForThirdSample:
                if (!handleRetractOuttake(SampleAutonState.thirdSampleOuttakePath)) return;
                break;

            case thirdSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipThirdSample, trajectories.followThirdSampleOuttakePath())) return;
                break;

            case waitForFlipThirdSample:
                if (!waitForFlipThen(SampleAutonState.releaseThirdSample, 1.0)) return;
                break;

            case releaseThirdSample:
                if (!waitThenRelease(SampleAutonState.forthSampleIntakePath, 0.2)) return;
                break;

            case forthSampleIntakePath:
                if (!driveAndGo(trajectories.followForthSampleIntakePath(), SampleAutonState.startIntakeForForthSample)) return;
                break;

            case startIntakeForForthSample:
                startIntake(SampleAutonState.checkSamplePickupForForthSample);
                break;

            case checkSamplePickupForForthSample:
                if (!checkSamplePickup(SampleAutonState.retractOuttakeForForthSample)) return;
                break;

            case retractOuttakeForForthSample:
                if (!handleRetractOuttake(SampleAutonState.forthSampleOuttakePath)) return;
                break;

            case forthSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipForthSample, trajectories.followForthSampleOuttakePath())) return;
                break;

            case waitForFlipForthSample:
                if (!waitForFlipThen(SampleAutonState.releaseForthSample, 1.0)) return;
                break;

            case releaseForthSample:
                if (!waitThenRelease(SampleAutonState.prepareNextCycleForFifthSample, 0.2)) return;
                break;

            case prepareNextCycleForFifthSample:
                if (drive.isBusy()) return;

                IntakeStates.setMotorState(IntakeMotorStates.idle);
                IntakeStates.setExtendoState(ExtendoStates.retracting);

                // Move arm back to default position
                OuttakeStates.setArmState(ArmStates.down);

                // Fully open the claw before retracting slides
                OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);

                // Retract slides after the arm is fully reset
                OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.retractSlides);

                // Set wait time to ensure slides fully retract
                if (currentWait == 0) {
                    addWaitTime(AutonomousConstants.slideRetractWait);
                }

                // Prevent state transition until slides are fully down
                if (currentWait > getSeconds()) return;


                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0; // Reset wait time for next use
                break;

            case fifthSampleIntakePath:
//                if (drive.isBusy()) return;


                drive.followTrajectorySequenceAsync(trajectories.followFiveSampleIntakePath());
                sampleAutonState = SampleAutonState.startIntakeForFifthSample;
                break;

            case startIntakeForFifthSample:
                if (drive.isBusy()) return;
                // Extend intake slides
                IntakeStates.setExtendoState(ExtendoStates.fullyExtend);

                // Start intake motor
                IntakeStates.setMotorState(IntakeMotorStates.forward);

                // Move to the next state or stop

                currentWait = getSeconds() + 1.0;
                sampleAutonState = SampleAutonState.waiting;
                break;

            case waiting:
                // Check if 1 second has passed
                if (getSeconds() < currentWait) return; // Stay in this state until time has passed

                // Once 1 second has passed, proceed to the next state
                sampleAutonState = SampleAutonState.fifthSampleOuttakePath;
                break;

            case fifthSampleOuttakePath:
                // Stop the intake motor
                IntakeStates.setMotorState(IntakeMotorStates.idle);

                // Retract the intake slides
                IntakeStates.setExtendoState(ExtendoStates.retracting);

                drive.followTrajectorySequenceAsync(trajectories.followFiveSampleOuttakePath());

                OuttakeStates.extendOuttakeAndFlipArm();

                sampleAutonState = SampleAutonState.waitForFlipFifthSample;
                break;

            case waitForFlipFifthSample:
                // Ensure the arm has fully flipped before proceeding
                if (!OuttakeStates.isArmFlipped()) return;

                // Wait 1 second before releasing the second sample
                sampleAutonState = SampleAutonState.releaseFifthSample;
                break;

            case releaseFifthSample:
                if(drive.isBusy()) return;

                // Release the sample
                OuttakeStates.releaseSample();

                // Wait 0.2 seconds before stopping
                addWaitTime(0.2);
                sampleAutonState = SampleAutonState.prepareNextCycleForSubSample;
                break;

            case prepareNextCycleForSubSample:
                if (drive.isBusy()) return;

                // Move arm back to default position
                OuttakeStates.setArmState(ArmStates.down);

                // Fully open the claw before retracting slides
                OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);

                // Retract slides after the arm is fully reset
                OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.retractSlides);

                // Set wait time to ensure slides fully retract
                if (currentWait == 0) {
                    addWaitTime(AutonomousConstants.slideRetractWait);
                }

                // Prevent state transition until slides are fully down
                if (currentWait > getSeconds()) return;


                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0; // Reset wait time for next use
                break;
            // Additional sample states can follow same pattern...
        }
    }

    private boolean waitForFlipThen(SampleAutonState next, double seconds) {
        if (!OuttakeStates.isArmFlipped()) return false;
        addWaitTime(seconds);
        sampleAutonState = next;
        return true;
    }

    private boolean waitThenRelease(SampleAutonState next, double waitTime) {
        if (currentWait > getSeconds()) return false;
        OuttakeStates.releaseSample();
        addWaitTime(waitTime);
        sampleAutonState = next;
        return true;
    }

    private boolean driveAndGo(TrajectorySequence traj, SampleAutonState next) {
        if (drive.isBusy()) return false;
        drive.followTrajectorySequenceAsync(traj);
        sampleAutonState = next;
        return true;
    }

    private void startIntake(SampleAutonState next) {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        sampleAutonState = next;
    }

    private boolean checkSamplePickup(SampleAutonState next) {
        if (drive.isBusy()) return false;
        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
            addWaitTime(AutonomousConstants.intakeCloseWait);
            IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
            sampleAutonState = next;
            return true;
        }
        return false;
    }

    private boolean handleRetractOuttake(SampleAutonState next) {
        if (drive.isBusy()) return false;

        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.fullyOpen);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);

        if (currentWait == 0) addWaitTime(AutonomousConstants.slideRetractWait);
        if (currentWait > getSeconds()) return false;

        IntakeStates.setExtendoState(ExtendoStates.retracted);
        setSampleClawState(SampleClawStates.closed);

        sampleAutonState = next;
        currentWait = 0;
        return true;
    }

    private boolean prepareNextCycle(SampleAutonState next) {
        return prepareNextCycle(next, null);
    }

    private boolean prepareNextCycle(SampleAutonState next, TrajectorySequence optionalTrajectory) {
        if (drive.isBusy()) return false;

        if (optionalTrajectory != null) {
            drive.followTrajectorySequenceAsync(optionalTrajectory);
        }

        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        extendOuttakeAndIntakeAndFlipArm();
        sampleAutonState = next;
        return true;
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
