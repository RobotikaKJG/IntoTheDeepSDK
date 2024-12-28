package org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

/**
 * SHOULD NOT BE USED SEQUENTIALLY OR WITHOUT INTAKE LOGIC
 */
public class EjectionServoAction implements Action {
    private final SampleEjectionLogic sampleEjectionLogic;

    public EjectionServoAction(SampleEjectionLogic sampleEjectionLogic) {
        this.sampleEjectionLogic = sampleEjectionLogic;
    }

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        sampleEjectionLogic.update();
        return (IntakeStates.getIntakeState() != SubsystemState.Idle);
    }
}
