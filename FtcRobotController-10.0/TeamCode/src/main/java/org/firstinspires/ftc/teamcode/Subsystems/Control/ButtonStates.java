package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger.RightTriggerStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper.RightBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareStates;

public class ButtonStates {
    private static RightBumperStates rightBumperStates = RightBumperStates.idle;
    private static RightTriggerStates rightTriggerStates = RightTriggerStates.idle;
    private static SquareStates squareStates = SquareStates.idle;

    public static void setInitialStates(){
        rightBumperStates = RightBumperStates.idle;
        rightTriggerStates = RightTriggerStates.idle;
        squareStates = SquareStates.idle;
    }

    public static RightBumperStates getRightBumperState() {
        return rightBumperStates;
    }

    public static void setRightBumperState(RightBumperStates crossStates) {
        ButtonStates.rightBumperStates = crossStates;
    }


    public static RightTriggerStates getRightTriggerState() {
        return rightTriggerStates;
    }

    public static void setRightTriggerState(RightTriggerStates rightTriggerStates) {
        ButtonStates.rightTriggerStates = rightTriggerStates;
    }

    public static SquareStates getSquareState() {
        return squareStates;
    }

    public static void setSquareState(SquareStates squareStates) {
        ButtonStates.squareStates = squareStates;
    }
}
