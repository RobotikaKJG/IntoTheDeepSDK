package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TurnServo.TurnServoStates;

public class SquareControl {
    public void update() {
        switch (ButtonStates.getSquareState()) {
            case takeSpecimen:
                OuttakeStates.setTurnServoState(TurnServoStates.takePos);
                OuttakeStates.setArmState(ArmStates.takeSpecimen);
                OuttakeStates.setSpecimenClawState(SpecimenClawStates.fullyOpen);
                break;
            case idle:
                break;
        }
    }
}
