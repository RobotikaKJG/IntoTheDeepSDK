package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

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
        if (OuttakeStates.getPivotState() == PivotStates.upFast) {
            if (motorControl.getMotorPosition(MotorConstants.pivot) > 150) {
                OuttakeStates.setPivotStates(PivotStates.up);
            }
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getPivotState()) {
            case upFast:
                motorControl.setMotorPos(MotorConstants.pivot, 200);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.7);
                break;
            case up:
                motorControl.setMotorPos(MotorConstants.pivot, 550);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.3);
                break;
            case down:
                motorControl.setMotorPos(MotorConstants.pivot, 0);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.3);
                break;
            case idle:
                break;
        }
    }
}
