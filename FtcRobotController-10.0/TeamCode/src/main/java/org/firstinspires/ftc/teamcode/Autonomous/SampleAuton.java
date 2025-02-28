package org.firstinspires.ftc.teamcode.Autonomous;

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

        // Extend outtake and intake slides, and flip the arm with a delay
        OuttakeStates.extendOuttakeAndIntakeAndFlipArm();

        // Ensure the sample claw is closed before moving to the next state
        OuttakeStates.setSampleClawState(SampleClawStates.closed);

        sampleAutonState = SampleAutonState.waitForFlip;
    }

    @Override
    public void run() {
        switch (sampleAutonState) {
            //FIRST SAMPLE
            // Outtake
            case waitForFlip:
                // Ensure the arm has fully flipped before proceeding
                if (!OuttakeStates.isArmFlipped()) return;

                // Wait 1 second before releasing the sample
                addWaitTime(1.0);
                sampleAutonState = SampleAutonState.releaseSample;
                break;

            case releaseSample:
                if (currentWait > getSeconds()) return; // Ensure the arm has had enough time to flip

                // Release the sample
                OuttakeStates.releaseSample();

                // Wait 0.2 seconds before proceeding
                addWaitTime(0.2);
                sampleAutonState = SampleAutonState.driveToSecondSample;
                break;

            // SECOND SAMPLE
            // Intake
            case driveToSecondSample:
                if (drive.isBusy()) return;

                drive.followTrajectorySequenceAsync(trajectories.follow2ndSamplePath());
                sampleAutonState = SampleAutonState.startIntake;
                break;

            case startIntake:
                // Start intake motor
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                sampleAutonState = SampleAutonState.checkSamplePickup;
                break;

            case checkSamplePickup:
                if (drive.isBusy()) return;

                // Wait until the sample is detected
                if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
                    // Sample is collected, set wait time and prepare to retract
                    addWaitTime(AutonomousConstants.intakeCloseWait);
                    IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
                    sampleAutonState = SampleAutonState.retractOuttake;
                }
                break;

            case retractOuttake:
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

                // Fully retract the intake before restarting the cycle
                IntakeStates.setExtendoState(ExtendoStates.retracted);

                // Close the sample claw before restarting the cycle
                OuttakeStates.setSampleClawState(SampleClawStates.closed);

                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.prepareNextCycle;
                currentWait = 0; // Reset wait time for next use
                break;

            case prepareNextCycle:
                if (drive.isBusy()) return;

                try {
                    Thread.sleep(350); // Wait for 0.35 seconds before restarting the cycle
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }

                // Extend outtake and intake slides, and flip the arm again for the second sample
                OuttakeStates.extendOuttakeAndIntakeAndFlipArm();

                sampleAutonState = SampleAutonState.waitForFlipSecondSample;
                break;

            // Outtake
            case waitForFlipSecondSample:
                // Ensure the arm has fully flipped before proceeding
                if (!OuttakeStates.isArmFlipped()) return;

                // Wait 1 second before releasing the second sample
                addWaitTime(1.0);
                sampleAutonState = SampleAutonState.releaseSecondSample;
                break;

            case releaseSecondSample:
                if (currentWait > getSeconds()) return; // Ensure the arm has had enough time to flip

                // Release the sample
                OuttakeStates.releaseSample();

                // Wait 0.2 seconds before stopping
                addWaitTime(0.2);
                sampleAutonState = SampleAutonState.thirdSampleIntakePath;
                break;

            // THIRD SAMPLE
            // Intake
            case thirdSampleIntakePath:
//                if (drive.isBusy()) return;

                drive.followTrajectorySequenceAsync(trajectories.followThirdSampleIntakePath());
                sampleAutonState = SampleAutonState.startIntakeForThirdSample;
                break;

            case startIntakeForThirdSample:
                // Start intake motor
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                sampleAutonState = SampleAutonState.checkSamplePickupForThirdSample;
                break;

            case checkSamplePickupForThirdSample:
                if (drive.isBusy()) return;

                // Wait until the sample is detected
                if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
                    // Sample is collected, set wait time and prepare to retract
                    addWaitTime(AutonomousConstants.intakeCloseWait);
                    IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
                    sampleAutonState = SampleAutonState.retractOuttakeForThirdSample;
                }
                break;

            case retractOuttakeForThirdSample:
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

                // Fully retract the intake before restarting the cycle
                IntakeStates.setExtendoState(ExtendoStates.retracted);

                // Close the sample claw before restarting the cycle
                OuttakeStates.setSampleClawState(SampleClawStates.closed);

                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.thirdSampleOuttakePath;
                currentWait = 0; // Reset wait time for next use
                break;

            // Outtake
            case thirdSampleOuttakePath:
//                if (drive.isBusy()) return;

                drive.followTrajectorySequenceAsync(trajectories.followThirdSampleOuttakePath());
                sampleAutonState = SampleAutonState.prepareNextCycleForThirdSample;
                break;

            case prepareNextCycleForThirdSample:
                if (drive.isBusy()) return;

                try {
                    Thread.sleep(350); // Wait for 0.35 seconds before restarting the cycle
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }

                // Extend outtake and intake slides, and flip the arm again for the second sample
                OuttakeStates.extendOuttakeAndIntakeAndFlipArm();

                sampleAutonState = SampleAutonState.waitForFlipThirdSample;
                break;

            case waitForFlipThirdSample:
                // Ensure the arm has fully flipped before proceeding
                if (!OuttakeStates.isArmFlipped()) return;

                // Wait 1 second before releasing the second sample
                addWaitTime(1.0);
                sampleAutonState = SampleAutonState.releaseThirdSample;
                break;

            case releaseThirdSample:
                if (currentWait > getSeconds()) return; // Ensure the arm has had enough time to flip

                // Release the sample
                OuttakeStates.releaseSample();

                // Wait 0.2 seconds before stopping
                addWaitTime(0.2);
                sampleAutonState = SampleAutonState.forthSampleIntakePath;
                break;


            // FORTH SAMPLE
            // Intake
            case forthSampleIntakePath:
//                if (drive.isBusy()) return;

                drive.followTrajectorySequenceAsync(trajectories.followForthSampleIntakePath());
                sampleAutonState = SampleAutonState.startIntakeForForthSample;
                break;

            case startIntakeForForthSample:
                // Start intake motor
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                sampleAutonState = SampleAutonState.checkSamplePickupForForthSample;
                break;

            case checkSamplePickupForForthSample:
                if (drive.isBusy()) return;

                // Wait until the sample is detected
                if (IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
                    // Sample is collected, set wait time and prepare to retract
                    addWaitTime(AutonomousConstants.intakeCloseWait);
                    IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
                    sampleAutonState = SampleAutonState.retractOuttakeForForthSample;
                }
                break;

            case retractOuttakeForForthSample:
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

                // Fully retract the intake before restarting the cycle
                IntakeStates.setExtendoState(ExtendoStates.retracted);

                // Close the sample claw before restarting the cycle
                OuttakeStates.setSampleClawState(SampleClawStates.closed);

                // Move to the next state after retraction is complete
                sampleAutonState = SampleAutonState.forthSampleOuttakePath;
                currentWait = 0; // Reset wait time for next use
                break;

            // Outtake
            case forthSampleOuttakePath:
//                if (drive.isBusy()) return;

                drive.followTrajectorySequenceAsync(trajectories.followForthSampleOuttakePath());
                sampleAutonState = SampleAutonState.prepareNextCycleForForthSample;
                break;

            case prepareNextCycleForForthSample:
                if (drive.isBusy()) return;

                try {
                    Thread.sleep(350); // Wait for 0.35 seconds before restarting the cycle
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }

                // Extend outtake and intake slides, and flip the arm again for the second sample
                OuttakeStates.extendOuttakeAndIntakeAndFlipArm();

                sampleAutonState = SampleAutonState.waitForFlipForthSample;
                break;

            case waitForFlipForthSample:
                // Ensure the arm has fully flipped before proceeding
                if (!OuttakeStates.isArmFlipped()) return;

                // Wait 1 second before releasing the second sample
                addWaitTime(1.0);
                sampleAutonState = SampleAutonState.releaseForthSample;
                break;

            case releaseForthSample:
                if (currentWait > getSeconds()) return; // Ensure the arm has had enough time to flip

                // Release the sample
                OuttakeStates.releaseSample();

                // Wait 0.2 seconds before stopping
                addWaitTime(0.2);
                sampleAutonState = SampleAutonState.stop;
                break;

            case stop:
                // Stop execution of the autonomous program
                break;
        }
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }

//    private void autonomousSampleOuttake(TrajectorySequence path) {
//        drive.followTrajectorySequenceAsync(path);
//    }
}
