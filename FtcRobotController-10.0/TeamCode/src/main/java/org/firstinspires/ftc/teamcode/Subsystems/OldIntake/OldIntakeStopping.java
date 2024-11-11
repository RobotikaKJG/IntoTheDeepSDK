package org.firstinspires.ftc.teamcode.Subsystems.OldIntake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.IntakeStoppingState;

public class OldIntakeStopping {
    private final OldIntakeStateControl intakeStateControl;
    private IntakeStoppingState intakeStoppingState = IntakeStoppingState.Start;
    private final ElapsedTime runtime;
    private final ServoControl servoControl;
    private double currentWait;

    public OldIntakeStopping(ElapsedTime runtime, OldIntakeStateControl intakeStateControl, ServoControl servoControl) {
        this.runtime = runtime;
        this.intakeStateControl = intakeStateControl;
        this.servoControl = servoControl;
    }

    public boolean isIdle() {
        switch (intakeStoppingState) {
            case Start:
                start();
                break;
            case CloseServos:
                closeServos();
                break;
            case PushOut:
                pushOut();
                break;
            case Stop:
                stop();
                break;
            case Idle:
                intakeStoppingState = IntakeStoppingState.Start;
                return true;
        }
        return false;
    }

    public void start() {
        servoControl.increasePos(ServoConstants.outtakeRight);
        servoControl.increasePos(ServoConstants.release);
        intakeStoppingState = IntakeStoppingState.CloseServos;
        addWaitTime(OldIntakeConstants.intakeStopServoCloseWait);
    }

    public void closeServos() {
        if (currentWait > runtime.seconds()) return;

        intakeStoppingState = IntakeStoppingState.PushOut;
        intakeStateControl.pushOut();
        addWaitTime(OldIntakeConstants.intakeStopPushoutWait);
    }

    public void pushOut() {
        if (currentWait > runtime.seconds()) return;

        intakeStoppingState = IntakeStoppingState.Stop;
    }

    public void stop() {
        intakeStateControl.stop();  // Perform action for this state
        // Transition back to IDLE after completion
        intakeStoppingState = IntakeStoppingState.Idle;
    }

    private void addWaitTime(double waitTime) {
        currentWait = runtime.seconds() + waitTime;
    }
}
