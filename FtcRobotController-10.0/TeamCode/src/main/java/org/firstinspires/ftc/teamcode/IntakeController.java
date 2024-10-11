package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.SubsystemState;
import org.firstinspires.ftc.teamcode.ServoControl;
import org.firstinspires.ftc.teamcode.RobotSubsystemController;
import org.firstinspires.ftc.teamcode.GamepadIndexValues;

public class IntakeController implements RobotSubsystemController{
    private final EdgeDetection edgeDetection;
    private final ServoControl servoControl;
    private SubsystemState intakeState = SubsystemState.Idle;


    public IntakeController(ServoControl servoControl, EdgeDetection edgeDetection) {
        this.edgeDetection = edgeDetection;
        this.servoControl = servoControl;
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
        servoControl.setServoPos(0.5);
        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        if (!edgeDetection.rising(GamepadIndexValues.circle)) {
            return;
        }
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        servoControl.setServoPos(0);
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(GamepadIndexValues.circle)) {
            return;
        }
        intakeState = SubsystemState.Start;
    }
}
