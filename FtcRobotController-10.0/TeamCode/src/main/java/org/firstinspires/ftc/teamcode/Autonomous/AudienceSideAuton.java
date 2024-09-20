package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.AudienceSideTrajectories;
import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.BlueAudienceSideTrajectories;
import org.firstinspires.ftc.teamcode.Autonomous.Trajectories.RedAudienceSideTrajectories;
import org.firstinspires.ftc.teamcode.Camera.AprilTagCameraControl;
import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.AudienceSideAutonState;
import org.firstinspires.ftc.teamcode.Enums.ReleaseServoState;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStateControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeDependencies;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;

public class AudienceSideAuton implements Auton {

    public AudienceSideAutonState audienceSideAutonState = AudienceSideAutonState.goToProp;
    private final SampleMecanumDrive drive;
    private AudienceSideTrajectories trajectories;
    private boolean wasIfCalled = false;
    private boolean wasPositionDetected = false;
    private final ElapsedTime elapsedTime = new ElapsedTime();
    private final IntakeStateControl intakeStateControl;
    private final SlideControl slideControl;
    private double currentWait;
    private final OuttakeServoControl outtakeServoControl;
    private final OuttakeController outtakeController;
    private final AprilTagCameraControl aprilTagCameraControl;

    public AudienceSideAuton(SampleMecanumDrive drive, IntakeStateControl intakeStateControl, OuttakeController outtakeController, AprilTagCameraControl aprilTagCameraControl) {
        this.drive = drive;
        this.intakeStateControl = intakeStateControl;
        OuttakeDependencies outtakeDependencies = outtakeController.getOuttakeDependencies();
        this.slideControl = outtakeDependencies.slideControl;
        this.outtakeServoControl = outtakeDependencies.outtakeServoControl;
        this.outtakeController = outtakeController;
        this.aprilTagCameraControl = aprilTagCameraControl;
    }

    @Override
    public void start() {
        setTrajectorySide();
        outtakeServoControl.releaseServoControl(ReleaseServoState.closeBoth);
        outtakeServoControl.releaseServoControl(ReleaseServoState.openFirst);
        drive.setPoseEstimate(trajectories.getStartPose());
        drive.followTrajectorySequenceAsync(trajectories.goToPixelStack());
    }

    @Override
    public void setTrajectorySide() {
        switch (GlobalVariables.alliance) {

            case Red:
                trajectories = new RedAudienceSideTrajectories(drive, GlobalVariables.propPos);
                break;
            case Blue:
                trajectories = new BlueAudienceSideTrajectories(drive, GlobalVariables.propPos);
                break;
        }
    }

    @Override
    public void run() {
        switch (audienceSideAutonState) {
            case goToProp:
                goToProp();
                break;
            case placePropPixel:
                placePropPixel();
                break;
            case goToPixelStack:
                goToPixelStack();
                break;
            case takePixel:
                takePixel();
                break;
            case pushOutExtraPixels:
                pushOutExtraPixels();
                break;
            case goBackstage:
                goBackstage();
                break;
            case goToWhitePixelPlacement:
                goToWhitePixelPlacement();
                break;
            case placeWhitePixel:
                placeWhitePixel();
                break;
            case goToYellowPixelPlacement:
                goToYellowPixelPlacement();
                break;
            case placeYellowPixel:
                placeYellowPixel();
                break;
            case goBackToStack:
                goBackToStack();
                break;
            case takeWhitePixels:
                takeWhitePixels();
                break;
            case pushOutExtraPixels2:
                pushOutExtraPixels2();
                break;
            case goBackToBackstage:
                goBackToBackstage();
                break;
            case goToWhitePixelsPlacement:
                goToWhitePixelsPlacement();
                break;
            case placeWhitePixels:
                placeWhitePixels();
                break;
            case parkBackstage:
                parkBackstage();
                break;
            case idle:
                break;
        }
    }

    private void goToProp() {
        intakeStateControl.moveDown();
        addWaitTime(AutonomousConstants.propPixelPlaceWait());
        audienceSideAutonState = AudienceSideAutonState.placePropPixel;
    }

    private void placePropPixel() {
        if (currentWait > elapsedTime.seconds()) return;

        intakeStateControl.pushOut(-0.5);


        audienceSideAutonState = AudienceSideAutonState.goToPixelStack;
        addWaitTime(AutonomousConstants.activateIntakeWait);
    }

    private void goToPixelStack() {
        if (currentWait > elapsedTime.seconds()) return;

        if (!wasIfCalled) {
            outtakeServoControl.releaseServoControl(ReleaseServoState.openSecond);
            intakeStateControl.takeIn();
            wasIfCalled = true;
        }

        if (drive.isBusy()) return;

        audienceSideAutonState = AudienceSideAutonState.takePixel;
        addWaitTime(AutonomousConstants.pixelStackWait);
        wasIfCalled = false;
    }

    private void takePixel() {
        if (currentWait > elapsedTime.seconds()) return;

        addWaitTime(AutonomousConstants.pixelStackPushoutWait);
        outtakeServoControl.releaseServoControl(ReleaseServoState.closeBoth);
        intakeStateControl.pushOut();
        audienceSideAutonState = AudienceSideAutonState.pushOutExtraPixels;

    }

    private void pushOutExtraPixels() {
        if (currentWait > elapsedTime.seconds()) return;

        intakeStateControl.stop();
        audienceSideAutonState = AudienceSideAutonState.goBackstage;
        drive.followTrajectorySequenceAsync(trajectories.goBackstage());
        addWaitTime(AutonomousConstants.slideOpenWait);
    }

    private void goBackstage() {
        if (currentWait > elapsedTime.seconds()) return;

        audienceSideAutonState = AudienceSideAutonState.goToWhitePixelPlacement;
        slideControl.setSlideExtensionTarget(AutonomousConstants.audienceSideWhitePixelExtension);
        addWaitTime(1);
        aprilTagCameraControl.startCamera();
    }

    private void goToWhitePixelPlacement() {
        slideControl.updateSlides();
        outtakeController.openServo();
        if (currentWait > elapsedTime.seconds()) return;

        if(!wasPositionDetected)
        {
            Pose2d currentPosition = aprilTagCameraControl.getCurrentPosition();
            if(currentPosition != null){
                drive.setPoseEstimate(currentPosition);
                wasPositionDetected = true;
                //aprilTagCameraControl.pauseCamera();
            }
        }


        if (drive.isBusy()) return;

        audienceSideAutonState = AudienceSideAutonState.placeWhitePixel;
        addWaitTime(AutonomousConstants.whitePixelPlaceWait);
        slideControl.setSlideExtensionTarget(AutonomousConstants.audienceSideYellowPixelExtension);
        outtakeServoControl.releaseServoControl(ReleaseServoState.openFirst);
        wasPositionDetected = false;
    }

    private void placeWhitePixel() {
        slideControl.updateSlides();
        if (currentWait > elapsedTime.seconds()) return;

        slideControl.setSlideExtensionTarget(AutonomousConstants.audienceSideWhitePixelExtension);
        audienceSideAutonState = AudienceSideAutonState.goToYellowPixelPlacement;
        drive.followTrajectorySequenceAsync(trajectories.goToYellowPixelPlacement());
        outtakeServoControl.releaseServoControl(ReleaseServoState.closeBoth);
        outtakeServoControl.releaseServoControl(ReleaseServoState.openSecond);
        addWaitTime(AutonomousConstants.yellowPixelPlaceWait);
    }

    private void goToYellowPixelPlacement() {
        slideControl.updateSlides();

        if (currentWait > elapsedTime.seconds()) return;

        if(!wasIfCalled)
        {
            outtakeServoControl.releaseServoControl(ReleaseServoState.openFirst);
            slideControl.setSlideExtensionTarget(AutonomousConstants.audienceSideYellowPixelExtension);
            wasIfCalled = true;
        }

        if (drive.isBusy()) return;

        wasIfCalled = false;
        audienceSideAutonState = AudienceSideAutonState.placeYellowPixel;
    }

    private void placeYellowPixel() {
        slideControl.updateSlides();

        if (drive.isBusy()) return;

        if(GlobalVariables.alliance == Alliance.Red) {
            audienceSideAutonState = AudienceSideAutonState.goBackToStack;
            drive.setPoseEstimate(trajectories.adjustPosition());
            drive.followTrajectorySequenceAsync(trajectories.goBackToStack());
        }
        else {
            audienceSideAutonState = AudienceSideAutonState.parkBackstage;
            drive.followTrajectorySequenceAsync(trajectories.parkBackstage());
        }
        addWaitTime(AutonomousConstants.closeSlidesWait);
        wasIfCalled = false;
    }

    private void goBackToStack() {
        if(!wasIfCalled)
            slideControl.updateSlides();
        if (currentWait < elapsedTime.seconds() && !wasIfCalled) {
            outtakeController.initialiseStop(); // retract slides
            wasIfCalled = true;
        }
        if(!wasIfCalled)
            return;
        outtakeController.stop();


        audienceSideAutonState = AudienceSideAutonState.takeWhitePixels;
        addWaitTime(AutonomousConstants.intakeSecondStartWait);
        wasIfCalled = false;
    }

    private void takeWhitePixels() {
        if (currentWait > elapsedTime.seconds()) return;
        intakeStateControl.takeIn();

        if (drive.isBusy()) return;
        addWaitTime(AutonomousConstants.pixelStackPushoutWait);
        outtakeServoControl.releaseServoControl(ReleaseServoState.closeBoth);
        intakeStateControl.pushOut();
        audienceSideAutonState = AudienceSideAutonState.pushOutExtraPixels2;
    }

    private void pushOutExtraPixels2() {
        if (currentWait > elapsedTime.seconds()) return;

        intakeStateControl.stop();
        audienceSideAutonState = AudienceSideAutonState.goBackToBackstage;
        drive.followTrajectorySequenceAsync(trajectories.goBackToBackstage());
        addWaitTime(AutonomousConstants.slideOpenWait);
    }

    private void goBackToBackstage() {
        if (currentWait > elapsedTime.seconds()) return;
        audienceSideAutonState = AudienceSideAutonState.goToWhitePixelsPlacement;
        slideControl.setSlideExtensionTarget(AutonomousConstants.audienceSideSecondCycleExtension);
        //aprilTagCameraControl.startCamera();
//        drive.followTrajectorySequenceAsync(trajectories.goToWhitePixelsPlacement());
    }
    private void goToWhitePixelsPlacement() {
        slideControl.updateSlides();
        outtakeController.openServo();

//        if(!wasPositionDetected)
//        {
//            Pose2d currentPosition = aprilTagCameraControl.getCurrentPosition();
//            if(currentPosition != null){
//                drive.setPoseEstimate(currentPosition);
//                wasPositionDetected = true;
//                aprilTagCameraControl.pauseCamera();
//            }
//        }

        if (drive.isBusy()) return;

        audienceSideAutonState = AudienceSideAutonState.placeWhitePixels;
        outtakeServoControl.releaseServoControl(ReleaseServoState.openFirst);
        outtakeServoControl.releaseServoControl(ReleaseServoState.openSecond);
        addWaitTime(AutonomousConstants.whitePixelsPlaceWait);
    }

    private void placeWhitePixels() {
        slideControl.updateSlides();
        if (currentWait > elapsedTime.seconds()) return;
        audienceSideAutonState = AudienceSideAutonState.parkBackstage;
        addWaitTime(AutonomousConstants.closeSlidesWait);
        drive.followTrajectorySequenceAsync(trajectories.parkBackstage());
    }

    private void parkBackstage() {
        if(!wasIfCalled)
            slideControl.updateSlides();
        if (currentWait < elapsedTime.seconds() && !wasIfCalled) {
            outtakeController.initialiseStop(); // retract slides
            wasIfCalled = true;
        }
        if(wasIfCalled && outtakeController.getOuttakeState() != SubsystemState.Idle)
            outtakeController.stop();
        if (drive.isBusy()||outtakeController.getOuttakeState() != SubsystemState.Idle) return;

        //aprilTagCameraControl.stopCamera();
        audienceSideAutonState = AudienceSideAutonState.idle;
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
