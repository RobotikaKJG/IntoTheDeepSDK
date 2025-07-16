package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle.CircleLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross.CrossLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareStates;


public class ButtonControl {
    private final EdgeDetection edgeDetection;
    private final CircleLogic circleLogic;
    private final CrossLogic crossLogic = new CrossLogic();
    private final SquareLogic squareLogic = new SquareLogic();


    public ButtonControl(EdgeDetection edgeDetection, SensorControl sensorControl) {
        this.edgeDetection = edgeDetection;
        circleLogic = new CircleLogic(sensorControl);
    }

    public void update() {
        updateLogic();
    }

    private void updateLogic(){

        if(edgeDetection.rising(GamepadIndexValues.circle))
            circleLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.cross))
            crossLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.square))
            squareLogic.update();
    }
}
