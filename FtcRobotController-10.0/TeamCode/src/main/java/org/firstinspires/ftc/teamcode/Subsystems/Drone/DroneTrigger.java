package org.firstinspires.ftc.teamcode.Subsystems.Drone;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemTrigger;

public class DroneTrigger implements SubsystemTrigger {

    private static final GamepadIndexValues trigger = GamepadIndexValues.circle;
    @Override
    public GamepadIndexValues getTrigger() {
        return trigger;
    }
}
