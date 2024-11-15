package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;

public class IntakeStates {
    private static SubsystemState intakeState;
    private static IntakeMotorStates motorState;

    public static void setAllStates(SubsystemState state) {
        intakeState = SubsystemState.Idle;
        motorState = IntakeMotorStates.idle;
    }

    public static SubsystemState getIntakeState() {
        return intakeState;
    }

    public static void setIntakeState(SubsystemState state) {
        intakeState = state;
    }

    public static IntakeMotorStates getMotorState() {
        return motorState;
    }

    public static void setMotorState(IntakeMotorStates state) {
        motorState = state;
    }
}
