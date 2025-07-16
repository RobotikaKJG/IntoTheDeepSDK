package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmExtentionController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private final MotorControl rightMotorControl;
    private final MotorControl leftMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    public int angle = 0;

    public ArmExtentionController(EdgeDetection edgeDetection, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.rightMotorControl = new MotorControl(hardwareMap, "rightMotor", true);
        this.leftMotorControl = new MotorControl(hardwareMap, "leftMotor", false);
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
        intakeState = SubsystemState.Run;
    }

    public void run() {
        if (edgeDetection.rising(GamepadIndexValues.square)) {
            rightMotorControl.runToAngle(710, 0.4, 1140, 1, DcMotorSimple.Direction.REVERSE);
            leftMotorControl.runToAngle(710, 0.4, 1140, 1, DcMotorSimple.Direction.FORWARD);
            angle = 500;
            // 2250 ticks for the slides
        }

        if (edgeDetection.rising(GamepadIndexValues.cross)) {
            rightMotorControl.addMotorAngle(5, 1, 1140, 1);
            leftMotorControl.addMotorAngle(5, 1, 1140, 1);
            angle += 5;
        }

        // Stop when pressing circle
        if (edgeDetection.rising(GamepadIndexValues.circle)) {
            intakeState = SubsystemState.Stop;
        }
    }

    @Override
    public void stop() {
        intakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (edgeDetection.rising(GamepadIndexValues.circle)) {
            intakeState = SubsystemState.Start;
        }
    }



    // Getter methods for telemetry in TeleOpController
    public int getMotorPosition() {
        return rightMotorControl.getMotorCurrentPosition();
    }

    public boolean isMotorBusy() {
        return rightMotorControl.isMotorBusy();
    }
}
