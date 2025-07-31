package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger.RightTriggerLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper.RightBumperLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareLogic;


public class ButtonControl {
    private final EdgeDetection edgeDetection;
    private final RightTriggerLogic rightTriggerLogic;
    private final RightBumperLogic crossLogic = new RightBumperLogic();
    private final SquareLogic squareLogic = new SquareLogic();


    public ButtonControl(EdgeDetection edgeDetection, SensorControl sensorControl) {
        this.edgeDetection = edgeDetection;
        rightTriggerLogic = new RightTriggerLogic(sensorControl);
    }

    public void update() {
        updateLogic();
    }

    private void updateLogic(){

        if(edgeDetection.rising(GamepadIndexValues.rightTrigger))
            rightTriggerLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.rightBumper))
            crossLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.square))
            squareLogic.update();
    }
}
