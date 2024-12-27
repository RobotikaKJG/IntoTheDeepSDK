package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleAuton implements Auton{

    private final SampleMecanumDrive drive;
    private SampleAutonState sampleAutonState;
    private final SampleTrajectories trajectories;
    private final ElapsedTime elapsedTime;
    private final AutonomousControl autonomousControl;

    private double currentWait;

    public SampleAuton(SampleMecanumDrive drive, ElapsedTime elapsedTime, AutonomousControl autonomousControl) {
        this.drive = drive;
        this.elapsedTime = elapsedTime;
        this.autonomousControl = autonomousControl;

        trajectories = new SampleTrajectories(drive);
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.preloadTrajectory());
        OuttakeStates.setClawState(ClawStates.closed);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
        addWaitTime(AutonomousConstants.goToBasketWait);
        sampleAutonState = SampleAutonState.goToBasket;
    }

    @Override
    public void run() {
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
            case takeSecondSample:
                break;
            case placeSecondSample:
                break;
            case goToThirdSample:
                break;
            case takeThirdSample:
                break;
            case placeThirdSample:
                break;
            case stop:
                stop();
                break;
            case idle:
                break;
        }
    }

    private void goToBasket() {
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.flipArm);
        addWaitTime(AutonomousConstants.flipArmWait);
        sampleAutonState = SampleAutonState.flipArm;
    }

    private void flipArm() {
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.releaseSample);
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeSample;
    }

    private void placeSample() {
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.retractSlides);
        addWaitTime(AutonomousConstants.slideRetractWait);
        sampleAutonState = SampleAutonState.goToFirstSample;
    }

    private void goToFirstSample() {
        if(currentWait > elapsedTime.seconds()) return;
        IntakeStates.setMotorState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.extended);
        addWaitTime(AutonomousConstants.takeSampleMaxWait);
        sampleAutonState = SampleAutonState.takeFirstSample;
    }

    private void takeFirstSample() {
        //A loop, very very scary, NOTE
//        while(currentWait > elapsedTime.seconds() && IntakeStates.getAutoCloseStates() != AutoCloseStates.idle) {
//            autonomousControl.updateSubsystems();
//        }
        if(currentWait > elapsedTime.seconds()) return;
        addWaitTime(AutonomousConstants.intakeCloseWait);
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
        sampleAutonState = SampleAutonState.retractFirstSample;
    }

    private void retractFirstSample(){
        if(currentWait > elapsedTime.seconds()) return;
        addWaitTime(AutonomousConstants.goToBasketSecondWait);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
        sampleAutonState = SampleAutonState.goToPlaceFirstSample;
    }

    private void goToPlaceFirstSample(){
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.flipArm);
        addWaitTime(AutonomousConstants.flipArmWait);
        sampleAutonState = SampleAutonState.flipArmForFirstSample;
    }

    private void flipArmForFirstSample(){
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.releaseSample);
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeFirstSample;
    }

    private void placeFirstSample() {
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.retractSlides);
        addWaitTime(AutonomousConstants.slideRetractWait);
        sampleAutonState = SampleAutonState.stop;
    }

    private void stop() {
        if(currentWait > elapsedTime.seconds()) return;
        sampleAutonState = SampleAutonState.idle;
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}