package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class ManualCloseLogic {
    private double currentWait = 0;
    private final ElapsedTime elapsedTime;

    public ManualCloseLogic(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void update() {
        switch (IntakeStates.getManualCloseStates()) {
            case activate:
                activate();
                break;
            case eject:
                eject();
                break;
            case close:
                close();
                break;
            case idle:
                break;
        }
    }

    private void activate() {
        addWaitTime(IntakeConstants.intakePushoutTime);
        IntakeStates.setManualCloseStates(ManualCloseStates.close);
    }

    private void eject() {
        if(currentWait > elapsedTime.seconds()) return;
        IntakeStates.setManualCloseStates(ManualCloseStates.close);
    }

    private void close() {
        IntakeStates.setManualCloseStates(ManualCloseStates.idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
