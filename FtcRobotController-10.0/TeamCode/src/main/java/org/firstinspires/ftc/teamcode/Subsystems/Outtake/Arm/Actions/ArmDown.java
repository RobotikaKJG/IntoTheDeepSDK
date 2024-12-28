package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ArmDown implements Action {
    private boolean initialized = false;
    private double currentWait = 0;
    private final double armDownWait = 1;

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        if (!initialized) {
            OuttakeStates.setArmState(ArmStates.down);
            addWaitTime(armDownWait);
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
