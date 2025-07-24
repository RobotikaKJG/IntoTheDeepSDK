package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ActivateOuttakeActions;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.OuttakePivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class ActivateOuttakeControl {
    private ActivateOuttakeStates prevActivateOuttakeStates = ActivateOuttakeStates.idle;

    public void update(){
        if(OuttakeStates.getActivateOuttakeState() != prevActivateOuttakeStates) {
            updateStates();
            prevActivateOuttakeStates = OuttakeStates.getActivateOuttakeState();
        }
    }

    private void updateStates(){
        switch(OuttakeStates.getActivateOuttakeState()){
            case rotateArmUp:
                rotateArmUp();
                break;
            case rotateIntakePivot:
                rotateIntakePivot();
                break;
            case rotateArmDown:
                rotateArmDown();
                break;
            case closeClaw:
                closeClaw();
                break;
            case raiseSlides:
                raiseSlides();
                break;
            case rotateArm:
                rotateArm();
                break;
            case idle:
                break;
        }
    }

    private void rotateArmUp() {
        OuttakeStates.setPivotState(OuttakePivotStates.take);
        OuttakeStates.setArmState(ArmStates.aBitUp);
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private void rotateIntakePivot() {
        IntakeStates.setPivotState(PivotStates.up);
    }

    private void rotateArmDown() {
        OuttakeStates.setArmState(ArmStates.down);
    }

    private void closeClaw() {
        OuttakeStates.setClawState(ClawStates.closed);
    }

    private void raiseSlides() {
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
    }

    private void rotateArm() {
        OuttakeStates.setArmState(ArmStates.up);
        IntakeStates.setPivotState(PivotStates.overSub);
        OuttakeStates.setPivotState(OuttakePivotStates.place);
    }
}
