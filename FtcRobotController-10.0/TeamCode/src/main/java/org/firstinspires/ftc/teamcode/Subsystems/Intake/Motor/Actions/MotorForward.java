package org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class MotorForward implements Action {

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        return false;
    }
}
