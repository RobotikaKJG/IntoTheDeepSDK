package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;

public class PivotControl {
    private PivotStates prevPivotState;
    private MotorControl motorControl;

    public PivotControl(MotorControl motorControl) {
        this.motorControl = motorControl;
    }

    public void update() {
        if (prevPivotState != OuttakeStates.getPivotState()) {
            updateStates();
            prevPivotState = OuttakeStates.getPivotState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getPivotState()) {
            case up:
                motorControl.setMotorPos(MotorConstants.pivot, 145);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.5);
                break;
            case down:
                motorControl.setMotorPos(MotorConstants.pivot, 0);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.5);
                break;
            case idle:
                break;
        }
    }
}
