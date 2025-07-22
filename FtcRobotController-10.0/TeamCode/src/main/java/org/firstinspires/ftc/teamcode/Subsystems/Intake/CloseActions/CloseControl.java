package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;

public class CloseControl {

    private CloseStates prevIntakeCloseState = CloseStates.idle;
    private final Gamepad gamepad1;

    public CloseControl(Gamepad gamepad1) {
        this.gamepad1 = gamepad1;
    }

    public void update() {
        if(IntakeStates.getCloseStates() != prevIntakeCloseState) {
            updateStates();
            prevIntakeCloseState = IntakeStates.getCloseStates();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getCloseStates()) {
            case checkColor:
                checkColor();
                break;
            case secureGoodSample:
                secureGoodSample();
                break;
            case waitForCommand:
                waitForCommand();
                break;
            case pivot:
                pivot();
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
        if(GlobalVariables.subCycles || !GlobalVariables.isAutonomous)
            OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private void secureGoodSample() {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
//        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }

    private void waitForCommand() {
        IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
        gamepad1.rumble(400);
    }

    private void pivot() {
        IntakeStates.setPivotState(PivotStates.up);
        IntakeStates.setMotorState(IntakeMotorStates.idleWasForward); //temporary? NIGHTNOTE
    }

    private static void waitToRetract() {
        IntakeStates.setExtendoState(ExtendoStates.retracting);
        if(GlobalVariables.subCycles || !GlobalVariables.isAutonomous)
            IntakeStates.setMotorState(IntakeMotorStates.idleWasForward); // for manual retract, NOTE
//        IntakeStates.setEjectionServoState(EjectionServoStates.closed);
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private static void closeSampleClaw() {
        OuttakeStates.setArmState(ArmStates.down);
        if(GlobalVariables.subCycles || !GlobalVariables.isAutonomous) {
            OuttakeStates.setClawState(ClawStates.closed);
            OuttakeStates.setSampleLockState(SampleLockStates.open);
        }
    }

}
