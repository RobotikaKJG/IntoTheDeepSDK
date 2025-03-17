package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SpecimenTrajectories;
import org.firstinspires.ftc.teamcode.Roadrunner.DriveConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
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
            case goToPushSample:
                goToPushSample();
                break;
            case pushSample:
                pushSample();
                break;
            case waitToPush:
                waitToPush();
                break;
            case retractExtendo:
                retractExtendo();
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
        if(drive.isBusy()) return;
        specimenAutonState = SpecimenAutonState.hangFirstSpecimen;
    }

    private void hangFirstSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.clipOn);
            initialised = true;
        }

        if(OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.idle)  return;

        specimenAutonState = SpecimenAutonState.goToPushSample;
        initialised = false;
        addWaitTime(AutonomousConstants.goToPushFirstSampleWait);
        drive.followTrajectorySequenceAsync(trajectories.collectSamples());
    }

    private void goToPushSample() {
        if(currentWait > getSeconds()) return;
        specimenAutonState = SpecimenAutonState.pushSample;
        switch (collectSampleCycleState)
        {
            case firstSample:
                addWaitTime(AutonomousConstants.goToPushFirstSampleWait);
                break;
            case secondSample:
                addWaitTime(AutonomousConstants.goToPushSecondSampleWait);
                break;
            case thirdSample:
                addWaitTime(AutonomousConstants.goToPushThirdSampleWait);
                break;
        }
        addWaitTime(AutonomousConstants.goToPushFirstSampleWait);
        specimenAutonState = SpecimenAutonState.pushSample;
    }

    private void pushSample() {
        switch (collectSampleCycleState)
        {
            case firstSample:
            case secondSample:
                IntakeStates.setExtendoState(ExtendoStates.fullyExtend);

                break;
            case thirdSample:
                break;
        }
        addWaitTime(AutonomousConstants.pushSampleWait);
        specimenAutonState = SpecimenAutonState.waitToPush;

    }
    private void waitToPush(){
        if(currentWait > getSeconds()) return;
        specimenAutonState = SpecimenAutonState.retractExtendo;
        //IntakeStates.setMotorState(IntakeMotorStates.backward);
    }

    private void retractExtendo() {
//        if(initialised)
//            IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
        if(!initialised)
        {
            IntakeStates.setExtendoState(ExtendoStates.retracting);
            initialised = true;
        }


        if(drive.isBusy()) return;

        switch (collectSampleCycleState)
        {
            case firstSample:
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
                drive.followTrajectorySequenceAsync(trajectories.goToTakeSecondSpecimen());
                //collectSampleCycleState = CollectSampleCycleState.secondSample;
                break;
            case secondSample:
                IntakeStates.setExtendoState(ExtendoStates.retracting);
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
                //collectSampleCycleState = CollectSampleCycleState.thirdSample;
                break;
            case thirdSample:
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
                break;
        }
        initialised = false;
    }

    private void goToTakeSpecimen() {
        if(drive.isBusy()) return;

//        switch (specimenCycleState){
//            case secondSpecimen:
//                drive.followTrajectorySequenceAsync(trajectories.hangSecondSpecimen());
//                addWaitTime(AutonomousConstants.goToTakeSecondSpecimenWait);
//                break;
//            case thirdSpecimen:
//                break;
//            case fourthSpecimen:
//                break;
//            case fifthSpecimen:
//                break;
//        }
        specimenAutonState = SpecimenAutonState.takeSpecimen;
    }

    private void takeSpecimen() {
        if(!initialised)
        {
//            OuttakeStates.setTakeSpecimenStates(TakeSpecimenStates.takeSpecimen);
            initialised = true;
        }

//        if(OuttakeStates.getTakeSpecimenStates() != TakeSpecimenStates.idle) return;
        specimenAutonState = SpecimenAutonState.goToPlaceSpecimen;
        switch (specimenCycleState){
            case secondSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangSecondSpecimen());
                break;
            case thirdSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangThirdSpecimen());
                break;
            case fourthSpecimen:
                break;
            case fifthSpecimen:
                break;
        }
        initialised = false;
        addWaitTime(AutonomousConstants.goToPlaceSecondSpecimenWait);
    }

    private void goToPlaceSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
            initialised = true;
        }
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

        if(OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.idle)  return;
        initialised = false;

        switch (specimenCycleState){
            case secondSpecimen:
                specimenCycleState = SpecimenCycleState.thirdSpecimen;
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen; //NOTE, only for testing, will be at 5
                drive.followTrajectorySequenceAsync(trajectories.goToTakeThirdSpecimen());
                break;
            case thirdSpecimen:
                specimenCycleState = SpecimenCycleState.fourthSpecimen;
                specimenAutonState = SpecimenAutonState.extendExtendoForPark; //NOTE, only for testing, will be at 5
                drive.followTrajectorySequenceAsync(trajectories.park());
                break;
            case fourthSpecimen:
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
