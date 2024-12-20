package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.OuttakeController;

public class IntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final ServoControl servoControl;
    private final MotorControl motorControl;
    private final HardwareMap hardwareMap;
    private final OuttakeController outtakeController;
    private SubsystemState intakeState = SubsystemState.Idle;
    private final MotorControl extendingMotorControl;
    private final double power = 0.5;
    private boolean circle = false;
    private boolean hasRotated = false;
    private int increasement = 10;
    private double currentAngle = 0; // Current angle of the motor

    // Limits for motor movement
    private static final double minAngle = 0;    // Minimum angle (degrees)
    private static final double maxAngle = 2000; // Maximum angle (degrees)

    public IntakeController(ServoControl servoControl, EdgeDetection edgeDetection, MotorControl motorControl, HardwareMap hardwareMap, OuttakeController outtakeController) {
        this.edgeDetection = edgeDetection;
        this.servoControl = servoControl;
        this.motorControl = motorControl;
        this.hardwareMap = hardwareMap;
        this.extendingMotorControl = new MotorControl(hardwareMap, "extendingMotor", true);
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
        if (circle && !outtakeController.risen) {
//            servoControl.setServoSpeed(1);
            servoControl.setServoPos(0.35);
            circle = false;
        } else if (!circle) {
//            servoControl.setServoSpeed(-1);
            servoControl.setServoPos(0);
        }

        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        if (edgeDetection.rising(GamepadIndexValues.circle) || edgeDetection.rising(GamepadIndexValues.cross)) {
            intakeState = SubsystemState.Stop;
        }
    }

    @Override
    public void stop() {
//        servoControl.setServoSpeed(0);
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        // In idle state, wait for the right trigger press to restart the process
        if (edgeDetection.rising(GamepadIndexValues.circle)) {
            circle = true;
            intakeState = SubsystemState.Start;
        }
        if (edgeDetection.rising(GamepadIndexValues.cross)) {
            circle = false;
            intakeState = SubsystemState.Start;
        }
    }
}