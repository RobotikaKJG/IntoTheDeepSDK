package org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class AutoTakeControl {
    private AutoTakeStates prevIntakeAutoTakeState = AutoTakeStates.idle;

    public AutoTakeControl() {}

    public void update() {
        if(OuttakeStates.getAutoTakeState() != prevIntakeAutoTakeState) {
            updateStates();
            prevIntakeAutoTakeState = OuttakeStates.getAutoTakeState();
        }
    }

    public void updateStates() {
        switch (OuttakeStates.getAutoTakeState()) {
            case activate:
                activate();
                break;
            case liftArm:
                liftArm();
                break;
            case idle:
                break;
        }
    }

    private void activate() {
        OuttakeStates.setSpecimenClawState(SpecimenClawStates.freeMove);
    }

    private void liftArm() {
        OuttakeStates.setArmState(ArmStates.placeSpecimen);
    }
}
