package org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec;

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

    public AutoTakeLogic() {}

    public void update() {
        switch (OuttakeStates.getAutoTakeState()) {
            case activate:
                activate();
                break;
            case liftArm:
                liftArm();
                break;
            case idle:
                break;
        }
    }

    private void activate() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setAutoTakeState(AutoTakeStates.liftArm);
    }
    private void liftArm() {
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

