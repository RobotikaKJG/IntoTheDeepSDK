package org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;

public class AutoEjectControl {

    private AutoEjectStates prevIntakeAutoEjectState = AutoEjectStates.idle;

    public AutoEjectControl() {}

    public void update() {
        if(IntakeStates.getAutoEjectState() != prevIntakeAutoEjectState) {
            updateStates();
            prevIntakeAutoEjectState = IntakeStates.getAutoEjectState();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getAutoEjectState()) {
            case sampleOut:
                sampleOut();
                break;
            case close:
                close();
                break;
            case idle:
                break;
        }
    }

    private void sampleOut() {
        IntakeStates.setLatchState(LatchStates.open);
    }

    private void close() {
        IntakeStates.setLatchState(LatchStates.closed);
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        IntakeStates.setPivotState(PivotStates.down);
    }

}
