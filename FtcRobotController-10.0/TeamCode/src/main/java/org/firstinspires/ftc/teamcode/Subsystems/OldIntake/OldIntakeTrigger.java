package org.firstinspires.ftc.teamcode.Subsystems.OldIntake;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemTrigger;

public class OldIntakeTrigger implements SubsystemTrigger {

    private static final GamepadIndexValues trigger = GamepadIndexValues.leftTrigger;
    @Override
    public GamepadIndexValues getTrigger() {
        return trigger;
    }
}
