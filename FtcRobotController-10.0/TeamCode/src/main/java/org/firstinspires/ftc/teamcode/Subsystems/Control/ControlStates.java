package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle.CircleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross.CrossStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown.DpadDownStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight.DpadRightStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp.DpadUpStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper.LeftBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger.LeftTriggerStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper.RightBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger.RightTriggerStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareStates;

public class ControlStates {
    private static LeftTriggerStates leftTriggerStates = LeftTriggerStates.idle;
    private static LeftBumperStates leftBumperStates = LeftBumperStates.idle;
    private static RightTriggerStates rightTriggerStates = RightTriggerStates.idle;
    private static RightBumperStates rightBumperStates = RightBumperStates.idle;
    private static SquareStates squareStates = SquareStates.idle;
    private static DpadUpStates dpadUpStates = DpadUpStates.idle;
    private static DpadDownStates dpadDownStates = DpadDownStates.idle;
    private static CrossStates crossStates = CrossStates.idle;
    private static CircleStates circleStates = CircleStates.idle;
    private static DpadRightStates dpadRightStates = DpadRightStates.idle;

    public static void setInitialStates(){
        leftTriggerStates = LeftTriggerStates.idle;
        leftBumperStates = LeftBumperStates.idle;
        rightTriggerStates = RightTriggerStates.idle;
        rightBumperStates = RightBumperStates.idle;
        squareStates = SquareStates.idle;
        dpadUpStates = DpadUpStates.idle;
        dpadDownStates = DpadDownStates.idle;
        crossStates = CrossStates.idle;
        circleStates = CircleStates.idle;
        dpadRightStates = DpadRightStates.idle;
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


    public static CrossStates getCrossState() {
        return crossStates;
    }

    public static void setCrossState(CrossStates crossStates) {
        ControlStates.crossStates = crossStates;
    }


    public static CircleStates getCircleState() {
        return circleStates;
    }

    public static void setCircleState(CircleStates circleStates) {
        ControlStates.circleStates = circleStates;
    }


    public static DpadRightStates getDpadRightState() {
        return dpadRightStates;
    }

    public static void setDpadRightState(DpadRightStates state) {
        dpadRightStates = state;
    }
}
