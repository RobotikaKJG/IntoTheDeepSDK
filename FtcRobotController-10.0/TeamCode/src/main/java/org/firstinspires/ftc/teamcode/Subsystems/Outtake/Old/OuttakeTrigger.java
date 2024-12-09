package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Old;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemTrigger;

public class OuttakeTrigger implements SubsystemTrigger {

    private static final GamepadIndexValues trigger = GamepadIndexValues.leftBumper;
    @Override
    public GamepadIndexValues getTrigger() {
        return trigger;
    }
}
