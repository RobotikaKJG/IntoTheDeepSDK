package org.firstinspires.ftc.teamcode.Subsystems.Control;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger.LeftTriggerStates;

public class ControlStates {
    private static LeftTriggerStates leftTriggerStates = LeftTriggerStates.idle;

    public static void setAllStates(SubsystemState state) {
        leftTriggerStates = LeftTriggerStates.idle;
    }

    public static LeftTriggerStates getIntakeState() {
        return leftTriggerStates;
    }

    public static void setLeftTriggerState(LeftTriggerStates state) {
        leftTriggerStates = state;
    }
}
