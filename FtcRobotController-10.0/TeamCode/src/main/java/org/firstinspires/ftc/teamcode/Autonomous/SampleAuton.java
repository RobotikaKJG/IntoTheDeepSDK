package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.extendOuttakeAndIntakeAndFlipArm;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setArmState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleClawState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleLockState;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorControl;
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
    private long samplePickupWaitStartTime = -1;
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
                if (!waitThenRelease(SampleAutonState.startIntake, 0.2)) return;
                break;

            case startIntake:
                if (!startIntake(SampleAutonState.checkSamplePickup)) return;
                break;

            case checkSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttake)) return;
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
                if (!samplePickup(SampleAutonState.retractOuttakeForThirdSample)) return;
                break;

            case retractOuttakeForThirdSample:
                if (!handleRetractOuttake(SampleAutonState.thirdSampleOuttakePath)) return;
                break;

            case thirdSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipThirdSample, null)) return;
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
                if (!samplePickup(SampleAutonState.retractOuttakeForForthSample)) return;
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
                if (currentWait > getSeconds()) return;

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

                TrajectorySequence fiveSampleIntakePath =
                        drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
                                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
                                .lineToSplineHeading(new Pose2d(-34, -8, Math.toRadians(0)))
                                .build();

                IntakeStates.setMotorState(IntakeMotorStates.idle);
                drive.followTrajectorySequenceAsync(fiveSampleIntakePath);
                sampleAutonState = SampleAutonState.startIntakeForFifthSample;
                break;

            case startIntakeForFifthSample:
                if (drive.isBusy()) return;
                startIntake(SampleAutonState.waiting);
                break;

            case waiting:
                if (checkSamplePickup(SampleAutonState.fifthSampleOuttakePath)) return;

                break;

//            case returningAfterPickup:
//                IntakeStates.setMotorState(IntakeMotorStates.forward);
////                addWaitTime(0.1);
//                sampleAutonState = SampleAutonState.fifthSampleOuttakePath;
//                break;

            case fifthSampleOuttakePath:
//                if (currentWait > getSeconds()) return;
                IntakeStates.setMotorState(IntakeMotorStates.idle);

                OuttakeStates.setSampleClawState(SampleClawStates.closed);
                drive.followTrajectorySequenceAsync(trajectories.followFiveSampleOuttakePath());

                OuttakeStates.extendOuttakeAndFlipArm();

                sampleAutonState = SampleAutonState.waitForFlipFifthSample;
                break;

            case waitForFlipFifthSample:
                if (waitForFlipThenSub(SampleAutonState.releaseFifthSample)) return;
                break;

            case releaseFifthSample:
//                if(drive.isBusy()) return;
                if (waitThenRelease(SampleAutonState.prepareNextCycleForSubSample, 0.2)) return;
                break;

            case prepareNextCycleForSubSample:
//                if (drive.isBusy()) return;

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

    private boolean waitForFlipThenSub(SampleAutonState next) {
        addWaitTime(AutonomousConstants.flipSubArmFirstWait);

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
        sampleAutonState = next;
        wasIfCalled = false;
        return true;
    }

    private boolean driveAndGo(TrajectorySequence traj, SampleAutonState next) {
//        if (drive.isBusy()) return false;
        drive.followTrajectorySequenceAsync(traj);

//        addWaitTime(AutonomousConstants.intakeSampleWait);

        sampleAutonState = next;
        return true;
    }

    private boolean startIntake(SampleAutonState next) {
        if (drive.isBusy()) return false;
//        addWaitTime(AutonomousConstants.intakeSampleWait);
//        if (currentWait > getSeconds()) return false;

        OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.fullyOpen);
        IntakeStates.setExtendoState(ExtendoStates.fullyExtend);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        OuttakeStates.setSampleLockState(SampleLockStates.closed);
        IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor);
        sampleAutonState = next;
        return true;
    }

    private boolean checkSamplePickup(SampleAutonState next) {
        if (drive.isBusy()) return false;
        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.fullyOpen);
        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) return false;
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        OuttakeStates.setSampleLockState(SampleLockStates.closed);
        sampleAutonState = next;
        return true;
    }

    private boolean samplePickup(SampleAutonState next) {
        // If the drive is busy, reset the timer and exit.
        if (drive.isBusy()) {
            samplePickupWaitStartTime = -1;
            return false;
        }

        // Set the arm and claw to the desired positions.
        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.fullyOpen);

        // Check whether the intake auto-close process has already started.
        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
            // If the timer hasn't been started yet, start it now.
            if (samplePickupWaitStartTime < 0) {
                samplePickupWaitStartTime = System.currentTimeMillis();
            }
            // Calculate elapsed time and check if it is less than the wait time (converted to milliseconds)
            if (System.currentTimeMillis() - samplePickupWaitStartTime < (long)(AutonomousConstants.intakeCloseWait * 1000)) {
                // Still waiting for the timeout; return false
                return false;
            }
            // If the timeout has been reached, proceed with the transition.
        }

        // Reset the timer once we are proceeding.
        samplePickupWaitStartTime = -1;

        // Update intake states and proceed to the next autonomous state.
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
//        IntakeStates.setMotorState(IntakeMotorStates.idle);
        sampleAutonState = next;
        return true;
    }



    private boolean handleRetractOuttake(SampleAutonState next) {
        if (drive.isBusy()) return false;
//        IntakeStates.setMotorState(IntakeMotorStates.forward);

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

        if(IntakeStates.getExtendoState() != ExtendoStates.retracted) return false;
//        IntakeStates.setExtendoState(ExtendoStates.retracted);
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
        IntakeStates.setMotorState(IntakeMotorStates.idle);

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
