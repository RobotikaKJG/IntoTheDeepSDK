package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class LeftBumperControl {
    public void update() {
        switch (ButtonStates.getLeftBumperState()) {
            case pivotDown:
                IntakeStates.setPivotState(PivotStates.down);
                break;
            case pivotUp:
                IntakeStates.setPivotState(PivotStates.upSlightly);
                break;
            case takeSample:
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                IntakeStates.setPivotState(PivotStates.down);
                break;
            case sampleTaken:
                IntakeStates.setMotorState(IntakeMotorStates.idle);
                IntakeStates.setPivotState(PivotStates.upSlightly);
                break;
            case retract:
                IntakeStates.setArmSlideState(ArmSlideStates.close);
                break;
            case idle:
                break;
        }
    }
}
