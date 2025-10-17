package org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection;

//import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class SampleEjectionControl {

    private SampleEjectionStates prevSampleEjectionState = SampleEjectionStates.idle;

    public void update() {
        if(IntakeStates.getSampleEjectionState() != prevSampleEjectionState) {
            updateStates();
            prevSampleEjectionState = IntakeStates.getSampleEjectionState();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getSampleEjectionState()) {
            case stopMotor:
                stopMotor();
                break;
            case openLatch:
                openLatch();
                break;
            case startMotor:
                startMotor();
                break;
            case closeLatch:
                closeLatch();
                break;
            case idle:
                break;
        }
    }

    private void stopMotor() {
        IntakeStates.setMotorState(IntakeMotorStates.idle);
    }

    private void openLatch() {
        IntakeStates.setLatchState(LatchStates.open);
    }

    private void startMotor() {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
    }


    private void closeLatch() {
        IntakeStates.setLatchState(LatchStates.closed);
    }
}
