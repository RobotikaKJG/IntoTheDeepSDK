package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SpecimenTrajectories;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions.DropSampleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TakeSpecimen.TakeSpecimenStates;

public class SpecimenAuton implements Auton{
    private final SampleMecanumDrive drive;
    private final SpecimenTrajectories trajectories;
    private SpecimenAutonState specimenAutonState = SpecimenAutonState.goToHangFirstSpecimen;
    private double currentWait = 0;
    private boolean initialised = false;
    private CollectSampleCycleState collectSampleCycleState = CollectSampleCycleState.firstSample;
    private SpecimenCycleState specimenCycleState = SpecimenCycleState.secondSpecimen;

    public SpecimenAuton(SampleMecanumDrive drive) {
        this.drive = drive;

        trajectories = new SpecimenTrajectories(drive);
    }

    @Override
    public void start() {
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.hangFirstSpecimen());
        OuttakeStates.setSpecimenClawState(SpecimenClawStates.closed);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
        addWaitTime(AutonomousConstants.placeFirstWait);
//        DriveConstants.MAX_VEL = 75;//68;
//        DriveConstants.MAX_ANG_VEL = 20;
//        DriveConstants.MAX_ANG_ACCEL = 10;
    }

    @Override
    public void run() {
        switch (specimenAutonState)
        {
            case goToHangFirstSpecimen:
                goToHangFirstSpecimen();
                break;
            case hangFirstSpecimen:
                hangFirstSpecimen();
                break;
            case goToTakeSpecimen:
                goToTakeSpecimen();
                break;
            case takeSpecimen:
                takeSpecimen();
                break;
            case goToPlaceSpecimen:
                goToPlaceSpecimen();
                break;
            case placeSpecimen:
                placeSpecimen();
                break;
            case extendExtendoForPark:
                extendExtendoForPark();
                break;
            case stop:
                stop();
                break;
            case idle:
                break;
        }
    }

    private void goToHangFirstSpecimen() {
        if(currentWait > getSeconds()) return;
        specimenAutonState = SpecimenAutonState.hangFirstSpecimen;
    }

    private void hangFirstSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.clipOn);
            OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
            OuttakeStates.setSampleLockState(SampleLockStates.open);
            initialised = true;
        }

        if(OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.close)  return;


        initialised = false;
        addWaitTime(AutonomousConstants.goToTakeSampleWait);
        drive.followTrajectorySequenceAsync(trajectories.collectFirstSample());
        specimenAutonState = SpecimenAutonState.goToTakeSpecimen;//goToTakeSpecimen;
    }


    private void goToTakeSpecimen() {
        if(drive.isBusy()) return;
        specimenAutonState = SpecimenAutonState.takeSpecimen;
    }

    private void takeSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setTakeSpecimenStates(TakeSpecimenStates.takeSpecimen);
            initialised = true;
        }

        if(OuttakeStates.getTakeSpecimenStates() != TakeSpecimenStates.idle) return;
        specimenAutonState = SpecimenAutonState.goToPlaceSpecimen;
        switch (specimenCycleState){
            case secondSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangSecondSpecimen());
//                addWaitTime(3);
                break;
            case thirdSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangThirdSpecimen());
//                addWaitTime(2.5);
                break;
            case fourthSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangFourthSpecimen());
//                addWaitTime(2.5);
                break;
            case fifthSpecimen:
                break;
        }
        initialised = false;
//        addWaitTime(AutonomousConstants.goToPlaceSecondSpecimenWait);
    }

    private void goToPlaceSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
            initialised = true;
        }
//        if(currentWait > getSeconds()) return;
        if(drive.isBusy()) return;

        initialised = false;
        specimenAutonState = SpecimenAutonState.placeSpecimen;
    }

    private void placeSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.clipOn);
            initialised = true;
        }

        if(OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.close)  return;
        initialised = false;

        switch (specimenCycleState){
            case secondSpecimen:
                specimenCycleState = SpecimenCycleState.thirdSpecimen;
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
                drive.followTrajectorySequenceAsync(trajectories.goToTakeThirdSpecimen());
                break;
            case thirdSpecimen:
                specimenCycleState = SpecimenCycleState.fourthSpecimen;
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
                drive.followTrajectorySequenceAsync(trajectories.goToTakeFourthSpecimen());
                break;
            case fourthSpecimen:
                specimenCycleState = SpecimenCycleState.fifthSpecimen;
                specimenAutonState = SpecimenAutonState.extendExtendoForPark;
                drive.followTrajectorySequenceAsync(trajectories.park());
                break;
            case fifthSpecimen:
                break;
        }
    }

    private void extendExtendoForPark() {

    }

    private void stop() {
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
