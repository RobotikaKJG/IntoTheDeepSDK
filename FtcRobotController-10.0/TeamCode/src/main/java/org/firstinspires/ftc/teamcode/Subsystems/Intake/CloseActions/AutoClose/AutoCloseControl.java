package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;
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

    private static void secureGoodSample() {
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setMotorState(IntakeRotationStates.forward);
        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }

    private static void ejectExtraSamples() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.idle);
    }

    private void waitForCommand() {
        IntakeStates.setMotorState(IntakeRotationStates.idleWasForward);
        gamepad1.rumble(400);
    }

    private static void waitToRetract() {
        IntakeStates.setExtendoState(ExtendoStates.retracting);
        IntakeStates.setMotorState(IntakeRotationStates.idleWasForward); // for manual retract, NOTE
        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private static void closeClaw() {
        OuttakeStates.setArmState(ArmStates.down);
        OuttakeStates.setClawState(ClawStates.closed);
    }
}
