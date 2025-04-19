package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.extendOuttakeAndIntakeAndFlipArm;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setArmState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleClawState;
import static org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates.setSampleLockState;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
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
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class SampleAuton implements Auton {

    private final SampleMecanumDrive drive;
    private final SampleTrajectories trajectories;
    private SampleAutonState sampleAutonState;
    private double currentWait = 0;
    private boolean wasIfCalled = false;
    private long samplePickupWaitStartTime = -1;
    private boolean rotateCommandIssued = false;
    private IntakeMotorLogic intakeMotorLogic;
    private static double subPathYcoordinate = -8;
    private static Pose2d fifthIntakePose = new Pose2d(-30, subPathYcoordinate, Math.toRadians(0));
    TrajectorySequence fiveSampleIntakePath;
//    private ElapsedTime time = new ElapsedTime();

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
                if (!waitForFlipThen(SampleAutonState.releaseSample,1.2)) return;
                break;

            case releaseSample:
                if (!waitThenRelease(SampleAutonState.startIntake)) return;
                addWaitTime(0.8);
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
                if (!prepareNextCycle(SampleAutonState.driveToSecondSample)) return;
                break;

            case driveToSecondSample:
                if (!driveAndGo(trajectories.follow2ndSamplePath(), SampleAutonState.waitForFlipSecondSample)) return;
                break;

            case waitForFlipSecondSample:
                if (!waitForFlipThen(SampleAutonState.releaseSecondSample,1.1)) return;
                break;

            case releaseSecondSample:
                if (!waitThenRelease(SampleAutonState.thirdSampleIntakePath)) return;
                break;

            case thirdSampleIntakePath:
                if (!driveAndGo(trajectories.followThirdSampleIntakePath(), SampleAutonState.startIntakeForThirdSample)) return;
                addWaitTime(0.7);
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
                if (!waitForFlipThen(SampleAutonState.releaseThirdSample,1.1)) return;
                break;

            case releaseThirdSample:
                if (!waitThenRelease(SampleAutonState.forthSampleIntakePath)) return;
                break;

            case forthSampleIntakePath:
                if (!driveAndGo(trajectories.followForthSampleIntakePath(), SampleAutonState.startIntakeForForthSample)) return;
                addWaitTime(0.7);
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
                if (!waitForFlipThen(SampleAutonState.releaseForthSample,1.3)) return;
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
//                if (drive.isBusy()) return;
                TrajectorySequence fiveSampleIntakePath =
                        drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
                                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80, 50))
                                .lineToSplineHeading(fifthIntakePose)
                                .build();

                drive.followTrajectorySequenceAsync(fiveSampleIntakePath);
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

    private boolean driveAndGo(TrajectorySequence traj, SampleAutonState next) {
//        if (drive.isBusy()) return false;
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
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                OuttakeStates.setSampleLockState(SampleLockStates.open);
                drive.turn(Math.toRadians(30)); // rotate 30 degrees
                rotateCommandIssued = true;
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                OuttakeStates.setSampleLockState(SampleLockStates.closed);
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

    public static Pose2d getFifthIntakePose() {
        return fifthIntakePose;
    }
}
