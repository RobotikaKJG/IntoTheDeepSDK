package org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class IntakeMotorLogic {
    private final MotorControl motorControl;
    private boolean wasOverCurrent = false;
    private double currentWait;

    public IntakeMotorLogic(MotorControl motorControl) {
        this.motorControl = motorControl;
    }

    public void update() {
//        if(!GlobalVariables.isAutonomous) return;
        boolean isOverCurrent = motorControl.isOverCurrent(MotorConstants.intake);
        System.out.println(motorControl.getMotorCurrent(MotorConstants.intake));
        if(isOverCurrent && !wasOverCurrent) {
            IntakeStates.setMotorState(IntakeMotorStates.backward);
            addWaitTime(0.3);
            wasOverCurrent = true;
        }

        if(currentWait > getSeconds()){
            IntakeStates.setMotorState(IntakeMotorStates.forward);
            wasOverCurrent = false;
        }

    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
