package org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;

public class AutoCloseControl {

    private AutoCloseStates prevIntakeAutoCloseState = AutoCloseStates.idle;
    private final Gamepad gamepad1;

    public AutoCloseControl(Gamepad gamepad1) {
        this.gamepad1 = gamepad1;
    }

    public void update() {
        if(IntakeStates.getAutoCloseState() != prevIntakeAutoCloseState) {
            updateStates();
            prevIntakeAutoCloseState = IntakeStates.getAutoCloseState();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getAutoCloseState()) {
            case checkColor:
                checkColor();
                break;
            case securedGoodSample:
                securedGoodSample();
                break;
            case idle:
                break;
        }
    }

    private void checkColor() {
        IntakeStates.setLatchState(LatchStates.closed);
    }

    private void securedGoodSample() {
        gamepad1.rumble(200);
        IntakeStates.setLatchState(LatchStates.closed);
        IntakeStates.setMotorState(IntakeMotorStates.idle);
        IntakeStates.setPivotState(PivotStates.upSlightly);
    }

}