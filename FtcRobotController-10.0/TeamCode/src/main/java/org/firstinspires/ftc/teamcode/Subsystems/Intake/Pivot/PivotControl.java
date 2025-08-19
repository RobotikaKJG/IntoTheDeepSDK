package org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class PivotControl {
    private SensorControl sensorControl;
    private PivotStates prevPivotState;
    private MotorControl motorControl;
    private int targetPosition = 0;

    public PivotControl(MotorControl motorControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
    }

    public void update() {
        if (prevPivotState != IntakeStates.getPivotState()) {
            updateStates();
            prevPivotState = IntakeStates.getPivotState();
        }
        if(IntakeStates.getPivotState() == PivotStates.goingDown) {
            if(pivotBottomReached()) {
                IntakeStates.setPivotState(PivotStates.isDown);
            }
        }
    }

    private void updateStates() {
        switch (IntakeStates.getPivotState()) {
            case up:
                motorControl.setMotorPos(MotorConstants.pivot, IntakeConstants.pivotMaxAngle);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.7);
                motorControl.setMotors(MotorConstants.pivot);
                break;
            case upSlightly:
                motorControl.setMotorPos(MotorConstants.pivot, IntakeConstants.pivotSlightlyRaisedAngle);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, 0.7);
                motorControl.setMotors(MotorConstants.pivot);
                break;
            case down:
                motorControl.setMotorPos(MotorConstants.pivot, 69);
                motorControl.setMotorMode(MotorConstants.pivot, DcMotor.RunMode.RUN_TO_POSITION);
                motorControl.setMotorSpeed(MotorConstants.pivot, IntakeConstants.pivotDownSpeed);
                IntakeStates.setPivotState(PivotStates.goingDown);
                break;
            case goingDown:
                break;
            case isDown:
                break;
            case idle:
                break;
        }
    }

    private boolean pivotBottomReached() {
        return retractPivot(MotorConstants.pivot, LimitSwitches.pivot);
    }

    private boolean retractPivot(int slide, LimitSwitches limitSwitch) {
        if(sensorControl.isLimitSwitchPressed(limitSwitch)){
            motorControl.setMotorMode(slide, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorControl.setMotorSpeed(slide, 0);
            motorControl.setMotors(slide);
            return true;
        }

        if(motorControl.getMotorPosition(slide) > IntakeConstants.limitSwitchThreshold)
            return false;

        targetPosition -= IntakeConstants.limitSwitchRetractionStep;
        motorControl.setMotorSpeed(MotorConstants.pivot, IntakeConstants.pivotDownSpeed);
        motorControl.setMotors(MotorConstants.pivot);
        motorControl.setMotorPos(slide, targetPosition);
        return false;
    }
}
