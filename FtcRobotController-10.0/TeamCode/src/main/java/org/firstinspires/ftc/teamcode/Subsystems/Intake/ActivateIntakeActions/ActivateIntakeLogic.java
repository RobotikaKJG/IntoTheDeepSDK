package org.firstinspires.ftc.teamcode.Subsystems.Intake.ActivateIntakeActions;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ActivateIntakeLogic {
    private double currentWait = 0;
    private boolean wasIfCalled = false;
    private final SlideLogic slideLogic;

    public ActivateIntakeLogic(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update(){
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
        if(!wasIfCalled){
            addWaitTime(OuttakeConstants.rotateOuttakeArmWait);
            wasIfCalled = true;
        }

        if(currentWait > getSeconds()) return;
        IntakeStates.setActivateIntakeState(ActivateIntakeStates.extendExtendo);
    }

    private void extendExtendo() {
        if(Math.abs(slideLogic.getSlidePosition() - IntakeConstants.ArmClearsIntakeExtension) > IntakeConstants.slideTargetThreshold) return;
        IntakeStates.setActivateIntakeState(ActivateIntakeStates.rotateArmDown);

    }

    private void rotateArmDown() {
        IntakeStates.setActivateIntakeState(ActivateIntakeStates.idle);
    }



    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
