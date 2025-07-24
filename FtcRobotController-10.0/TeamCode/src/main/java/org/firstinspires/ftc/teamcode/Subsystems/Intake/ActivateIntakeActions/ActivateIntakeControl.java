package org.firstinspires.ftc.teamcode.Subsystems.Intake.ActivateIntakeActions;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.OuttakePivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class ActivateIntakeControl {
    private ActivateIntakeStates prevActivateIntakeStates = ActivateIntakeStates.idle;

    public void update(){
        if(IntakeStates.getActivateIntakeState() != prevActivateIntakeStates) {
            updateStates();
            prevActivateIntakeStates = IntakeStates.getActivateIntakeState();
        }
    }

    private void updateStates(){
        switch(IntakeStates.getActivateIntakeState()){
            case rotateArmUp:
                rotateArmUp();
                break;
            case extendExtendo:
                extendExtendo();
                break;
            case rotateArmDown:
                rotateArmDown();
                break;
            case idle:
                break;
        }
    }

    private void rotateArmUp() {
        OuttakeStates.setArmState(ArmStates.aBitUp);
    }

    private void extendExtendo() {
        IntakeStates.setExtendoState(ExtendoStates.stepUp);
    }

    private void rotateArmDown() {
        OuttakeStates.setArmState(ArmStates.down);
    }


}
