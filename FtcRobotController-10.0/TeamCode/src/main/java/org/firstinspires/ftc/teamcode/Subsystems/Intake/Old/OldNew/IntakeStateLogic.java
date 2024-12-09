package org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.OldNew;

import com.qualcomm.robotcore.util.ElapsedTime;

public class IntakeStateLogic {
    private final ElapsedTime elapsedTime;
    private boolean wasIfCalled = false;
    private double currentWait = 0;

    public IntakeStateLogic(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void update() {

    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
