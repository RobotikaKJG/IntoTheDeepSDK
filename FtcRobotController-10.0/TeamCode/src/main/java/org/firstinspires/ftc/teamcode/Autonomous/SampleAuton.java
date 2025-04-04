package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.extendOuttakeAndIntakeAndFlipArm;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setArmState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleClawState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleLockState;

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
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleAuton implements Auton {

    private final SampleMecanumDrive drive;
    private final SampleTrajectories trajectories;
    private SampleAutonState sampleAutonState;
    private double currentWait = 0;
    private boolean wasIfCalled = false;
//    private ElapsedTime time = new ElapsedTime();

    public SampleAuton(SampleMecanumDrive drive) {
        this.drive = drive;
        trajectories = new SampleTrajectories(drive);
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.preloadTrajectory());

        extendOuttakeAndIntakeAndFlipArm();
//        setSampleClawState(SampleClawStates.closed);
        sampleAutonState = SampleAutonState.waitForFlip;
    }

    @Override
    public void run() {
        switch (sampleAutonState) {
            case waitForFlip:
                if (!waitForFlipThen(SampleAutonState.releaseSample)) return;
                break;

            case releaseSample:
                if (!waitThenRelease(SampleAutonState.driveToSecondSample, 0.2)) return;
                break;

            case driveToSecondSample:
                if (!driveAndGo(trajectories.follow2ndSamplePath(), SampleAutonState.startIntake)) return;
                break;

            case startIntake:
                if (!startIntake(SampleAutonState.checkSamplePickup)) return;
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
                if (!waitForFlipThen(SampleAutonState.releaseSecondSample)) return;
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
                if (!waitForFlipThen(SampleAutonState.releaseThirdSample)) return;
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
                if (!waitForFlipThen(SampleAutonState.releaseForthSample)) return;
                break;

            case releaseForthSample:
                if (!waitThenRelease(SampleAutonState.prepareNextCycleForFifthSample, 0.2)) return;
                break;

            case prepareNextCycleForFifthSample:
                if (drive.isBusy()) return;

                setArmState(ArmStates.down);
                setSampleClawState(SampleClawStates.fullyOpen);

                if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);
                if (currentWait > getSeconds()) return;
                if(!wasIfCalled) {
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                    IntakeStates.setMotorState(IntakeMotorStates.idle);
                    IntakeStates.setExtendoState(ExtendoStates.retracting);
                    wasIfCalled = true;
                }


                // Move to the next state after retraction is complete
                //sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0; // Reset wait time for next use
                wasIfCalled = false;
                break;

            case fifthSampleIntakePath:
//                if (drive.isBusy()) return;

                IntakeStates.setMotorState(IntakeMotorStates.idle);
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

                if (checkSamplePickup(SampleAutonState.fifthSampleOuttakePath)) return;
                else {
                    // Begin moving forward slowly to try and collect the sample
                    drive.followTrajectorySequenceAsync(
                            drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                    .forward(3) // move forward a small distance
                                    .build()
                    );

                    sampleAutonState = SampleAutonState.searchingForward;
                }
                break;

            case searchingForward:
                if (drive.isBusy()) return;

                // Check if the sample was picked up after moving forward
                if (checkSamplePickup(SampleAutonState.fifthSampleOuttakePath)) {
                    // If sample was picked up, go back to original position
                    drive.followTrajectorySequenceAsync(
                            drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                                    .back(3) // move backward the same distance
                                    .build()
                    );
                    sampleAutonState = SampleAutonState.returningAfterPickup;
                }
                break;

            case returningAfterPickup:
                if (drive.isBusy()) return;

                // After returning to original position, proceed to outtake
                sampleAutonState = SampleAutonState.fifthSampleOuttakePath;
                break;

            case fifthSampleOuttakePath:
                OuttakeStates.setSampleClawState(SampleClawStates.closed);

                drive.followTrajectorySequenceAsync(trajectories.followFiveSampleOuttakePath());

                OuttakeStates.extendOuttakeAndFlipArm();

                sampleAutonState = SampleAutonState.releaseFifthSample;
                break;

            case waitForFlipFifthSample:
                if (waitForFlipThen(SampleAutonState.releaseFifthSample)) return;
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

                setArmState(ArmStates.down);
                setSampleClawState(SampleClawStates.fullyOpen);

                if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);
                if (currentWait > getSeconds()) return;
                if(!wasIfCalled) {
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                    wasIfCalled = true;
                }


                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0;
                wasIfCalled = false;
                break;
            // Additional sample states can follow same pattern...
        }
    }

    private boolean waitForFlipThen(SampleAutonState next) {
        addWaitTime(AutonomousConstants.flipArmFirstWait);

        //if (!OuttakeStates.isArmFlipped(currentWait)) return false;

        sampleAutonState = next;
        wasIfCalled = false;
        return true;
    }

    private boolean waitThenRelease(SampleAutonState next, double waitTime) {
        if (currentWait > getSeconds()) return false;
//        OuttakeStates.releaseSample();
        setSampleClawState(SampleClawStates.halfOpen);
        setSampleLockState(SampleLockStates.closed);
//        if(!wasIfCalled) {
//            addWaitTime(waitTime);
//            wasIfCalled = true;
//        }
//        if (currentWait > getSeconds()) return false;
        sampleAutonState = next;
        wasIfCalled = false;
        return true;
    }

    private boolean driveAndGo(TrajectorySequence traj, SampleAutonState next) {
        if (drive.isBusy()) return false;
        drive.followTrajectorySequenceAsync(traj);
        sampleAutonState = next;
        return true;
    }

    private boolean startIntake(SampleAutonState next) {
        if (drive.isBusy()) return false;
        IntakeStates.setExtendoState(ExtendoStates.fullyExtend);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        OuttakeStates.setSampleLockState(SampleLockStates.closed);
        sampleAutonState = next;
        return true;
    }

    private boolean checkSamplePickup(SampleAutonState next) {
        if (drive.isBusy()) return false;
        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.fullyOpen);
        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) return false;
        //addWaitTime(AutonomousConstants.intakeCloseWait);
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
        sampleAutonState = next;
        return true;
    }

    private boolean handleRetractOuttake(SampleAutonState next) {
        if (drive.isBusy()) return false;

//        OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.waitToRelease);

        if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);
        //System.out.println(currentWait + " > " + getSeconds());
        if (currentWait > getSeconds()) return false;

        if(!wasIfCalled) {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
            setSampleClawState(SampleClawStates.fullyOpen);
            wasIfCalled = true;
        }

        System.out.println(OuttakeStates.getVerticalSlideState());
        if (OuttakeStates.getVerticalSlideState() != VerticalSlideStates.closed) return false;

        IntakeStates.setExtendoState(ExtendoStates.retracted);
        setSampleClawState(SampleClawStates.closed);
        OuttakeStates.setSampleLockState(SampleLockStates.open);

        sampleAutonState = next;
        currentWait = 0;
        wasIfCalled = false;
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
