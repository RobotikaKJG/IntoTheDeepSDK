package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.lang.Math;

public class ArmExtentionController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final MotorControl motorControl;
    private final HardwareMap hardwareMap;
    private final OuttakeController outtakeController;
    private final MotorControl extendingMotorControl;
    private final MotorControl armMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    public double angle = 0;
    public double currentAngle = 0;
    public double angleIncrement = 3;
    private boolean up = false;
    private boolean down = false;
    private boolean border = false;
    private final int maxExtentionAngle = 310;
    private boolean extended = false;

    public ArmExtentionController(EdgeDetection edgeDetection, MotorControl motorControl, HardwareMap hardwareMap, OuttakeController outtakeController) {
        this.edgeDetection = edgeDetection;
        this.motorControl = motorControl;
        this.hardwareMap = hardwareMap;
        this.extendingMotorControl = new MotorControl(hardwareMap, "extendingMotor", true);
        this.armMotorControl = new MotorControl(hardwareMap, "armMotor", true);
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
        extended = false;
        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        currentAngle = extendingMotorControl.getMotorCurrentPosition();
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

        if (edgeDetection.rising(GamepadIndexValues.dpadUp)) {
            border = !border;
            angle = 0;

            if (border) {
                armMotorControl.runToAngle(120, 0.5, 1150, 1, DcMotorSimple.Direction.FORWARD);
            } else {
                armMotorControl.runToAngle(0, 0.5, 1150, 1, DcMotorSimple.Direction.FORWARD);
            }
        }


        if (!outtakeController.risen) {
            if (!border) {
                if (angle > 100 && !extended) {
                    extended = true;
                    armMotorControl.runToAngle(130 * Math.sin(Math.PI / (2 * 370) * angle) - 16, 0.5, 1150, 1, DcMotorSimple.Direction.FORWARD);
                }
                else if (extended) {
                    armMotorControl.runToAngle(130 * Math.sin(Math.PI / (2 * 370) * angle) - 16, 0.5, 1150, 1, DcMotorSimple.Direction.FORWARD);
                }
            } else {
                if (angle > 250) {
                    armMotorControl.runToAngle(130 * Math.sin(Math.PI / (2 * 370) * angle) - 16, 0.5, 1150, 1, DcMotorSimple.Direction.FORWARD);
                }
            }
        }

        extendingMotorControl.runToAngle(angle, 0.6, 1150, 1.0, DcMotorSimple.Direction.FORWARD);
        if (angle < 5 && down) {
            extendingMotorControl.runToAngle(0, 1, 1150, 1.0, DcMotorSimple.Direction.FORWARD);
            angle = 0;
            extended = false;
            intakeState = SubsystemState.Stop;
        }

    }

    @Override
    public void stop() {
        if (!extendingMotorControl.isMotorBusy()) {
            down = false;
            up = false;
            extended = false;
            extendingMotorControl.setMotorPower(0);
            intakeState = SubsystemState.Idle;
        }
    }

    @Override
    public void idle() {
        intakeState = SubsystemState.Start;
    }
}
