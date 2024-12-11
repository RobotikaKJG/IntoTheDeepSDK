package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.DpadDown.DpadDownLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.DpadUp.DpadUpLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftBumper.LeftBumperLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger.LeftTriggerLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.RightBumper.RightBumperLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.RightTrigger.RightTriggerLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Square.SquareLogic;

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
