package org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMode;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class IntakeMotorControl {
    private final MotorControl motorControl;
    private IntakeMotorStates prevMotorRotationStates = IntakeMotorStates.idle;

    public IntakeMotorControl(MotorControl motorControl) {
        this.motorControl = motorControl;

    }

    public void update() {
        if(IntakeStates.getMotorState() != prevMotorRotationStates) {
            updateStates();
            prevMotorRotationStates = IntakeStates.getMotorState();
        }
    }

    public void updateStates() {
        if (GlobalVariables.autonomousMode == AutonomousMode.sampleAuton) {
            switch (IntakeStates.getMotorState()) {
                case forward:
                    motorControl.setMotorSpeed(MotorConstants.intake, 1.0);
                    break;
                case backward:
                    motorControl.setMotorSpeed(MotorConstants.intake, -1.0);
                    break;
                case idle:
                case idleWasForward:
                case idleWasBackward:
                    motorControl.setMotorSpeed(MotorConstants.intake, 0);
                    break;
            }
        }

        else {
            switch (IntakeStates.getMotorState()) {
                case forward:
                    IntakeConstants.setIntakeSpeed(1);
                    motorControl.setMotorSpeed(MotorConstants.intake, IntakeConstants.getIntakeSpeed());
                    break;
                case backward:
                    motorControl.setMotorSpeed(MotorConstants.intake, -IntakeConstants.getIntakeSpeed());
                    break;
                case idle:
                case idleWasForward:
                case idleWasBackward:
                    motorControl.setMotorSpeed(MotorConstants.intake, 0);
                    break;
            }
        }

    }
}
