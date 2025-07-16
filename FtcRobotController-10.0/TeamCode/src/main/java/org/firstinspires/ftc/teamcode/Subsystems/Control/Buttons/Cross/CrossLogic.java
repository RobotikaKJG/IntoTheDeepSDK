package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross;


import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle.CircleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class CrossLogic {
    private final CrossControl crossControl = new CrossControl();

    public void update() {
        stepUp();
        return;
    }

    private void completeAction(){
        crossControl.update();
        ButtonStates.setCrossState(CrossStates.idle);
    }

    private void stepUp() {
        ButtonStates.setCrossState(CrossStates.stepUp);
        completeAction();
    }
}
