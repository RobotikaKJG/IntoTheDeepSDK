package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class OuttakeStates {
    private static SubsystemState outtakeState = SubsystemState.Idle;
    private static SpecimenClawStates sampleClawState = SpecimenClawStates.fullyOpen;
    private static ArmStates armState = ArmStates.idle;
    private static AutoTakeStates autoTakeState = AutoTakeStates.idle;

    public static void setInitialStates() {
        outtakeState = SubsystemState.Idle;
        armState = ArmStates.idle;
        autoTakeState = AutoTakeStates.idle;
        if(GlobalVariables.isAutonomous) {
            sampleClawState = SpecimenClawStates.closed;
        }
        else {
            sampleClawState = SpecimenClawStates.fullyOpen;
        }
    }

    public static SubsystemState getOuttakeState() {
        return outtakeState;
    }

    public static void setOuttakeState(SubsystemState state) {
        outtakeState = state;
    }


    public static SpecimenClawStates getSpecimenClawState() {
        return sampleClawState;
    }

    public static void setSpecimenClawState(SpecimenClawStates state) {
        sampleClawState = state;
    }

    public static ArmStates getArmState() {
        return armState;
    }

    public static void setArmState(ArmStates state) {
        armState = state;
    }

    public static AutoTakeStates getAutoTakeState() {
        return autoTakeState;
    }

    public static void setAutoTakeState(AutoTakeStates state) {autoTakeState = state;}

}
