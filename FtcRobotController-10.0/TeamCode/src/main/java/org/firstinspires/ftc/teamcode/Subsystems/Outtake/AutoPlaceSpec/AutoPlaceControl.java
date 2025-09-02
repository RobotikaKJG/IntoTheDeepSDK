package org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoPlaceSpec;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TurnServo.TurnServoStates;

public class AutoPlaceControl {
    private AutoPlaceStates prevIntakeAutoPlaceState = AutoPlaceStates.idle;

    public AutoPlaceControl() {}

    public void update() {
        if(OuttakeStates.getAutoPlaceState() != prevIntakeAutoPlaceState) {
            updateStates();
            prevIntakeAutoPlaceState = OuttakeStates.getAutoPlaceState();
        }
    }

    public void updateStates() {
        switch (OuttakeStates.getAutoPlaceState()) {
            case placeSpec:
                placeSpec();
                break;
            case releaseSpec:
                releaseSpec();
                break;
            case moveBack:
                moveBack();
                break;
            case idle:
                break;
        }
    }

    private void placeSpec() {
        OuttakeStates.setArmState(ArmStates.releaseSpecimen);
    }

    private void releaseSpec() {
        OuttakeStates.setSpecimenClawState(SpecimenClawStates.fullyOpen);
        OuttakeStates.setArmState(ArmStates.movePos);
        OuttakeStates.setTurnServoState(TurnServoStates.takePos);
    }

    private void moveBack() {
        OuttakeStates.setArmState(ArmStates.takeSpecimen);
    }
}
