package org.firstinspires.ftc.teamcode.Subsystems.Control.Square;


import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class SquareLogic {
    private final SquareControl squareControl = new SquareControl();

    public void update() {
        if(iterateOuttakeArmStates()) return;
        if(manualToggleClaw()) return;
    }

    private void completeAction(){
        squareControl.update();
        ControlStates.setSquareState(SquareStates.idle);
    }

    private boolean iterateOuttakeArmStates() {
        if(!outtakeActive()) return false;
        ControlStates.setSquareState(SquareStates.iterateOuttakeArmStates);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean manualToggleClaw()
    {
        if(outtakeActive()) return false;
        ControlStates.setSquareState(SquareStates.manualToggleClaw);
        completeAction();
        return true;
    }
}
