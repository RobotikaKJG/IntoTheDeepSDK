package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class ManualCloseControl {
    private final ElapsedTime elapsedTime = new ElapsedTime();

    private enum EjectPhase { START, WAITING, FINISH }
    private EjectPhase ejectPhase = EjectPhase.START;

    private ManualCloseStates prevManualCloseState = ManualCloseStates.idle;
    private double waitDuration = 1.0; // seconds
    private double waitUntilTime = 0;

    public void update() {
        if (IntakeStates.getManualCloseStates() != prevManualCloseState) {
            ejectPhase = EjectPhase.START; // reset eject state on change
            prevManualCloseState = IntakeStates.getManualCloseStates();
        }

        updateStates();
    }

    private void updateStates() {
        switch (IntakeStates.getManualCloseStates()) {
            case eject:
                eject();
                break;
            case close:
                close();
                break;
            case idle:
                break;
        }
    }

    private void eject() {
        switch (ejectPhase) {
            case START:
                if (IntakeStates.getMotorState() == IntakeMotorStates.forward) {
                    IntakeStates.setMotorState(IntakeMotorStates.backward);
                    waitUntilTime = elapsedTime.seconds() + waitDuration;
                    ejectPhase = EjectPhase.WAITING;
                } else {
                    ejectPhase = EjectPhase.FINISH;
                }
                break;

            case WAITING:
                if (elapsedTime.seconds() >= waitUntilTime) {
                    ejectPhase = EjectPhase.FINISH;
                }
                break;

            case FINISH:
                IntakeStates.setExtendoState(ExtendoStates.retracted);
//              IntakeStates.setEjectionServoState(EjectionServoStates.closed);
                break;
        }
    }

    private void close() {
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }
}
