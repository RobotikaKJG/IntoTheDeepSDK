package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeController implements RobotSubsystemController{

    public boolean risen = false;
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private final MotorControl outtakeMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    public double currentAngle = 0;
    private int bumperClicks = 0;
    public final int maxArmAngle = 650;
    private boolean hasLifted = false;
    public boolean goDown = false;

    public OuttakeController(EdgeDetection edgeDetection, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.outtakeMotorControl = new MotorControl(hardwareMap, "liftMotor", true);
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
        outtakeMotorControl.resetMotorEncoder();

        hasLifted = false;
        bumperClicks = 0;

        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        if (edgeDetection.rising(GamepadIndexValues.rightBumper)) {
            if (bumperClicks != 1) {
                bumperClicks++;
                risen = true;
                hasLifted = true;
                outtakeMotorControl.runToAngle(maxArmAngle, 0.8, 1150, 1, DcMotorSimple.Direction.REVERSE);
            }
        } else if (edgeDetection.rising(GamepadIndexValues.rightTrigger)) {
            if (bumperClicks != 0) {
                bumperClicks--;
                risen = false;
                outtakeMotorControl.runToAngle(0, 0.5, 1150, 1, DcMotorSimple.Direction.REVERSE);
            }
        }

        if (!risen && hasLifted && outtakeMotorControl.getMotorCurrentPosition() <= outtakeMotorControl.angleToTicks(maxArmAngle - 100, 1150, 1)) {
            goDown = true;
        }

        // Check if left and right bumper clicks balance out
        if (outtakeMotorControl.getMotorCurrentPosition() <= 5 && bumperClicks == 0 && hasLifted) {
            intakeState = SubsystemState.Stop;
            outtakeMotorControl.runToAngle(0, 0.1, 1150, 1, DcMotorSimple.Direction.REVERSE);
        }
    }

    @Override
    public void stop() {
        if (!outtakeMotorControl.isMotorBusy()) {
            // Once it reaches the position, stop the motor
            outtakeMotorControl.setMotorPower(0);

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