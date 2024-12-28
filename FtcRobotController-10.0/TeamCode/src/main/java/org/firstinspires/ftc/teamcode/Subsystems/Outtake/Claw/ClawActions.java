package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.Actions.ClawClosed;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.Actions.ClawFullyOpen;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.Actions.ClawHalfOpen;

public class ClawActions {

    public Action close(){
        return new ClawClosed();
    }
    public Action halfOpen(){
        return new ClawHalfOpen();
    }
    public Action fullyOpen(){
        return new ClawFullyOpen();
    }
}
