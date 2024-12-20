package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeController implements RobotSubsystemController{

    public boolean risen = false;
    private final EdgeDetection edgeDetection;
    private final ServoControl servoControl;
    private final MotorControl motorControl;
    private final HardwareMap hardwareMap;
    private final MotorControl outtakeMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    private int initialPosition;
    public double currentAngle = 0;
    private int rightBumperClicks = 0;
    private int bumperClicks = 0;
    public final int maxArmAngle = 930;

    public OuttakeController(EdgeDetection edgeDetection, ServoControl servoControl, MotorControl motorControl, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.servoControl = servoControl;
        this.hardwareMap = hardwareMap;
        this.motorControl = motorControl;
        this.outtakeMotorControl = new MotorControl(hardwareMap, "armMotor", true);
    }

    @Override
    public void updateState() {
        currentAngle = outtakeMotorControl.getMotorCurrentPosition();
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
        motorControl.resetMotorEncoder();
        initialPosition = motorControl.getMotorCurrentPosition();

        rightBumperClicks = 0;
        bumperClicks = 0;

        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        if (edgeDetection.rising(GamepadIndexValues.rightBumper)) {
            if (bumperClicks != 1) {
                bumperClicks++;
                rightBumperClicks++;
                risen = true;
                outtakeMotorControl.runToAngle(maxArmAngle, 0.8, 1150, 1, DcMotorSimple.Direction.REVERSE);
            }
        } else if (edgeDetection.rising(GamepadIndexValues.rightTrigger)) {
            if (bumperClicks != 0) {
                bumperClicks--;
                risen = false;
                outtakeMotorControl.runToAngle(0, 0.5, 1150, 1, DcMotorSimple.Direction.REVERSE);
            }
        }

        // Check if left and right bumper clicks balance out
        if (outtakeMotorControl.getMotorCurrentPosition() <= 5 && bumperClicks == 0 && rightBumperClicks > 0) {
            intakeState = SubsystemState.Stop;
        }
    }

    @Override
    public void stop() {
        if (!outtakeMotorControl.isMotorBusy()) {
            // Once it reaches the position, stop the motor
            outtakeMotorControl.setMotorPower(0);

            // Reset click counters and transition to Idle state
            rightBumperClicks = 0;
            bumperClicks = 0;
            risen = false;

            // Synchronize the current angle with the motor's actual position
            outtakeMotorControl.resetMotorAngle();

            intakeState = SubsystemState.Idle;
        }
    }

    @Override
    public void idle() {
        intakeState = SubsystemState.Start;

    }
}