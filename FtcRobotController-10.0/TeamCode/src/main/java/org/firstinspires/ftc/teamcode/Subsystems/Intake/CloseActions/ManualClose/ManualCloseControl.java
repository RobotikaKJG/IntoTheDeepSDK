package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class ManualCloseControl {

    private ManualCloseStates prevManualCloseState = ManualCloseStates.idle;

    public ManualCloseControl() {

    }

    public void update() {
        if(IntakeStates.getManualCloseStates() != prevManualCloseState) {
            updateStates();
            prevManualCloseState = IntakeStates.getManualCloseStates();
        }
    }

    public void updateStates() {
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
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward)
            IntakeStates.setMotorState(IntakeMotorStates.backward);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }

    private void close() {
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }
}
