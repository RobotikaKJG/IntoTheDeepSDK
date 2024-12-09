package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;

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
        if(IntakeStates.getMotorState() == IntakeRotationStates.forward)
            IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }

    private void close() {
        IntakeStates.setMotorState(IntakeRotationStates.idle);
        IntakeStates.setServoState(IntakeRotationStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }
}
