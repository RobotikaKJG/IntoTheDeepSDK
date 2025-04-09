package org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class DropSampleControl {
    private DropSampleStates prevDropSampleStates;

    public void update() {
        if (prevDropSampleStates != OuttakeStates.getDropSampleState()) {
            updateStates();
            prevDropSampleStates = OuttakeStates.getDropSampleState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getDropSampleState()) {
            case openLock:
                OuttakeStates.setSampleLockState(SampleLockStates.open);
                break;
            case spinMotor:
                IntakeConstants.setIntakeSpeed(1);//0.5);
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                break;
            case waitToEject:
                break;
            case idle:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
                OuttakeStates.setSampleLockState(SampleLockStates.closed);
                break;
        }
    }
}
