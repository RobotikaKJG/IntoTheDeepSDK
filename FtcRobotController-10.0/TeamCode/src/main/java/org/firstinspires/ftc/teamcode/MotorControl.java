package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorControl {

    private final HardwareMap hardwareMap;
    private DcMotor motor;
    private double currentAngle = 0;

    public MotorControl(HardwareMap hardwareMap, String motorName, boolean reverse) {
        this.hardwareMap = hardwareMap;

        motor = hardwareMap.dcMotor.get(motorName);
        if (reverse) {
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToAngle(double angle, double power, int ticksPerRev, double gearRatio, DcMotorSimple.Direction direction) {
        // Set the motor direction and mode for running to a position
        motor.setDirection(direction);

        int targetPosition = angleToTicks(angle, ticksPerRev, gearRatio);
        motor.setTargetPosition(targetPosition);

        // Set the motor power
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
    }

    public void addMotorAngle(double Angle, double power, int ticksPerRev, double gearRatio) {
        // Calculate the target position based on the current motor position
        int currentTicks = motor.getCurrentPosition();
        int targetPosition = currentTicks + angleToTicks(Angle, ticksPerRev, gearRatio);

        // Set the motor to the target position
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
    }

    public void stop() {
        // Stop the motor and reset position to 0
        motor.setPower(0);  // Stop the motor

        // Reset encoder or set the mode back to RUN_USING_ENCODER for the next command
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // convert angle to ticks
    public int angleToTicks(double angle, int ticksPerRev, double gearRatio) {
        return (int) ((angle / 360.0) * ticksPerRev * gearRatio);
    }

    public void setMotorPower(double power) {
        motor.setPower(power);
    }

    public int getMotorTargetPosition(int position) {
        motor.setTargetPosition(position);
        return motor.getTargetPosition();
    }

    public int getMotorCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public boolean isMotorBusy() {
        return motor.isBusy();
    }

    public void resetMotorEncoder() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setMotorDirection(DcMotorSimple.Direction direction) {
        motor.setDirection(direction);
    }

    public void resetMotorAngle() {
        currentAngle = 0; // Reset the tracked angle
    }
}
