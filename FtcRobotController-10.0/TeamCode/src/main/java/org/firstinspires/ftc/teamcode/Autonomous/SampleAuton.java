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
    private static double subPathYcoordinate = -13;
    private static double subRotation = 0;
    private static double subPlaceYCoordinate = -60;
    private static Pose2d fifthIntakePose = new Pose2d(-30, subPathYcoordinate, Math.toRadians(subRotation));
    TrajectorySequence fiveSampleIntakePath;
    TrajectorySequence fiveSampleOuttakePath;

    public SampleAuton(SampleMecanumDrive drive, IntakeMotorLogic intakeMotorLogic) {
        this.drive = drive;
        trajectories = new SampleTrajectories(drive);
        this.intakeMotorLogic = intakeMotorLogic;
        fiveSampleIntakePath =
                drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                        .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
                        .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
                        .lineToSplineHeading(fifthIntakePose)
                        .build();

        fiveSampleOuttakePath = drive.trajectorySequenceBuilder(SampleAuton.getFifthIntakePose())
                .lineToSplineHeading(new Pose2d(-48, subPlaceYCoordinate, Math.toRadians(35)))
                .build();
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.preloadTrajectory());

        extendOuttakeAndIntakeAndFlipArm(0.5); //MOST LIKELY TOO LONG, NOTE
        setSampleClawState(SampleClawStates.closed);
        sampleAutonState = SampleAutonState.waitForFlip;
    }

    @Override
    public void run() {
        switch (sampleAutonState) {
            case waitForFlip:
                if (!waitForFlipThen(SampleAutonState.releaseSample,1)) return; // DO NOT REDUCE FURTHER. (0.6-7 for catapult?)
                break;

            case releaseSample:
                if (!waitThenRelease(SampleAutonState.secondSampleIntakePath)) return;
                addWaitTime(0); // DO NOT REDUCE FURTHER.
                break;

            case secondSampleIntakePath:
                if(!startTrajectoryAndContinue(trajectories.secondSampleIntakePath(), SampleAutonState.startIntakeForSecondSample)) return;
                addWaitTime(0.1); // DO NOT REDUCE FURTHER.
                break;

            case startIntakeForSecondSample:
                if (!startIntake(SampleAutonState.checkSecondSamplePickup)) return;
                break;

            case checkSecondSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForSecondSample)) return;
                break;

            case retractOuttakeForSecondSample:
                if (!handleRetractOuttake(SampleAutonState.prepareNextCycle, 0.25)) return; //DO NOT REDUCE FURTHER. (until latch faster)
                break;

            case prepareNextCycle:
                if (!prepareNextCycle(SampleAutonState.driveToPlaceFirstSample)) return;
                break;

            case driveToPlaceFirstSample:
                if (!startTrajectoryAndContinue(trajectories.secondSampleOuttakePath(), SampleAutonState.waitForFlipSecondSample)) return;
                break;

            case waitForFlipSecondSample:
                if (!waitForFlipThen(SampleAutonState.releaseSecondSample,0.65)) return; //DO NOT REDUCE FURTHER. (0.35-45 for catapult)
                break;

            case releaseSecondSample:
                if (!waitThenRelease(SampleAutonState.thirdSampleIntakePath)) return;
                break;

            case thirdSampleIntakePath: //START MOVING EARLIER, NOTE
                if (!startTrajectoryAndContinue(trajectories.followThirdSampleIntakePath(), SampleAutonState.startIntakeForThirdSample)) return;
                addWaitTime(0.1); // DO NOT REDUCE FURTHER?
                break;

            case startIntakeForThirdSample:
                startIntake(SampleAutonState.checkThirdSamplePickup);
                break;

            case checkThirdSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForThirdSample)) return;
                break;

            case retractOuttakeForThirdSample:
                if (!handleRetractOuttake(SampleAutonState.thirdSampleOuttakePath,0.25)) return;
                break;

            case thirdSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipThirdSample, null)) return;
                break;

            case waitForFlipThirdSample:
                if (!waitForFlipThen(SampleAutonState.releaseThirdSample,0.65)) return; // DO NOT REDUCE FURTHER. (0.4-5 for catapult)
                break;

            case releaseThirdSample:
                if (!waitThenRelease(SampleAutonState.forthSampleIntakePath)) return;
                break;

            case forthSampleIntakePath:
                if (!startTrajectoryAndContinue(trajectories.followForthSampleIntakePath(), SampleAutonState.startIntakeForForthSample)) return;
                addWaitTime(0.1); //DO NOT REDUCE FURTHER?
                break;

            case startIntakeForForthSample:
                startIntake(SampleAutonState.checkForthSamplePickup);
                break;

            case checkForthSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForForthSample)) return;
                break;

            case retractOuttakeForForthSample:
                if (!handleRetractOuttake(SampleAutonState.forthSampleOuttakePath,0.25)) return;
                break;

            case forthSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipForthSample, trajectories.followForthSampleOuttakePath())) return;
                break;

            case waitForFlipForthSample:
                if (!waitForFlipThen(SampleAutonState.releaseForthSample,0.65)) return; // DO NOT REDUCE FURTHER? (0.7 for catapult?)
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

                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0; // Reset wait time for next use
                break;

            case fifthSampleIntakePath:
                if (drive.isBusy()) return;

                drive.followTrajectorySequenceAsync(fiveSampleIntakePath);
                addWaitTime(0.8);
                sampleAutonState = SampleAutonState.startIntakeForFifthSample;
                break;

            case startIntakeForFifthSample:
                subPathYcoordinate += 0.5;
                subRotation += 25;
                subPlaceYCoordinate += 1;
                fiveSampleIntakePath =
                        drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
                                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
                                .lineToSplineHeading(fifthIntakePose)
                                .build();
                fiveSampleOuttakePath =
                        drive.trajectorySequenceBuilder(SampleAuton.getFifthIntakePose())
                        .lineToSplineHeading(new Pose2d(-48, subPlaceYCoordinate, Math.toRadians(35)))
                        .build();

                if (currentWait > getSeconds()) return;
                if(!wasIfCalled) {
                    IntakeStates.setExtendoState(ExtendoStates.fullyExtend);
                    setArmState(ArmStates.intake);
                    setSampleClawState(SampleClawStates.fullyOpen);
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
                    drive.turnAsync(Math.toRadians(15));
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
        IntakeStates.setExtendoState(ExtendoStates.sampleExtend);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        OuttakeStates.setSampleLockState(SampleLockStates.closed);
        IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor);
        sampleAutonState = next;
        return true;
    }

    private double retractWaitStartTime = -1;

    private boolean checkSamplePickup(SampleAutonState next) {
        if (drive.isBusy() && !wasIfCalled) return false;


        // Starts timer
        if (!wasIfCalled && retractWaitStartTime == -1) {
            retractWaitStartTime = getSeconds();
        }

        if (!intakeClosing() && !wasIfCalled) {
            // Waited long enough?
            if (getSeconds() - retractWaitStartTime >= 0.75 && !rotateCommandIssued) {
                // Rotate and try to eject sample
                drive.turn(Math.toRadians(-20)); // rotate 30 degrees
                rotateCommandIssued = true;
            }
            return false; // Still waiting
        }

        if (!wasIfCalled) {
            drive.followTrajectorySequenceAsync(trajectories.followFiveSampleOuttakePath());
            addWaitTime(1);
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
        if(IntakeStates.getAutoCloseStates() == AutoCloseStates.closeSampleClaw) {
            OuttakeStates.setArmState(ArmStates.down);
            return false;
        }

        if (currentWait > getSeconds()) return false;

        OuttakeStates.setArmState(ArmStates.down);
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        OuttakeStates.setSampleLockState(SampleLockStates.open);
        OuttakeStates.setSampleClawState(SampleClawStates.closed);
        addWaitTime(0.3);
        sampleAutonState = next;
        return true;
    }

    private boolean intakeClosing(){
        return IntakeStates.getAutoCloseStates() == AutoCloseStates.secureGoodSample ||
                IntakeStates.getAutoCloseStates() == AutoCloseStates.ejectExtraSamples ||
                IntakeStates.getAutoCloseStates() == AutoCloseStates.waitForCommand ||
                IntakeStates.getAutoCloseStates() == AutoCloseStates.waitToRetract;
    }

    private boolean samplePickup(SampleAutonState next) {
        // If the drive is busy, reset the timer and exit.
        if (drive.isBusy()) { // This probably slows second sample, NOTE
            samplePickupWaitStartTime = -1;
            return false;
        }

        // Set the arm and claw to the desired positions.
        setArmState(ArmStates.intake);
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


    private boolean handleRetractOuttake(SampleAutonState next, double waitTime) {
        if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);

        if (currentWait > getSeconds()) return false;

        if(!wasIfCalled) {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
            setSampleClawState(SampleClawStates.fullyOpen);
            setArmState(ArmStates.intake);
            wasIfCalled = true;
        }

        if(IntakeStates.getExtendoState() != ExtendoStates.retracted) return false;

        IntakeStates.setMotorState(IntakeMotorStates.idle);

        if (OuttakeStates.getVerticalSlideState() != VerticalSlideStates.closed) return false;

        setArmState(ArmStates.down);
        setSampleClawState(SampleClawStates.closed);
        OuttakeStates.setSampleLockState(SampleLockStates.open);

        sampleAutonState = next;
        currentWait = 0;
        wasIfCalled = false;
        addWaitTime(waitTime);
        return true;
    }

    private boolean prepareNextCycle(SampleAutonState next) {
        return prepareNextCycle(next, null);
    }

    private boolean prepareNextCycle(SampleAutonState next, TrajectorySequence optionalTrajectory) {

        if (optionalTrajectory != null) {
            drive.followTrajectorySequenceAsync(optionalTrajectory);
        }

        if(currentWait > getSeconds()) return false;

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(isForthSample()){
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
            setArmState(ArmStates.up);
            setSampleClawState(SampleClawStates.closed);
        }
        else
            extendOuttakeAndIntakeAndFlipArm(0.2); // MAY NEED TO BE CHANGED, NOTE
        sampleAutonState = next;
        return true;
    }

    private boolean isForthSample() {
        return sampleAutonState == SampleAutonState.retractOuttakeForForthSample ||
                sampleAutonState == SampleAutonState.forthSampleOuttakePath ||
                sampleAutonState == SampleAutonState.waitForFlipForthSample ||
                sampleAutonState == SampleAutonState.releaseForthSample;
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
