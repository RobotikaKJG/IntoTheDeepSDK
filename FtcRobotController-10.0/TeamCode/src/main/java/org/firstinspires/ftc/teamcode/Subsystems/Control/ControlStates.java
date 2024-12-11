package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Subsystems.Control.DpadDown.DpadDownStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.DpadUp.DpadUpStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftBumper.LeftBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger.LeftTriggerStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.RightBumper.RightBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.RightTrigger.RightTriggerStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Square.SquareStates;

public class ControlStates {
    private static LeftTriggerStates leftTriggerStates = LeftTriggerStates.idle;
    private static LeftBumperStates leftBumperStates = LeftBumperStates.idle;
    private static RightTriggerStates rightTriggerStates = RightTriggerStates.idle;
    private static RightBumperStates rightBumperStates = RightBumperStates.idle;
    private static SquareStates squareStates = SquareStates.idle;
    private static DpadUpStates dpadUpStates = DpadUpStates.idle;
    private static DpadDownStates dpadDownStates = DpadDownStates.idle;

    public static void setInitialStates(){
        leftTriggerStates = LeftTriggerStates.idle;
        leftBumperStates = LeftBumperStates.idle;
        rightTriggerStates = RightTriggerStates.idle;
        rightBumperStates = RightBumperStates.idle;
        squareStates = SquareStates.idle;
        dpadUpStates = DpadUpStates.idle;
        dpadDownStates = DpadDownStates.idle;
    }


    public static LeftTriggerStates getLeftTriggerState() {
        return leftTriggerStates;
    }

    public static void setLeftTriggerState(LeftTriggerStates state) {
        leftTriggerStates = state;
    }


    public static LeftBumperStates getLeftBumperState() {
        return leftBumperStates;
    }

    public static void setLeftBumperState(LeftBumperStates state) {
        leftBumperStates = state;
    }


    public static RightTriggerStates getRightTriggerState() {
        return rightTriggerStates;
    }

    public static void setRightTriggerState(RightTriggerStates state) {
        rightTriggerStates = state;
    }


    public static RightBumperStates getRightBumperState() {
        return rightBumperStates;
    }

    public static void setRightBumperState(RightBumperStates state) {
        rightBumperStates = state;
    }


    public static SquareStates getSquareState() {
        return squareStates;
    }

    public static void setSquareState(SquareStates state) {
        squareStates = state;
    }


    public static DpadUpStates getDpadUpState() {
        return dpadUpStates;
    }

    public static void setDpadUpState(DpadUpStates state) {
        dpadUpStates = state;
    }

    public static DpadDownStates getDpadDownState() {
        return dpadDownStates;
    }

    public static void setDpadDownState(DpadDownStates state) {
        dpadDownStates = state;
    }
}
