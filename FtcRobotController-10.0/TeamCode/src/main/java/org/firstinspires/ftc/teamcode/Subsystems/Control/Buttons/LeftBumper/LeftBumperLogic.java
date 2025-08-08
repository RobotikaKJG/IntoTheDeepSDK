package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;


import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;

public class LeftBumperLogic {
    private final LeftBumperControl leftBumperControl = new LeftBumperControl();

    public void update() {
        return;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }
}
