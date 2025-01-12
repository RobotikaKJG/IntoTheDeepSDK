package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private final OuttakeController outtakeController;
    private SubsystemState intakeState = SubsystemState.Idle;
    private final ServoControl intakeServoControl;
    private final ServoControl turnServoControl;
    private final ServoControl liftServoControl;
    private final double power = 0.5;
    private boolean circle = false;
    private boolean hasRotated = false;
    private double currentAngle = 0;
    public boolean open = true;
    public boolean liftDown = false;
    private double currentTServoPos = 0.28;
    public boolean isUp = true;
    public boolean wasDown = false;
    public boolean sample = false;

    public IntakeController(EdgeDetection edgeDetection, HardwareMap hardwareMap, OuttakeController outtakeController) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.outtakeController = outtakeController;

        this.intakeServoControl = new ServoControl(hardwareMap, "intakeServo", false);
        this.turnServoControl = new ServoControl(hardwareMap, "turnServo", false);
        this.liftServoControl = new ServoControl(hardwareMap, "liftServo", false);
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
        if (outtakeController.risen && !outtakeController.square) {
            liftServoControl.setServoPos(0.78);
            turnServoControl.setServoPos(0.28);
            isUp = false;
        } else if (outtakeController.goDown) {
            liftServoControl.setServoPos(0.94);
            turnServoControl.setServoPos(0.28);
            isUp = true;
            liftDown = false;
            sample = false;
            outtakeController.goDown = false;
        }

        if (edgeDetection.rising(GamepadIndexValues.circle) && !outtakeController.risen) {
            circle = !circle;
            if (circle) {
                liftServoControl.setServoPos(0.62);
                isUp = false;
            } else {
                liftServoControl.setServoPos(0.94);
                isUp = true;
            }
        }

        if (outtakeController.square) {
            liftServoControl.setServoPos(0.87);
            turnServoControl.setServoPos(0.96);
            if (edgeDetection.rising(GamepadIndexValues.cross) && !sample) {
                sample = true;
            }
            if (liftDown) {
                liftServoControl.setServoPos(1);
            }
            if (liftDown && liftServoControl.getServoPos() > 0.99) {
                open = true;
            }
            if (open && edgeDetection.falling(GamepadIndexValues.cross)) {
                intakeServoControl.setServoPos(0.3);
            }
        }

        if (edgeDetection.rising(GamepadIndexValues.cross) && !isUp) {
            if (open) {
                intakeServoControl.setServoPos(0.57);
                open = false;
            } else {
                intakeServoControl.setServoPos(0.3);
                open = true;
            }
        }

        if (edgeDetection.rising(GamepadIndexValues.dpadRight) && !outtakeController.risen && !isUp && !circle) {
            if (currentTServoPos < 0.62) {
                currentTServoPos += 0.17;
                turnServoControl.setServoPos(currentTServoPos);
            }
        }

        if (edgeDetection.rising(GamepadIndexValues.dpadLeft) && !outtakeController.risen && !isUp && !circle) {
            if (currentTServoPos > 0.11) {
                currentTServoPos -= 0.17;
                turnServoControl.setServoPos(currentTServoPos);
            }
        }

        if (edgeDetection.rising(GamepadIndexValues.dpadUp) && !outtakeController.risen && !circle) {
            liftServoControl.setServoPos(0.94);
            turnServoControl.setServoPos(0.28);
            currentTServoPos = 0.28;
            isUp = true;
        }

        if (edgeDetection.rising(GamepadIndexValues.dpadDown) && !outtakeController.risen && !circle) {
            liftServoControl.setServoPos(0.3);
            wasDown = true;
            isUp = false;
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
        intakeServoControl.setServoPos(0.3);
        turnServoControl.setServoPos(0.28);
        currentTServoPos = 0.28;
        liftServoControl.setServoPos(0.94);
        intakeState = SubsystemState.Start;
    }
}