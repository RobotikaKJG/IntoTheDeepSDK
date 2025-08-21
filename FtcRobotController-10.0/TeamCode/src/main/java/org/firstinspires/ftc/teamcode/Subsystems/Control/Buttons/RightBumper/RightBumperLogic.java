package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;


import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class RightBumperLogic {
    private final RightBumperControl rightBumperControl = new RightBumperControl();

    public void update() {
        if(GlobalVariables.hang)
            hang();
        else
            stepUp();
        return;
    }

    private void completeAction(){
        rightBumperControl.update();
        ButtonStates.setRightBumperState(RightBumperStates.idle);
    }

    private void hang() {
        IntakeStates.setHangState(HangStates.retractSlides);
        completeAction();
    }

    private void stepUp() {
        ButtonStates.setRightBumperState(RightBumperStates.stepUp);
        completeAction();
    }
}
