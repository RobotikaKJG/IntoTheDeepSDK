package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmExtentionController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final IntakeController intakeController;
    private final HardwareMap hardwareMap;
    private final OuttakeController outtakeController;
    private final MotorControl extendingMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    public double currentAngle = 0;
    public double angle = 0;
    private double angleS = 550;
    public double angleIncrement = 10;
    private boolean up = false;
    private boolean down = false;
    private final int maxExtentionAngle = 930;

    public ArmExtentionController(EdgeDetection edgeDetection, IntakeController intakeController, HardwareMap hardwareMap, OuttakeController outtakeController) {
        this.edgeDetection = edgeDetection;
        this.intakeController = intakeController;
        this.hardwareMap = hardwareMap;
        this.outtakeController = outtakeController;

        this.extendingMotorControl = new MotorControl(hardwareMap, "extendingMotor", true);
    }

    @Override
    public void updateState() {
        currentAngle = (extendingMotorControl.getMotorCurrentPosition() / (double) 1150) * 360.0;
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
        if (TeleOpController.isUp && TeleOpController.wasDown) {
            intakeController.wasDown = false;
            angle = 0;
        }

        if (outtakeController.risen) {
            if (outtakeController.square) {
                angle = angleS;
            } else {
                angle = 620;
            }
        } else if (outtakeController.goDown) {
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

        if (intakeController.sample) {
            angleS = 0;
            if (currentAngle < 20) {
                intakeController.liftDown = true;
                intakeController.sample = false;
            }
        }

        if (!outtakeController.square) {
            angleS = 300;
        }

        extendingMotorControl.runToAngle(angle, 0.6, 1150, 1.0, DcMotorSimple.Direction.FORWARD);
        if (angle < 10 && down) {
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