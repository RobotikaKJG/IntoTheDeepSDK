package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SampleTrajectories;
import org.firstinspires.ftc.teamcode.Enums.SampleAutonState;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeController;

public class SampleAuton implements Auton{

    private final SampleMecanumDrive drive;
    private SampleAutonState sampleAutonState;
    private final SampleTrajectories trajectories;
    private final ElapsedTime elapsedTime;
    private final OuttakeController outtakeController;
    private final IntakeController intakeController;
    private final SlideLogic intakeSlideLogic;

    private double currentWait;

    public SampleAuton(SampleMecanumDrive drive, ElapsedTime elapsedTime, OuttakeController outtakeController, IntakeController intakeController, SlideLogic intakeSlideLogic) {
        this.drive = drive;
        this.elapsedTime = elapsedTime;
        this.outtakeController = outtakeController;
        this.intakeController = intakeController;
        this.intakeSlideLogic = intakeSlideLogic;

        trajectories = new SampleTrajectories(drive);
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.threeSampleTrajectory());
        addWaitTime(AutonomousConstants.goToBasketWait);
        outtakeController.stepUp();
        outtakeController.stepUp();
        outtakeController.closeButtonActions();
    }

    @Override
    public void run() {
        switch (sampleAutonState) {
            case goToBasket:
                goToBasket();
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
            case placeFirstSample:
                placeFirstSample();
                break;
            case goToSecondSample:
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
        outtakeController.closeButtonActions(); //release
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.placeSample;
    }

    private void placeSample() {
        if(currentWait > elapsedTime.seconds()) return;
        outtakeController.closeButtonActions(); //close outtake
        sampleAutonState = SampleAutonState.goToFirstSample;
    }

    private void goToFirstSample() {
        if(currentWait > elapsedTime.seconds()) return;
        intakeController.toggleIntakePower();
        intakeSlideLogic.setSlideExtensionTarget(AutonomousConstants.takeFirstSampleExtension);
        addWaitTime(AutonomousConstants.takeSampleMaxWait);
        sampleAutonState = SampleAutonState.takeFirstSample;
    }

    private void takeFirstSample() {
        //A loop, very very scary
        while(intakeController.getState() != SubsystemState.Idle || currentWait > elapsedTime.seconds()) {
            intakeController.initialiseStop();
        }
        outtakeController.stepUp();
        outtakeController.stepUp();
        outtakeController.closeButtonActions();
        addWaitTime(AutonomousConstants.goToBasketSecondWait);
        sampleAutonState = SampleAutonState.placeFirstSample;

    }

    private void placeFirstSample() {
        if(currentWait > elapsedTime.seconds()) return;
        outtakeController.closeButtonActions(); //release
        addWaitTime(AutonomousConstants.sampleReleaseWait);
        sampleAutonState = SampleAutonState.idle;
    }

    private void stop() {
        if(currentWait > elapsedTime.seconds()) return;
        outtakeController.closeButtonActions(); //close
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
