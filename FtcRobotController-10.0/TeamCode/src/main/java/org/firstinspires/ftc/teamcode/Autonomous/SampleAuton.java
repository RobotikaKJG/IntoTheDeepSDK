package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.extendOuttakeAndIntakeAndFlipArm;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setArmState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleClawState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleLockState;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
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
    private boolean rotateCommandIssued = false;
    private IntakeMotorLogic intakeMotorLogic;
    private static double subPathYcoordinate = -2;
    private static Pose2d fifthIntakePose = new Pose2d(-30, subPathYcoordinate, Math.toRadians(0));

    public SampleAuton(SampleMecanumDrive drive, IntakeMotorLogic intakeMotorLogic) {
        this.drive = drive;
        trajectories = new SampleTrajectories(drive);
        this.intakeMotorLogic = intakeMotorLogic;
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
                if (!waitForFlipThen(SampleAutonState.releaseSample,1)) return; // DO NOT REDUCE FURTHER (0.8 for catapult?)
                break;

            case releaseSample:
                if (!waitThenRelease(SampleAutonState.secondSampleIntakePath)) return;
                addWaitTime(0.05); // DO NOT REDUCE FURTHER
                break;

            case secondSampleIntakePath:
                if(!startTrajectoryAndContinue(trajectories.secondSampleIntakePath(), SampleAutonState.startIntakeForSecondSample)) return;
                addWaitTime(0.25); // DO NOT REDUCE FURTHER
                break;

            case startIntakeForSecondSample:
                if (!startIntake(SampleAutonState.checkSecondSamplePickup)) return;
                break;

            case checkSecondSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForSecondSample)) return;
                break;

            case retractOuttakeForSecondSample:
                if (!handleRetractOuttake(SampleAutonState.prepareNextCycle)) return;
                break;

            case prepareNextCycle:
                if (!prepareNextCycle(SampleAutonState.driveToPlaceFirstSample)) return;
                break;

            case driveToPlaceFirstSample:
                if (!startTrajectoryAndContinue(trajectories.secondSampleOuttakePath(), SampleAutonState.waitForFlipSecondSample)) return;
                break;

            case waitForFlipSecondSample:
                if (!waitForFlipThen(SampleAutonState.releaseSecondSample,0.7)) return; //DO NOT REDUCE FURTHER (0.475-0.55 for catapult)
                break;

            case releaseSecondSample:
                if (!waitThenRelease(SampleAutonState.thirdSampleIntakePath)) return;
                break;

            case thirdSampleIntakePath:
                if (!startTrajectoryAndContinue(trajectories.followThirdSampleIntakePath(), SampleAutonState.startIntakeForThirdSample)) return;
                addWaitTime(0.4); // DO NOT REDUCE FURTHER
                break;

            case startIntakeForThirdSample:
                startIntake(SampleAutonState.checkThirdSamplePickup);
                break;

            case checkThirdSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForThirdSample)) return;
                break;

            case retractOuttakeForThirdSample:
                if (!handleRetractOuttake(SampleAutonState.thirdSampleOuttakePath)) return;
                break;

            case thirdSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipThirdSample, null)) return;
                break;

            case waitForFlipThirdSample:
                if (!waitForFlipThen(SampleAutonState.releaseThirdSample,0.8)) return; // DO NOT REDUCE FURTHER (0.5-0.6 for catapult)
                break;

            case releaseThirdSample:
                if (!waitThenRelease(SampleAutonState.forthSampleIntakePath)) return;
                break;

            case forthSampleIntakePath:
                if (!startTrajectoryAndContinue(trajectories.followForthSampleIntakePath(), SampleAutonState.startIntakeForForthSample)) return;
                addWaitTime(0.3); //DO NOT REDUCE FURTHER
                break;

            case startIntakeForForthSample:
                startIntake(SampleAutonState.checkForthSamplePickup);
                break;

            case checkForthSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForForthSample)) return;
                break;

            case retractOuttakeForForthSample:
                if (!handleRetractOuttake(SampleAutonState.forthSampleOuttakePath)) return;
                break;

            case forthSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipForthSample, trajectories.followForthSampleOuttakePath())) return;
                break;

            case waitForFlipForthSample:
                if (!waitForFlipThen(SampleAutonState.releaseForthSample,0.9)) return; // DO NOT REDUCE FURTHER (0.7 for catapult?)
                break;

            case releaseForthSample:
                if (!waitThenRelease(SampleAutonState.prepareNextCycleForFifthSample)) return;
                break;

            case prepareNextCycleForFifthSample:
                if (drive.isBusy()) return;
                if (currentWait > getSeconds()) return;

                GlobalVariables.subCycles = true;
                setArmState(ArmStates.down);
                setSampleClawState(SampleClawStates.fullyOpen);
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                IntakeStates.setMotorState(IntakeMotorStates.idle);
                IntakeStates.setExtendoState(ExtendoStates.retracting);

                sampleAutonState = SampleAutonState.stop;
                currentWait = 0; // Reset wait time for next use
                break;

            case fifthSampleIntakePath:
//                if (drive.isBusy()) return;
//                TrajectorySequence fiveSampleIntakePath =
//                        drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
//                                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
//                                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
//                                .lineToSplineHeading(fifthIntakePose)
//                                .build();
//
//                drive.followTrajectorySequenceAsync(fiveSampleIntakePath);
//                addWaitTime(1);
                sampleAutonState = SampleAutonState.startIntakeForFifthSample;
                break;

            case startIntakeForFifthSample:
                subPathYcoordinate += 5;
                if (currentWait > getSeconds()) return;
                if(!wasIfCalled) {
                    IntakeStates.setExtendoState(ExtendoStates.fullyExtend);
                    wasIfCalled = true;
                }
                if (drive.isBusy()) return;
                startIntake(SampleAutonState.waiting);
                wasIfCalled = false;
                break;

            case waiting:
                if (checkSamplePickup(SampleAutonState.fifthSampleOuttakePath)) return;
//                if (currentWait > getSeconds()) return;
//                if(rotateCommandIssued) return;
//                if(IntakeStates.getAutoCloseStates() != AutoCloseStates.checkColor) return;
//                System.out.println("spin");

//                drive.turnAsync(Math.toRadians(15));
//                rotateCommandIssued = true;

                break;


            case fifthSampleOuttakePath:
                if (rotateCommandIssued) {
                    drive.turnAsync(Math.toRadians(-15));
                    rotateCommandIssued = false;
                }
                if (currentWait > getSeconds()) return;

                OuttakeStates.extendOuttakeAndFlipArm();

                sampleAutonState = SampleAutonState.waitForFlipFifthSample;
                break;

            case waitForFlipFifthSample:
                if (waitForFlipThen(SampleAutonState.releaseFifthSample, AutonomousConstants.flipSubArmWait)) return;
                break;

            case releaseFifthSample:
//                if(drive.isBusy()) return;
                if (waitThenRelease(SampleAutonState.prepareNextCycleForSubSample)) return;
                break;

            case prepareNextCycleForSubSample:
//                if (drive.isBusy()) return;

                setArmState(ArmStates.down);
                setSampleClawState(SampleClawStates.fullyOpen);

                if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);
                if (currentWait > getSeconds()) return;

                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);



                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0;
                wasIfCalled = false;
                break;
            // Additional sample states can follow same pattern...
        }
    }

    private boolean waitForFlipThen(SampleAutonState next, double waitTime) {
        addWaitTime(waitTime);
        sampleAutonState = next;
        return true;
    }

    private boolean waitThenRelease(SampleAutonState next) {
        if (currentWait > getSeconds()) return false;
//        OuttakeStates.releaseSample();
        setSampleClawState(SampleClawStates.halfOpen);
        setSampleLockState(SampleLockStates.closed);
        sampleAutonState = next;
        wasIfCalled = false;
        return true;
    }

    private boolean startTrajectoryAndContinue(TrajectorySequence traj, SampleAutonState next) {
//        if (drive.isBusy()) return false;
        if (currentWait > getSeconds()) return false;
        drive.followTrajectorySequenceAsync(traj);

//        addWaitTime(AutonomousConstants.intakeSampleWait);

        sampleAutonState = next;
        return true;
    }

    private boolean startIntake(SampleAutonState next) {
        if (currentWait > getSeconds()) return false;

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

    private double retractWaitStartTime = -1;

    private boolean checkSamplePickup(SampleAutonState next) {
        if (drive.isBusy() && !wasIfCalled) return false;
        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.fullyOpen);

        // Starts timer
        if (!wasIfCalled && retractWaitStartTime == -1) {
            retractWaitStartTime = getSeconds();
        }

        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.waitToRetract && !wasIfCalled) {
            // Waited long enough?
            if (getSeconds() - retractWaitStartTime >= 1.5 && !rotateCommandIssued) {
                // Rotate and try to eject sample
                drive.turn(Math.toRadians(30)); // rotate 30 degrees
                rotateCommandIssued = true;
            }
            return false; // Still waiting
        }

        if (!wasIfCalled) {
            drive.followTrajectorySequenceAsync(trajectories.followFiveSampleOuttakePath());
            addWaitTime(0.5);
            wasIfCalled = true;
            retractWaitStartTime = -1; // Reset for future use
            rotateCommandIssued = false;
        }

////        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.waitToRetract && !wasIfCalled) return false;
//
//        double startTime = getSeconds();
//        if (IntakeStates.getAutoCloseStates() != AutoCloseStates.waitToRetract && !wasIfCalled) {
//            if (getSeconds() - startTime >= 2.0) {
//                drive.turn(Math.toRadians(15));
//                rotateCommandIssued = true;
//            }
//            return false;
//        }
//        if(!wasIfCalled) {
//            drive.followTrajectorySequenceAsync(trajectories.followFiveSampleOuttakePath());
//            addWaitTime(0.5);
//            wasIfCalled = true;
//        }
        if (currentWait > getSeconds()) return false;

        IntakeStates.setMotorState(IntakeMotorStates.idle);
        OuttakeStates.setSampleLockState(SampleLockStates.open);
        OuttakeStates.setSampleClawState(SampleClawStates.closed);
        addWaitTime(0.3);
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
//        if (drive.isBusy()) return false;
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

//        System.out.println(OuttakeStates.getVerticalSlideState());
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
            Thread.sleep(10);
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

    public static Pose2d getFifthIntakePose() {
        return fifthIntakePose;
    }
}
