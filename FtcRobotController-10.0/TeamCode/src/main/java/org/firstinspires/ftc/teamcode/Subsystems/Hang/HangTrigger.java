package org.firstinspires.ftc.teamcode.Subsystems.Hang;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemTrigger;

public class HangTrigger implements SubsystemTrigger {

    private static final GamepadIndexValues trigger = GamepadIndexValues.cross;
    @Override
    public GamepadIndexValues getTrigger() {
        return trigger;
    }
}
