package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private SubsystemState intakeState = SubsystemState.Idle;
    private final ServoControl intakeServoControl;
    private final ServoControl turnServoControl;

    public IntakeController(EdgeDetection edgeDetection, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;

        this.intakeServoControl = new ServoControl(hardwareMap, "back", true);
        this.turnServoControl = new ServoControl(hardwareMap, "front", false);
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
        if (edgeDetection.rising(GamepadIndexValues.square)) {
            intakeState = SubsystemState.Run;
        }

    }

    @Override
    public void run() {
        intakeServoControl.setServoSpeed(1);
        turnServoControl.setServoPos(0.7);
        if (edgeDetection.rising(GamepadIndexValues.square)) {
            intakeState = SubsystemState.Stop;
        }
    }

    @Override
    public void stop() {
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        intakeServoControl.setServoSpeed(0);
        turnServoControl.setServoPos(0.7);
        intakeState = SubsystemState.Start;
    }
}