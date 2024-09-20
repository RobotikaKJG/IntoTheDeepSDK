package org.firstinspires.ftc.teamcode.Subsystems.Drivebase;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemTrigger;

public class DrivebaseTrigger implements SubsystemTrigger {

    private static final GamepadIndexValues trigger = GamepadIndexValues.dpadDown;
    @Override
    public GamepadIndexValues getTrigger() {
        return trigger;
    }
}
