package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class LeftBumperControl {
    public void update() {
        switch (ButtonStates.getLeftBumperState()) {
            case outtakeReady:
                OuttakeStates.setArmState(ArmStates.takeSpecimen);
                break;
            case takeSpecimen:
                OuttakeStates.setSpecimenClawState(SpecimenClawStates.freeMove);
                break;
            case armUp:
                OuttakeStates.setArmState(ArmStates.placeSpecimen);
                break;
            case placeSpecimen:
                OuttakeStates.setSpecimenClawState(SpecimenClawStates.fullyOpen);
                break;
            case idle:
                break;
        }
    }
}
