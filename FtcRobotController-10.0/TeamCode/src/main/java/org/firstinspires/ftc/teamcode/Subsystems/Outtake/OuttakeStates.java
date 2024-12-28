package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class OuttakeStates {
    private static SubsystemState outtakeState = SubsystemState.Idle;
    private static VerticalSlideStates verticalSlideStates = VerticalSlideStates.closed;
    private static ClawStates clawState = ClawStates.fullyOpen;
    private static ArmStates armState = ArmStates.down;
    private static ReleaseButtonStates releaseButtonStates = ReleaseButtonStates.idle;

    public static void setInitialStates(){
        outtakeState = SubsystemState.Idle;
        verticalSlideStates = VerticalSlideStates.closed;
        clawState = ClawStates.fullyOpen;
        armState = ArmStates.down;
        releaseButtonStates = ReleaseButtonStates.idle;
    }

    public static SubsystemState getOuttakeState() {
        return outtakeState;
    }

    public static void setOuttakeState(SubsystemState state) {
        outtakeState = state;
    }


    public static VerticalSlideStates getVerticalSlideState(){
        return verticalSlideStates;
    }

    public static void setVerticalSlideState(VerticalSlideStates state) {
        verticalSlideStates = state;
    }


    public static ClawStates getClawState() {
        return clawState;
    }

    public static void setClawState(ClawStates state) {
        clawState = state;
    }


    public static ArmStates getArmState() {
        return armState;
    }

    public static void setArmState(ArmStates state) {
        armState = state;
    }


    public static ReleaseButtonStates getReleaseButtonState(){
        return releaseButtonStates;
    }

    public static void setReleaseButtonState (ReleaseButtonStates state) {
        releaseButtonStates = state;
    }
}
