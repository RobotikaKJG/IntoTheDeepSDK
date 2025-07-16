package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle.CircleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross.CrossStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareStates;

public class ButtonStates {
    private static CrossStates crossStates = CrossStates.idle;
    private static CircleStates circleStates = CircleStates.idle;
    private static SquareStates squareStates = SquareStates.idle;

    public static void setInitialStates(){
        crossStates = CrossStates.idle;
        circleStates = CircleStates.idle;
        squareStates = SquareStates.idle;
    }

    public static CrossStates getCrossState() {
        return crossStates;
    }

    public static void setCrossState(CrossStates crossStates) {
        ButtonStates.crossStates = crossStates;
    }


    public static CircleStates getCircleState() {
        return circleStates;
    }

    public static void setCircleState(CircleStates circleStates) {
        ButtonStates.circleStates = circleStates;
    }

    public static SquareStates getSquareState() {
        return squareStates;
    }

    public static void setSquareState(SquareStates squareStates) {
        ButtonStates.squareStates = squareStates;
    }
}
