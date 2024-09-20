package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class IntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final IntakeTrigger intakeTrigger = new IntakeTrigger();
    private final IntakeStateControl intakeStateControl;
    private final IntakeStopping intakeStopping;
    private SubsystemState intakeState = SubsystemState.Idle;

    public IntakeController(IntakeStateControl intakeStateControl, ServoControl servoControl, EdgeDetection edgeDetection, ElapsedTime elapsedTime) {
        this.edgeDetection = edgeDetection;
        this.intakeStateControl = intakeStateControl;
        intakeStopping = new IntakeStopping(elapsedTime, intakeStateControl, servoControl);
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
        if (!edgeDetection.rising(intakeTrigger.getTrigger())) return;
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        if (!intakeStopping.isIdle()) return;
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(intakeTrigger.getTrigger())) return;

        intakeState = SubsystemState.Start;
    }

    public SubsystemState getIntakeState() {
        return intakeState;
    }
}
