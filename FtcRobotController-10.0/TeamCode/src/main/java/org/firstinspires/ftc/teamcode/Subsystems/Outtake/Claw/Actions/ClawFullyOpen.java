package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ClawFullyOpen implements Action {
    private boolean initialized = false;
    private double currentWait = 0;
    private final double clawFullyOpenWait = 1;

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        if (!initialized) {
            OuttakeStates.setArmState(ArmStates.up);
            addWaitTime(clawFullyOpenWait);
            initialized = true;
        }

        return (currentWait < getSeconds());
    }

    private void addWaitTime ( double waitTime){
    currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
