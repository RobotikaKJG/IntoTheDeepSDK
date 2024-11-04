package org.firstinspires.ftc.teamcode.Subsystems.OldIntake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class OldIntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final OldIntakeTrigger oldIntakeTrigger = new OldIntakeTrigger();
    private final OldIntakeStateControl intakeStateControl;
    private final OldIntakeStopping oldIntakeStopping;
    private SubsystemState intakeState = SubsystemState.Idle;

    public OldIntakeController(OldIntakeStateControl oldIntakeStateControl, ServoControl servoControl, EdgeDetection edgeDetection, ElapsedTime elapsedTime) {
        this.edgeDetection = edgeDetection;
        this.intakeStateControl = oldIntakeStateControl;
        oldIntakeStopping = new OldIntakeStopping(elapsedTime, oldIntakeStateControl, servoControl);
    }

    @Override
    public void updateState() {
        switch (intakeState) {
            case Start:
                start();
                break;
            case Run:
                run();
                break;
            case Stop:
                stop();
                break;
            case Idle:
                idle();
                break;
        }
    }

    @Override
    public void start() {
        intakeStateControl.openAndTakeIn();
        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        if (!edgeDetection.rising(oldIntakeTrigger.getTrigger())) return;
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        if (!oldIntakeStopping.isIdle()) return;
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(oldIntakeTrigger.getTrigger())) return;

        intakeState = SubsystemState.Start;
    }

    @Override
    public SubsystemState getState() {
        return intakeState;
    }
}
