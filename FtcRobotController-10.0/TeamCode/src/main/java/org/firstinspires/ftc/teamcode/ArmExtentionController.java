package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.lang.Math;

public class ArmExtentionController implements RobotSubsystemController {
    private final EdgeDetection edgeDetection;
    private final HardwareMap hardwareMap;
    private final MotorControl motorMotorControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    private double voltageStep = 0.01;  // Increment step
    private double maxVoltage = 1.0;    // Maximum motor power
    private double minVoltage = 0.0;    // Minimum motor power
    public double voltage = 0.0;

    public ArmExtentionController(EdgeDetection edgeDetection, HardwareMap hardwareMap) {
        this.edgeDetection = edgeDetection;
        this.hardwareMap = hardwareMap;
        this.motorMotorControl = new MotorControl(hardwareMap, "Motor", true);
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
        // Check button presses using edge detection
        if (edgeDetection.rising(GamepadIndexValues.dpadUp)) {
            voltage = Math.min(voltage + voltageStep, maxVoltage);
        }
        if (edgeDetection.rising(GamepadIndexValues.dpadDown)) {
            voltage = Math.max(voltage - voltageStep, minVoltage);
        }

        // Apply the voltage to the motor
        motorMotorControl.setMotorPower(voltage);

        // Stop when pressing circle
        if (edgeDetection.rising(GamepadIndexValues.circle)) {
            intakeState = SubsystemState.Stop;
        }
    }

    // Getter methods for telemetry in TeleOpController
    public int getMotorPosition() {
        return motorMotorControl.getMotorCurrentPosition();
    }

    public boolean isMotorBusy() {
        return motorMotorControl.isMotorBusy();
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
}
