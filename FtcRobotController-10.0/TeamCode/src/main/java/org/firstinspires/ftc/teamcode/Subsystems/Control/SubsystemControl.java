package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown.DpadDownLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp.DpadUpLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper.LeftBumperLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger.LeftTriggerLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper.RightBumperLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger.RightTriggerLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square.SquareLogic;

public class SubsystemControl {
    private final EdgeDetection edgeDetection;
    private final LeftTriggerLogic leftTriggerLogic;
    private final LeftBumperLogic leftBumperLogic = new LeftBumperLogic();
    private final RightTriggerLogic rightTriggerLogic = new RightTriggerLogic();
    private final RightBumperLogic rightBumperLogic = new RightBumperLogic();
    private final SquareLogic squareLogic = new SquareLogic();
    private final DpadUpLogic dpadUpLogic = new DpadUpLogic();
    private final DpadDownLogic dpadDownLogic = new DpadDownLogic();

    public SubsystemControl(EdgeDetection edgeDetection, SensorControl sensorControl) {
        this.edgeDetection = edgeDetection;
        leftTriggerLogic = new LeftTriggerLogic(sensorControl);
    }

    public void update() {
        updateLogic();
    }

    private void updateLogic(){
        if(edgeDetection.rising(GamepadIndexValues.leftTrigger))
            leftTriggerLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.leftBumper))
            leftBumperLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.rightTrigger))
            rightTriggerLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.rightBumper))
            rightBumperLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.square))
            squareLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.dpadUp))
            dpadUpLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.dpadDown))
            dpadDownLogic.update();

    }
}
