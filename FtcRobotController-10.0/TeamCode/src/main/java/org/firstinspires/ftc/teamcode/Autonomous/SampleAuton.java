package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.extendOuttakeAndIntakeAndFlipArm;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setArmState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleClawState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleLockState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setVerticalSlideState;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.DriveConstants;
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
    public SampleAutonState sampleAutonState;
    private double currentWait = 0;
    private boolean wasIfCalled = false;
    private long samplePickupWaitStartTime = -1;
    private boolean rotateCommandIssued = false;
    private IntakeMotorLogic intakeMotorLogic;
    private static double subPathYcoordinate = -13;
    private static double subRotation = -10;
    private static double subPlaceYCoordinate = -58;
//    private static Pose2d fifthIntakePose = new Pose2d(-31, subPathYcoordinate, Math.toRadians(subRotation));
    TrajectorySequence fiveSampleIntakePath;
    TrajectorySequence fiveSampleOuttakePath;
    private double startTime;
    private double endTime;

    public SampleAuton(SampleMecanumDrive drive, IntakeMotorLogic intakeMotorLogic) {
        this.drive = drive;
        trajectories = new SampleTrajectories(drive);
        this.intakeMotorLogic = intakeMotorLogic;
        fiveSampleIntakePath =
                drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                        .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), DriveConstants.TRACK_WIDTH))
                        .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
                        .lineToSplineHeading(getFifthIntakePose())
                        .build();

//        fiveSampleOuttakePath = drive.trajectorySequenceBuilder(SampleAuton.getFifthIntakePose())
//                .lineToSplineHeading(new Pose2d(-48, subPlaceYCoordinate, Math.toRadians(35)))
//                .build();
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.preloadTrajectory());

        extendOuttakeAndIntakeAndFlipArm(1.1); //MOST LIKELY TOO LONG, NOTE
        setSampleClawState(SampleClawStates.closed);
        sampleAutonState = SampleAutonState.waitForFlip;
        startTime = getSeconds();
    }

    @Override
    public void run() {
        switch (sampleAutonState) {
            case waitForFlip:
                if (!waitForFlipThen(SampleAutonState.releaseSample,1.1)) return; // DO NOT REDUCE FURTHER. (0.6-7 for catapult?)
                break;

            case releaseSample:
                if (!waitThenRelease(SampleAutonState.secondSampleIntakePath)) return;
                addWaitTime(0.1); // DO NOT REDUCE FURTHER.
                break;

            case secondSampleIntakePath:
//                if(!startTrajectoryAndContinue(trajectories.secondSampleIntakePath(), SampleAutonState.startIntakeForSecondSample)) return;
                sampleAutonState = SampleAutonState.startIntakeForSecondSample;
//                addWaitTime(0.1); // DO NOT REDUCE FURTHER.
                break;

            case startIntakeForSecondSample:
                if (!startIntake(SampleAutonState.checkSecondSamplePickup)) return;
                break;

            case checkSecondSamplePickup:
                if (!samplePickup(SampleAutonState.retractOuttakeForSecondSample)) return;
                break;

            case retractOuttakeForSecondSample:
                if (!handleRetractOuttake(SampleAutonState.prepareNextCycle, 0.2)) return; //DO NOT REDUCE FURTHER. (until latch faster)
                break;

            case prepareNextCycle:
                if (!prepareNextCycle(SampleAutonState.driveToPlaceSecondSample,0.9)) return;
                break;

            case driveToPlaceSecondSample:
//                if (!startTrajectoryAndContinue(trajectories.followThirdSamplePath(), SampleAutonState.waitForFlipSecondSample)) return;
                sampleAutonState = SampleAutonState.waitForFlipSecondSample;
                break;

            case waitForFlipSecondSample:
                if (!waitForFlipThen(SampleAutonState.releaseSecondSample,0.75)) return; //DO NOT REDUCE FURTHER. (0.35-45 for catapult)
                break;

            case releaseSecondSample:
                if (!waitThenRelease(SampleAutonState.thirdSampleIntakePath)) return;
                addWaitTime(0.1);
                break;

            case thirdSampleIntakePath: //START MOVING EARLIER, NOTE
                if (!startTrajectoryAndContinue(trajectories.followThirdSamplePath(), SampleAutonState.startIntakeForThirdSample)) return;

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
                if (!prepareNextCycle(SampleAutonState.waitForFlipThirdSample, 1)) return;
                break;

            case waitForFlipThirdSample:
                if (!waitForFlipThen(SampleAutonState.releaseThirdSample,0.8)) return; // DO NOT REDUCE FURTHER. (0.4-5 for catapult)
                break;

            case releaseThirdSample:
                if (!waitThenRelease(SampleAutonState.forthSampleIntakePath)) return;
                addWaitTime(0.1);

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
                if (!handleRetractOuttake(SampleAutonState.forthSampleOuttakePath,0.2)) return;
                break;

            case forthSampleOuttakePath:
                if (!prepareNextCycle(SampleAutonState.waitForFlipForthSample, trajectories.followForthSampleOuttakePath(),0)) return;
                break;

            case waitForFlipForthSample:
                if (!waitForFlipThen(SampleAutonState.releaseForthSample,0.7)) return; // DO NOT REDUCE FURTHER? (0.5 for catapult?)
                break;

            case releaseForthSample:
                if (!waitThenRelease(SampleAutonState.prepareNextCycleForFifthSample)) return;
//                addWaitTime(0.1);
                break;

            case prepareNextCycleForFifthSample:
//                if (currentWait > getSeconds()) return;
//                sampleAutonState = SampleAutonState.stop;

//                if (drive.isBusy()) return;
                if (currentWait > getSeconds()) return;

                GlobalVariables.subCycles = true;
//                setArmState(ArmStates.down);
//                setSampleClawState(SampleClawStates.fullyOpen);
//                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                IntakeStates.setMotorState(IntakeMotorStates.idle);
                IntakeStates.setExtendoState(ExtendoStates.retracting);

                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0; // Reset wait time for next use
                break;

            case fifthSampleIntakePath:
                if (drive.isBusy()) return;

                setArmState(ArmStates.down);
                setSampleClawState(SampleClawStates.fullyOpen);
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);

                drive.followTrajectorySequenceAsync(fiveSampleIntakePath);
                addWaitTime(1.1);
                sampleAutonState = SampleAutonState.extendExtendoForFifthSample;
                break;

            case extendExtendoForFifthSample:
                if(!wasIfCalled) {
                    subPathYcoordinate -= 1;
                    subRotation += 10;
//                    subPlaceYCoordinate += 1;
                    wasIfCalled = true;
                }
                fiveSampleIntakePath =
                        drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), DriveConstants.TRACK_WIDTH))
                                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
                                .lineToSplineHeading(getFifthIntakePose())
                                .build();
//                fiveSampleOuttakePath =
//                        drive.trajectorySequenceBuilder(SampleAuton.getFifthIntakePose())
//                        .lineToSplineHeading(new Pose2d(-48, subPlaceYCoordinate, Math.toRadians(35)))
//                        .build();

                if (currentWait > getSeconds()) return;

                IntakeStates.setExtendoState(ExtendoStates.fullyExtend);
                setArmState(ArmStates.intake);
                setSampleClawState(SampleClawStates.fullyOpen);
                addWaitTime(0.6);
                sampleAutonState = SampleAutonState.startIntakeForFifthSample;
                wasIfCalled = false;
                break;

            case startIntakeForFifthSample:
                if (currentWait > getSeconds()) return;

                startIntake(SampleAutonState.waiting);
                break;

            case waiting:
                if (checkSamplePickup(SampleAutonState.fifthSampleOuttakePath)) return;
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
                if (waitThenRelease(SampleAutonState.fifthSampleIntakePath)) return;
                break;

            case prepareNextCycleForSubSample:
//                if (drive.isBusy()) return;

                setArmState(ArmStates.down);
                setSampleClawState(SampleClawStates.fullyOpen);
//
//                if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);
//                if (currentWait > getSeconds()) return;

                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);



                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.fifthSampleIntakePath;
                currentWait = 0;
                wasIfCalled = false;
                break;
            // Additional sample states can follow same pattern...
            case stop:
                if(!wasIfCalled)
                    endTime = getSeconds();
                setArmState(ArmStates.intake);
                setVerticalSlideState(VerticalSlideStates.close);
                IntakeStates.setExtendoState(ExtendoStates.retracting);
                IntakeStates.setMotorState(IntakeMotorStates.idle);
                System.out.println(endTime-startTime);
                break;
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
            addWaitTime(0.9); // SUBNOTE TIME
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
        addWaitTime(0.2);
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
        if (IntakeStates.getAutoCloseStates() == AutoCloseStates.idle)
            return false;
        // Check whether the intake auto-close process has already started.
        else if (IntakeStates.getAutoCloseStates() == AutoCloseStates.checkColor) {
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
        IntakeStates.setMotorState(IntakeMotorStates.backward);
        sampleAutonState = next;
        return true;
    }


    private boolean handleRetractOuttake(SampleAutonState next, double waitTime) {
        if (currentWait == 0) addWaitTime(AutonomousConstants.flipArmWait);

        if (currentWait > getSeconds()) return false;

        IntakeStates.setMotorState(IntakeMotorStates.forward);

        if(!wasIfCalled) {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
            setSampleClawState(SampleClawStates.fullyOpen);
            setArmState(ArmStates.intake);
            wasIfCalled = true;
        }

        if(IntakeStates.getExtendoState() != ExtendoStates.retracted) return false;

//        IntakeStates.setMotorState(IntakeMotorStates.idle);

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

    private boolean prepareNextCycle(SampleAutonState next, double waitTime) {
        return prepareNextCycle(next, null, waitTime);
    }

    private boolean prepareNextCycle(SampleAutonState next, TrajectorySequence optionalTrajectory, double waitTime) {

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
            extendOuttakeAndIntakeAndFlipArm(waitTime); // MAY NEED TO BE CHANGED, NOTE
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
        return new Pose2d(-31, subPathYcoordinate, Math.toRadians(subRotation));
    }
}
