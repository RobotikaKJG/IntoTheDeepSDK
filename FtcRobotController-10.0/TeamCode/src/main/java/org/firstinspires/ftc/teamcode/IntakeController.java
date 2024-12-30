package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final MotorControl motorControl;
    private final HardwareMap hardwareMap;
    private final OuttakeController outtakeController;
    private SubsystemState intakeState = SubsystemState.Idle;
    private final MotorControl extendingMotorControl;
    private final ServoControl intakeServoControl;
    private final ServoControl turnServoControl;
    private final ServoControl liftServoControl;
    private final double power = 0.5;
    private boolean circle = false;
    private boolean hasRotated = false;
    private int increasement = 10;
    private double currentAngle = 0; // Current angle of the motor
    public boolean open = true;
    private double currentTServoPos = 0.28;

    // Limits for motor movement
    private static final double minAngle = 0;    // Minimum angle (degrees)
    private static final double maxAngle = 2000; // Maximum angle (degrees)

    public IntakeController(EdgeDetection edgeDetection, MotorControl motorControl, HardwareMap hardwareMap, OuttakeController outtakeController) {
        this.edgeDetection = edgeDetection;
        this.motorControl = motorControl;
        this.hardwareMap = hardwareMap;
        this.intakeServoControl = new ServoControl(hardwareMap, "intakeServo", false);
        this.turnServoControl = new ServoControl(hardwareMap, "turnServo", false);
        this.liftServoControl = new ServoControl(hardwareMap, "liftServo", false);
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
        if (edgeDetection.rising(GamepadIndexValues.cross)) {
            if (!open) {
                intakeServoControl.setServoPos(0.3);
                open = true;
            } else {
                intakeServoControl.setServoPos(0.66);

                open = false;
            }
        }



        if (edgeDetection.rising(GamepadIndexValues.dpadRight)) {
            if (currentTServoPos < 0.62) {
                currentTServoPos += 0.17;
                turnServoControl.setServoPos(currentTServoPos);
            }
        }

        // Handle dpadLeft for turning 45 degrees counterclockwise
        if (edgeDetection.rising(GamepadIndexValues.dpadLeft)) {
            if (currentTServoPos > 0.28) {
                currentTServoPos -= 0.17;
                turnServoControl.setServoPos(currentTServoPos);
            }
        }



        if (edgeDetection.rising(GamepadIndexValues.dpadUp)) {
            liftServoControl.setServoPos(0.94);
            turnServoControl.setServoPos(0.28);
        }
        if (edgeDetection.rising(GamepadIndexValues.dpadDown)) {
            liftServoControl.setServoPos(0.3);
        }
    }

    @Override
    public void run() {
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
//        servoControl.setServoSpeed(0);
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        intakeServoControl.setServoPos(0.3);
        turnServoControl.setServoPos(0.28);
        currentTServoPos = 0.28;
        liftServoControl.setServoPos(0.62);
        intakeState = SubsystemState.Start;
    }
}