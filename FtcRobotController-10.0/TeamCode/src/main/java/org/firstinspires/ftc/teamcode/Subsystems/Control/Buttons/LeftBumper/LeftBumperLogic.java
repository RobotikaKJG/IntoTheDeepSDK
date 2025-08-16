package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;


import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class LeftBumperLogic {
    private final LeftBumperControl leftBumperControl = new LeftBumperControl();

    public void update() {


        return;
    }

    private boolean outtakeReady() {
        if(OuttakeStates.getArmState() == ArmStates.takeSpecimen) return false;
        ButtonStates.setLeftBumperState(LeftBumperStates.outtakeReady);
        completeAction();
        return true;
    }

    private boolean takeSpecimen() {
        if(OuttakeStates.getArmState() !=  ArmStates.takeSpecimen) return false;
        ButtonStates.setLeftBumperState(LeftBumperStates.takeSpecimen);
        completeAction();
        return true;
    }

    private boolean armUp() {
        completeAction();
        return true;
    }

    private boolean placeSpecimen() {
        completeAction();
        return true;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }
}
