package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;


import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoPlaceSpec.AutoPlaceStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class LeftBumperLogic {
    private final LeftBumperControl leftBumperControl = new LeftBumperControl();

    public void update() {
        if(outtakeReady()) return;
        if(takeSpecimen()) return;
        if(placeSpecimen()) return;
        if(releaseSpecimen()) return;
        return;
    }

    private boolean outtakeReady() {
        if(OuttakeStates.getArmState() == ArmStates.takeSpecimen) return false;
        if((OuttakeStates.getArmState() == ArmStates.placeSpecimen)) return false;
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

    private boolean placeSpecimen() {
        if(OuttakeStates.getArmState() != ArmStates.placeSpecimen) return false;
        ButtonStates.setLeftBumperState(LeftBumperStates.placeSpecimen);
        completeAction();
        return true;
    }

    private boolean releaseSpecimen(){
        if(OuttakeStates.getAutoPlaceState() != AutoPlaceStates.releaseSpec) return false;
        ButtonStates.setLeftBumperState(LeftBumperStates.releaseSpecimen);
        completeAction();
        return true;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }
}
