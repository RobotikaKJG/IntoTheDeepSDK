package org.firstinspires.ftc.teamcode.Subsystems.Intake.Stall;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection.SampleEjectionStates;

public class StallControl {

    private StallStates prevStallStates = StallStates.idle;

    public void update() {
        if (IntakeStates.getStallState() != prevStallStates) {
            updateStates();
            prevStallStates = IntakeStates.getStallState();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getStallState()) {
            case eject:
                eject();
                break;
            case intake:
                intake();
                break;
            case idle:
                break;
        }
    }

    private void eject() {
//        System.out.println("ShouldEject");
        IntakeStates.setMotorState(IntakeMotorStates.backward);
    }

    private void intake(){
//        System.out.println("ShouldIntake");
        IntakeStates.setMotorState(IntakeMotorStates.forward);
    }
}
