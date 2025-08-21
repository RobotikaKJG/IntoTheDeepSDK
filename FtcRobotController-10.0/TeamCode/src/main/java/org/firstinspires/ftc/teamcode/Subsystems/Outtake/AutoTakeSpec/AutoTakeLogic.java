package org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class AutoTakeLogic {
    private double currentWait = 0;
    private boolean wasIfCalled = false;

    public AutoTakeLogic() {}

    public void update() {
        switch (OuttakeStates.getAutoTakeState()) {
            case activate:
                activate();
                break;
            case liftArm:
                liftArm();
                break;
            case turnClaw:
                turnClaw();
                break;
            case waitForTakeAgain:
                waitForTakeAgain();
                break;
            case turnServoBack:
                turnServoBack();
                break;
            case armTakePos:
                armTakePos();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void activate() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setAutoTakeState(AutoTakeStates.liftArm);
        addWaitTime(OuttakeConstants.specimenTurnWait);
    }
    private void liftArm() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setAutoTakeState(AutoTakeStates.turnClaw);
    }

    private void turnClaw() {
        OuttakeStates.setAutoTakeState(AutoTakeStates.waitForTakeAgain);
    }

    private void waitForTakeAgain() {
        if (OuttakeStates.getArmState() == ArmStates.placeSpecimen) return;
        OuttakeStates.setAutoTakeState(AutoTakeStates.idle);
    }

    private void turnServoBack() {
        if(!wasIfCalled)
        {
            addWaitTime(0.4);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;

        wasIfCalled = false;
        OuttakeStates.setAutoTakeState(AutoTakeStates.armTakePos);

    }

    private void armTakePos() {
        OuttakeStates.setAutoTakeState(AutoTakeStates.idle);
    }

    private void idle() {
        if(isClawClosed() && OuttakeStates.getArmState() == ArmStates.takeSpecimen) {
            OuttakeStates.setAutoTakeState(AutoTakeStates.activate);
            addWaitTime(OuttakeConstants.specimenTakeWait);
        }
    }

    private boolean isClawClosed() {
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.freeMove || OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}

