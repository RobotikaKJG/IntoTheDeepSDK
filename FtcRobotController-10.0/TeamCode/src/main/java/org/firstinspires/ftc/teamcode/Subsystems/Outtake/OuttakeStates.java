package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;

public class OuttakeStates {
    private static SubsystemState outtakeState;

    public static void setAllStates(SubsystemState state) {
        outtakeState = SubsystemState.Idle;
    }

    public static SubsystemState getOuttakeState() {
        return outtakeState;
    }

    public static void setOuttakeState(SubsystemState state) {
        outtakeState = state;
    }
}
