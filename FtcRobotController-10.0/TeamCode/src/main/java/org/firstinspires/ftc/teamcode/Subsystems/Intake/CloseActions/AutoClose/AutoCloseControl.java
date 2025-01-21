package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class AutoCloseControl {

    private AutoCloseStates prevIntakeAutoCloseState = AutoCloseStates.idle;
    private final Gamepad gamepad1;

    public AutoCloseControl(Gamepad gamepad1) {
        this.gamepad1 = gamepad1;
    }

    public void update() {
        if(IntakeStates.getAutoCloseStates() != prevIntakeAutoCloseState) {
            updateStates();
            prevIntakeAutoCloseState = IntakeStates.getAutoCloseStates();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getAutoCloseStates()) {
            case checkColor:
                break;
            case secureGoodSample:
                secureGoodSample();
                break;
            case ejectExtraSamples:
                ejectExtraSamples();
                break;
            case waitForCommand:
                waitForCommand();
                break;
            case waitToRetract:
                waitToRetract();
                break;
            case closeClaw:
                closeClaw();
                break;
            case idle:
                break;
        }
    }

    private void secureGoodSample() {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }

    private void ejectExtraSamples() {
        IntakeStates.setMotorState(IntakeMotorStates.backward);
    }

    private void waitForCommand() {
        IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
        gamepad1.rumble(400);
    }

    private static void waitToRetract() {
        IntakeStates.setExtendoState(ExtendoStates.retracting);
        IntakeStates.setMotorState(IntakeMotorStates.idleWasForward); // for manual retract, NOTE
        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private static void closeClaw() {
        OuttakeStates.setArmState(ArmStates.down);
        OuttakeStates.setClawState(ClawStates.closed);
    }
}
