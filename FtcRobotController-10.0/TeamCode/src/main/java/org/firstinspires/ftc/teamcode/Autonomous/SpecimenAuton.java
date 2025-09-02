package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.SpecimenTrajectories;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoPlaceSpec.AutoPlaceStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TurnServo.TurnServoStates;

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
        OuttakeStates.setArmState(ArmStates.placeSpecimen);
        OuttakeStates.setTurnServoState(TurnServoStates.placePos);
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
            case goToTakeFirstSample:
                goToTakeFirstSample();
                break;
            case extendSlidesForFirstSample:
                extendSlidesForFirstSample();
                break;
            case startIntakeForFirstSample:
                startIntakeForFirstSample();
                break;
            case checkFirstSamplePickup:
                checkFirstSamplePickup();
                break;
            case goToEjectFirstSample:
                goToEjectFirstSample();
                break;
            case ejectFirstSample:
                ejectFirstSample();
                break;
            case goToTakeSecondSample:
                goToTakeSecondSample();
                break;
            case startIntakeForSecondSample:
                startIntakeForSecondSample();
                break;
            case checkSecondSamplePickup:
                checkSecondSamplePickup();
                break;
            case goToEjectSecondSample:
                goToEjectSecondSample();
                break;
            case ejectSecondSample:
                ejectSecondSample();
                break;
            case goToTakeThirdSample:
                goToTakeThirdSample();
                break;
            case startIntakeForThirdSample:
                startIntakeForThirdSample();
                break;
            case checkThirdSamplePickup:
                checkThirdSamplePickup();
                break;
            case goToEjectThirdSample:
                goToEjectThirdSample();
                break;
            case ejectThirdSample:
                ejectThirdSample();
                break;
            case goToTakeSpecimen:
                goToTakeSpecimen();
                break;
            case takeSpecimen:
                takeSpecimen();
                break;
            case closeClaw:
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
        addWaitTime(1.6);
            specimenAutonState = SpecimenAutonState.hangFirstSpecimen;
    }

    private void hangFirstSpecimen() {
        if(currentWait > getSeconds()) return;

        OuttakeStates.setAutoPlaceState(AutoPlaceStates.activate);

        specimenAutonState = SpecimenAutonState.goToTakeFirstSample;//goToTakeSpecimen;
    }

    private void goToTakeFirstSample() {
        addWaitTime(AutonomousConstants.goToTakeFirstSampleWait);
        drive.followTrajectorySequenceAsync(trajectories.collectFirstSample());
        specimenAutonState = SpecimenAutonState.extendSlidesForFirstSample;
    }

    private void extendSlidesForFirstSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setPivotState(PivotStates.upSlightly);
        IntakeStates.setArmSlideState(ArmSlideStates.fullyExtend);
        addWaitTime(AutonomousConstants.intakeFirstStartWait);
        specimenAutonState = SpecimenAutonState.startIntakeForFirstSample;

    }

    private void startIntakeForFirstSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setLatchState(LatchStates.closed);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        IntakeStates.setPivotState(PivotStates.down);
        IntakeStates.setAutoCloseState(AutoCloseStates.checkColor);

        addWaitTime(AutonomousConstants.maxIntakeWait);
        specimenAutonState = SpecimenAutonState.checkFirstSamplePickup;

    }

    private void checkFirstSamplePickup() {
        IntakeStates.setLatchState(LatchStates.closed);
        System.out.println("1. " + IntakeStates.getAutoCloseState());
        if(IntakeStates.getAutoCloseState() != AutoCloseStates.idle && currentWait > getSeconds()) return;
        IntakeStates.setPivotState(PivotStates.upSlightly);
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        specimenAutonState = SpecimenAutonState.goToEjectFirstSample;

    }

    private void goToEjectFirstSample() {
        addWaitTime(AutonomousConstants.goToEjectFirstSampleWait);
        drive.followTrajectorySequenceAsync(trajectories.dropFirstCollectSecond());
        specimenAutonState = SpecimenAutonState.ejectFirstSample;

    }

    private void ejectFirstSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setLatchState(LatchStates.open);
        IntakeStates.setMotorState(IntakeMotorStates.backward);
        specimenAutonState = SpecimenAutonState.goToTakeSecondSample;
    }

    private void goToTakeSecondSample() {
        addWaitTime(AutonomousConstants.goToTakeSecondSampleWait);
//        IntakeStates.setMotorState(IntakeMotorStates.idle);
        specimenAutonState = SpecimenAutonState.startIntakeForSecondSample;
    }

    private void startIntakeForSecondSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setLatchState(LatchStates.closed);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        IntakeStates.setPivotState(PivotStates.down);
        IntakeStates.setAutoCloseState(AutoCloseStates.checkColor);

        addWaitTime(AutonomousConstants.maxIntakeWait);
        specimenAutonState = SpecimenAutonState.checkSecondSamplePickup;

    }

    private void checkSecondSamplePickup() {
        System.out.println("2. " + IntakeStates.getAutoCloseState());

        if(IntakeStates.getAutoCloseState() != AutoCloseStates.idle && currentWait > getSeconds()) return;
        IntakeStates.setPivotState(PivotStates.upSlightly);
        IntakeStates.setArmSlideState(ArmSlideStates.halfExtend);
        specimenAutonState = SpecimenAutonState.goToEjectSecondSample;

    }

    private void goToEjectSecondSample() {
        addWaitTime(AutonomousConstants.goToEjectSecondSampleWait);
        drive.followTrajectorySequenceAsync(trajectories.dropSecondCollectThird());
        specimenAutonState = SpecimenAutonState.ejectSecondSample;

    }

    private void ejectSecondSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setLatchState(LatchStates.open);
        IntakeStates.setMotorState(IntakeMotorStates.backward);
        specimenAutonState = SpecimenAutonState.goToTakeThirdSample;
    }

    private void goToTakeThirdSample() {
        addWaitTime(AutonomousConstants.goToTakeThirdSampleWait);
        specimenAutonState = SpecimenAutonState.startIntakeForThirdSample;
    }

    private void startIntakeForThirdSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setLatchState(LatchStates.closed);
        IntakeStates.setArmSlideState(ArmSlideStates.fullyExtend);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        IntakeStates.setPivotState(PivotStates.down);
        IntakeStates.setAutoCloseState(AutoCloseStates.checkColor);

        addWaitTime(AutonomousConstants.maxIntakeWait);
        specimenAutonState = SpecimenAutonState.checkThirdSamplePickup;

    }

    private void checkThirdSamplePickup() {
        System.out.println("3. " + IntakeStates.getAutoCloseState());
        if(IntakeStates.getAutoCloseState() != AutoCloseStates.idle && currentWait > getSeconds()) return;
        IntakeStates.setPivotState(PivotStates.upSlightly);
        IntakeStates.setAutoCloseState(AutoCloseStates.waitToRetract);
        specimenAutonState = SpecimenAutonState.goToEjectThirdSample;

    }

    private void goToEjectThirdSample() {
        addWaitTime(AutonomousConstants.goToEjectThirdSampleWait);
        drive.followTrajectorySequenceAsync(trajectories.goBack());
        specimenAutonState = SpecimenAutonState.ejectThirdSample;


    }

    private void ejectThirdSample() {
        System.out.println("slides " + IntakeStates.getArmSlideState());

        if(currentWait > getSeconds()) return;
        IntakeStates.setAutoCloseState(AutoCloseStates.openLatch);
        OuttakeStates.setArmState(ArmStates.takeSpecimen);
        specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
        System.out.println(IntakeStates.getLatchState());

    }


    private void goToTakeSpecimen() {
        System.out.println(IntakeStates.getLatchState());
        if(drive.isBusy()) return;
        specimenAutonState = SpecimenAutonState.takeSpecimen;
    }

    private void takeSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setSpecimenClawState(SpecimenClawStates.freeMove);
            initialised = true;
        }

//        if(OuttakeStates.getTakeSpecimenStates() != TakeSpecimenStates.idle) return;
        specimenAutonState = SpecimenAutonState.goToPlaceSpecimen;
        switch (specimenCycleState){
            case secondSpecimen:
//                drive.setPoseEstimate(new Pose2d(50,-64,Math.toRadians(90)));
                drive.followTrajectorySequenceAsync(trajectories.hangSecondSpecimen());
                addWaitTime(3.7);
                break;
            case thirdSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangThirdSpecimen());
                addWaitTime(1.7);
                break;
            case fourthSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangFourthSpecimen());
                addWaitTime(1.7);
                break;
            case fifthSpecimen:
                drive.followTrajectorySequenceAsync(trajectories.hangFifthSpecimen());
                addWaitTime(1.7);
                break;
        }
        initialised = false;
        addWaitTime(AutonomousConstants.goToPlaceSecondSpecimenWait);
    }

    private void goToPlaceSpecimen() {
//        if(!initialised)
//        {
//            OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
//            initialised = true;
//        }
        if(currentWait > getSeconds()) return;
//        if(drive.isBusy()) return;

        initialised = false;
        specimenAutonState = SpecimenAutonState.placeSpecimen;
    }

    private void placeSpecimen() {
        if(!initialised)
        {
            OuttakeStates.setAutoPlaceState(AutoPlaceStates.activate);
            initialised = true;
        }
        System.out.println(OuttakeStates.getAutoPlaceState());

//
//        if(OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.release)  return;
//        initialised = false;

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
                specimenAutonState = SpecimenAutonState.goToTakeSpecimen;
                drive.followTrajectorySequenceAsync(trajectories.goToTakeFifthSpecimen());
                break;
            case fifthSpecimen:
//                specimenAutonState = SpecimenAutonState.extendExtendoForPark;
                drive.followTrajectorySequenceAsync(trajectories.park());
                break;
        }
        initialised = false;
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
