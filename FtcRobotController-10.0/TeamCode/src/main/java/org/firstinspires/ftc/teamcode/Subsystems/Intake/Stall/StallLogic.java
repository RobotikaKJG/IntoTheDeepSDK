package org.firstinspires.ftc.teamcode.Subsystems.Intake.Stall;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection.SampleEjectionStates;

public class StallLogic {

    private double currentWait = 0;
    private boolean wasIfCalled = false;

    public void update(){
        switch(IntakeStates.getStallState())
        {
            case eject:
                eject();
                break;
            case intake:
                intake();
                break;
            case idle:
                break;
        }
    }

    private void eject() {
        if(!wasIfCalled) {
            addWaitTime(0.2);
            wasIfCalled = true;
//            System.out.println("OverCurrentActive");
        }
        if(currentWait > getSeconds()) return;
        IntakeStates.setStallState(StallStates.intake);
        wasIfCalled = false;
    }

    private void intake() {
        IntakeStates.setStallState(StallStates.idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
