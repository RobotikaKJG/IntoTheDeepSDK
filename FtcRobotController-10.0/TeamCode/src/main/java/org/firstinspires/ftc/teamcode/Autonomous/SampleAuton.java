package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleAuton implements Auton{

    private final SampleMecanumDrive drive;
    private SampleAutonState sampleAutonState;
    private final SampleTrajectories trajectories;
    private int repeats = 0;

    private double currentWait;

    public SampleAuton(SampleMecanumDrive drive) {
        this.drive = drive;

        trajectories = new SampleTrajectories(drive);
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.preloadTrajectory());
        OuttakeStates.setSampleClawState(SampleClawStates.closed);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
        addWaitTime(AutonomousConstants.goToBasketWait);
        sampleAutonState = SampleAutonState.goToBasket;
    }

    @Override
    public void run() {
        //execute(autonState /* interface */);

        // To autonStateInterfaceFactory
        switch (sampleAutonState) {
            case goToBasket:
                goToBasket();
                break;
            case flipArm:
                flipArm();
                break;
            case placeSample:
                placeSample();
                break;
            case goToFirstSample:
                goToFirstSample();
                break;
            case takeFirstSample:
                takeFirstSample();
                break;
            case retractFirstSample:
                retractFirstSample();
                break;
            case goToPlaceFirstSample:
                goToPlaceFirstSample();
                break;
            case flipArmForFirstSample:
                flipArmForFirstSample();
                break;
            case placeFirstSample:
                placeFirstSample();
                break;
            case goToSecondSample:
                goToSecondSample();
                break;
            case takeSecondSample:
                takeSecondSample();
                break;
            case retractSecondSample:
                retractSecondSample();
                break;
            case goToPlaceSecondSample:
                goToPlaceSecondSample();
                break;
            case flipArmForSecondSample:
                flipArmForSecondSample();
                break;
            case placeSecondSample:
                placeSecondSample();
                break;
            case goToThirdSample:
                goToThirdSample();
                break;
            case takeThirdSample:
                takeThirdSample();
                break;
            case retractThirdSample:
                retractThirdSample();
                break;
            case goToPlaceThirdSample:
                goToPlaceThirdSample();
                break;
            case flipArmForThirdSample:
                flipArmForThirdSample();
                break;
            case placeThirdSample:
                placeThirdSample();
                break;
            case stop:
                stop();
                break;
            case idle:
                break;
        }
    }


    private void goToBasket() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.flipArm);
        addWaitTime(AutonomousConstants.flipArmFirstWait);
        sampleAutonState = SampleAutonState.flipArm;
    }

    private void flipArm() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.releaseSample);
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeSample;
    }

    private void placeSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.retractSlides);
        addWaitTime(AutonomousConstants.slideRetractWait);
        sampleAutonState = SampleAutonState.goToFirstSample;
    }

    private void goToFirstSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setExtendoState(ExtendoStates.stepUp);

        if(repeats > 2) {
            IntakeStates.setExtendoState(ExtendoStates.stepUp);
            addWaitTime(AutonomousConstants.takeSampleMaxWait);
            IntakeStates.setMotorState(IntakeMotorStates.forward);
            sampleAutonState = SampleAutonState.takeFirstSample;
            repeats = 0;
        }
        else
            repeats++;
    }

    private void takeFirstSample() {
        //A loop, very very scary, NOTE
//        while(currentWait > getseconds() && IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
//            autonomousControl.updateSubsystems();
//        }
        if(currentWait > getSeconds()) return;
        addWaitTime(AutonomousConstants.intakeCloseWait);
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
        sampleAutonState = SampleAutonState.retractFirstSample;
    }

    private void retractFirstSample(){
        if(currentWait > getSeconds()) return;
        addWaitTime(AutonomousConstants.goToBasketSecondWait);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
        sampleAutonState = SampleAutonState.goToPlaceFirstSample;
    }

    private void goToPlaceFirstSample(){
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.flipArm);
        addWaitTime(AutonomousConstants.flipArmWait);
        sampleAutonState = SampleAutonState.flipArmForFirstSample;
    }

    private void flipArmForFirstSample(){
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.releaseSample);
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeFirstSample;
    }

    private void placeFirstSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.retractSlides);
        addWaitTime(AutonomousConstants.slideRetractWait);
        sampleAutonState = SampleAutonState.goToSecondSample;
    }

    private void goToSecondSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setExtendoState(ExtendoStates.stepUp);

        if(repeats > 2) {
            IntakeStates.setExtendoState(ExtendoStates.stepUp);
            addWaitTime(AutonomousConstants.takeSampleMaxWait);
            IntakeStates.setMotorState(IntakeMotorStates.forward);
            sampleAutonState = SampleAutonState.takeSecondSample;
            repeats = 0;
        }
        else
            repeats++;
    }

    private void takeSecondSample() {
        if(currentWait > getSeconds()) return;
        addWaitTime(AutonomousConstants.intakeCloseWait);
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
        sampleAutonState = SampleAutonState.retractSecondSample;
    }

    private void retractSecondSample() {
        if(currentWait > getSeconds()) return;
        addWaitTime(AutonomousConstants.goToBasketSecondWait);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
        sampleAutonState = SampleAutonState.goToPlaceSecondSample;
    }

    private void goToPlaceSecondSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.flipArm);
        addWaitTime(AutonomousConstants.flipArmWait);
        sampleAutonState = SampleAutonState.flipArmForSecondSample;
    }

    private void flipArmForSecondSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.releaseSample);
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeSecondSample;
    }

    private void placeSecondSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.retractSlides);
        addWaitTime(AutonomousConstants.slideRetractWait);
        sampleAutonState = SampleAutonState.goToThirdSample;
    }

    private void goToThirdSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setExtendoState(ExtendoStates.stepUp);

        if(repeats > 2) {
            IntakeStates.setExtendoState(ExtendoStates.stepUp);
            addWaitTime(AutonomousConstants.takeSampleMaxWait);
            IntakeStates.setMotorState(IntakeMotorStates.forward);
            sampleAutonState = SampleAutonState.takeThirdSample;
        }
        else
            repeats++;
    }

    private void takeThirdSample() {
        if(currentWait > getSeconds()) return;
        addWaitTime(AutonomousConstants.intakeCloseWait);
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
        sampleAutonState = SampleAutonState.retractThirdSample;
    }

    private void retractThirdSample() {
        if(currentWait > getSeconds()) return;
        addWaitTime(AutonomousConstants.goToBasketThirdWait);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
        sampleAutonState = SampleAutonState.goToPlaceThirdSample;
    }

    private void goToPlaceThirdSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.flipArm);
        addWaitTime(AutonomousConstants.flipArmWait);
        sampleAutonState = SampleAutonState.flipArmForThirdSample;
    }

    private void flipArmForThirdSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.releaseSample);
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeThirdSample;
    }

    private void placeThirdSample() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.retractSlides);
        addWaitTime(AutonomousConstants.slideRetractWait);
        sampleAutonState = SampleAutonState.stop;
    }

    private void stop() {
        if(currentWait > getSeconds()) return;
        sampleAutonState = SampleAutonState.idle;
    }

//    private void execute(AutonStateInterface state)
//    {
//        if(currentWait > getSeconds()) return;
//
//        state.execute();
//
//        // execute viduj V
//        OuttakeStates.setReleaseButtonState(state.ButtonState);
//
//        addWaitTime(state.WaitTime);
//        sampleAutonState = state.AutonState;
//    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}