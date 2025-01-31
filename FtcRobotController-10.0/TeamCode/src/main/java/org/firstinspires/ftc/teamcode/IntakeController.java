package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private SubsystemState intakeState = SubsystemState.Idle;
    private final ServoControl firstServoControl;
    private final ServoControl secondServoControl;
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

    public IntakeController(EdgeDetection edgeDetection, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.firstServoControl = new ServoControl(hardwareMap, "outtakeServo1", false);
        this.secondServoControl = new ServoControl(hardwareMap, "outtakeServo2", false);
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
//        firstServoControl.setServoPos(1);
//        secondServoControl.setServoPos(0.04);

        setServoPositions(0.5);

        if (edgeDetection.rising(GamepadIndexValues.square)) {
            intakeState = SubsystemState.Run;
        }
    }

    @Override
    public void run() {
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {

//        firstServoControl.setServoPos(0.02);
//        secondServoControl.setServoPos(1);

        setServoPositions(0.0);

        if (edgeDetection.rising(GamepadIndexValues.square)) {
            intakeState = SubsystemState.Start;
        }
    }

    private void setServoPositions(double inputValue) {
        // Clamp input value to the range [0, 1]
        inputValue = Math.max(0, Math.min(inputValue, 1));

        // Calculate positions for both servos
        double firstServoPos = 0.02 + (0.98 * inputValue);  // Servo 1: 0.02 to 1
        double secondServoPos = 1 - (0.96 * inputValue);  // Servo 2: 1 to 0.02

        // Set servo positions
        firstServoControl.setServoPos(firstServoPos);
        secondServoControl.setServoPos(secondServoPos);
    }
}