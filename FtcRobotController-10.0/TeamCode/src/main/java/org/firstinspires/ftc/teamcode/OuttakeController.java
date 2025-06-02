package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeController implements RobotSubsystemController {
    public boolean risen = false;
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private final MotorControl outtakeMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    public double currentAngle = 0;

    public OuttakeController(EdgeDetection edgeDetection, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.outtakeMotorControl = new MotorControl(hardwareMap, "armMotor", true);
        outtakeMotorControl.resetMotorEncoder();
    }

    @Override
    public void updateState() {
        currentAngle = (outtakeMotorControl.getMotorCurrentPosition() / (double) 1150) * 360.0;
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
        outtakeMotorControl.runToAngle(50, 20, 1140, 1, DcMotorSimple.Direction.FORWARD);
        if (edgeDetection.rising(GamepadIndexValues.rightBumper)) {
            intakeState = SubsystemState.Run;
        }
    }

    @Override
    public void run() {
        outtakeMotorControl.runToAngle(900, 20, 1140, 1, DcMotorSimple.Direction.FORWARD);
        if (edgeDetection.rising(GamepadIndexValues.rightBumper)) {
            intakeState = SubsystemState.Stop;
        }
    }

    @Override
    public void stop() {
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        intakeState = SubsystemState.Start;
    }
}