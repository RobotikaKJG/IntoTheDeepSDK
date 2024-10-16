package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemTrigger;

public class IntakeMotorTrigger implements SubsystemTrigger{

    private static final GamepadIndexValues trigger = GamepadIndexValues.rightBumper;

    @Override
    public GamepadIndexValues getTrigger() {
        return trigger;
    }
}
