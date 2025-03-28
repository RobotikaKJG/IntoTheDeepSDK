package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose;

import com.qualcomm.robotcore.hardware.Gamepad;

//import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;

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
                checkColor();
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
            case closeSampleClaw:
                closeSampleClaw();
                break;
            case idle:
                break;
        }
    }

    private void checkColor() {
        OuttakeStates.setSampleLockState(SampleLockStates.closed);
        OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
    }

    private void secureGoodSample() {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
//        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }

    private void ejectExtraSamples() {
        gamepad1.rumble(200);
        IntakeStates.setMotorState(IntakeMotorStates.backward);
    }

    private void waitForCommand() {
        IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
        gamepad1.rumble(400);
    }

    private static void waitToRetract() {
        IntakeStates.setExtendoState(ExtendoStates.retracting);
        IntakeStates.setMotorState(IntakeMotorStates.idleWasForward); // for manual retract, NOTE
//        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
        OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
    }

    private static void closeSampleClaw() {
        OuttakeStates.setArmState(ArmStates.down);
        OuttakeStates.setSampleClawState(SampleClawStates.closed);
        OuttakeStates.setSampleLockState(SampleLockStates.open);
    }

}
