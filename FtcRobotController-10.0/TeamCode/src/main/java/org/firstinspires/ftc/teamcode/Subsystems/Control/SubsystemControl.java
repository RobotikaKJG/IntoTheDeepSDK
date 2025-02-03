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
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle.CircleLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross.CrossLogic;

public class SubsystemControl {
    private final EdgeDetection edgeDetection;
    private final LeftTriggerLogic leftTriggerLogic;
    private final LeftBumperLogic leftBumperLogic = new LeftBumperLogic();
    private final RightTriggerLogic rightTriggerLogic = new RightTriggerLogic();
    private final RightBumperLogic rightBumperLogic;
    private final SquareLogic squareLogic = new SquareLogic();
    private final DpadUpLogic dpadUpLogic = new DpadUpLogic();
    private final DpadDownLogic dpadDownLogic = new DpadDownLogic();
    private final CircleLogic circleLogic;
    private final CrossLogic crossLogic = new CrossLogic();

    public SubsystemControl(EdgeDetection edgeDetection, SensorControl sensorControl) {
        this.edgeDetection = edgeDetection;
        leftTriggerLogic = new LeftTriggerLogic(sensorControl);
        rightBumperLogic = new RightBumperLogic(sensorControl);
        circleLogic = new CircleLogic(sensorControl);
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

        if(edgeDetection.rising(GamepadIndexValues.circle))
            circleLogic.update();

        if(edgeDetection.rising(GamepadIndexValues.cross))
            crossLogic.update();
    }
}
