package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.lang.Math;

public class ArmExtentionController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private final OuttakeController outtakeController;
    private final MotorControl extendingMotorControl;
    private final MotorControl liftMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    public double angle = 0;
    public double angleIncrement = 10;
    private boolean up = false;
    private boolean down = false;
    private final int maxExtentionAngle = 930;

    public ArmExtentionController(EdgeDetection edgeDetection, HardwareMap hardwareMap, OuttakeController outtakeController) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.extendingMotorControl = new MotorControl(hardwareMap, "extendingMotor", true);
        this.liftMotorControl = new MotorControl(hardwareMap, "liftMotor", true);
        this.outtakeController = outtakeController;
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
        extendingMotorControl.resetMotorEncoder();
        angle = 0;
        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {

        if (outtakeController.risen) {
            angle = maxExtentionAngle;
        }
        else if (outtakeController.goDown) {
            angle = 0;
        }

        if (edgeDetection.rising(GamepadIndexValues.leftBumper)) {
            up = true;
        } else if (edgeDetection.falling(GamepadIndexValues.leftBumper)) {
            up = false;
        }

        if (edgeDetection.rising(GamepadIndexValues.leftTrigger)) {
            down = true;
        } else if (edgeDetection.falling(GamepadIndexValues.leftTrigger)) {
            down = false;
        }

        if (up && angle < maxExtentionAngle) {
            angle += angleIncrement;
        } else if (up) {
            angle = maxExtentionAngle;
        }

        if (down) {
            angle -= angleIncrement;
        }

        extendingMotorControl.runToAngle(angle, 0.6, 1150, 1.0, DcMotorSimple.Direction.FORWARD);
        if (angle < 5 && down) {
            extendingMotorControl.runToAngle(0, 1, 1150, 1.0, DcMotorSimple.Direction.FORWARD);
            angle = 0;
            intakeState = SubsystemState.Stop;
        }

    }

    @Override
    public void stop() {
        if (!extendingMotorControl.isMotorBusy()) {
            down = false;
            up = false;
            extendingMotorControl.setMotorPower(0);
            intakeState = SubsystemState.Idle;
        }
    }

    @Override
    public void idle() {
        intakeState = SubsystemState.Start;
    }
}
