package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger.LeftTriggerControl;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger.LeftTriggerLogic;

public class SubsystemControl {
    private final EdgeDetection edgeDetection;
    LeftTriggerLogic leftTriggerLogic;
    LeftTriggerControl leftTriggerControl;

    public SubsystemControl(EdgeDetection edgeDetection) {
        this.edgeDetection = edgeDetection;
    }

    public void update() {
        updateLogic();
        updateControl();
    }

    private void updateLogic(){
        if (edgeDetection.rising(GamepadIndexValues.leftTrigger)) {
            leftTriggerLogic.update();
        }
    }

    private void updateControl(){
        leftTriggerControl.update();
    }
}
