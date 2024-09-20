package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.StateSwitch;

public class IntakeStateControl {

    private final ServoControl servoControl;
    private final IntakeMotorControl intakeMotorControl;

    public IntakeStateControl(MotorControl motorControl, ServoControl servoControl) {
        this.servoControl = servoControl;
        intakeMotorControl = new IntakeMotorControl(motorControl);
    }

    public void takeIn() {
        intakeMotorControl.motor(StateSwitch.downOn);
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMaxPos[ServoConstants.intake]);
    }

    public void openAndTakeIn()
    {
        servoControl.decreasePos(ServoConstants.release1);
        servoControl.decreasePos(ServoConstants.release2);
        takeIn();
    }

    public void pushOut() {
        intakeMotorControl.motor(StateSwitch.upOn);
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMinPos[ServoConstants.intake]);
    }

    public void pushOut(double speed) {
        intakeMotorControl.motor(StateSwitch.upOn,speed);
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMinPos[ServoConstants.intake]);
    }

    public void stop() {
        intakeMotorControl.motor(StateSwitch.noOn);
    }

    public void moveDown(){
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMaxPos[ServoConstants.intake]);
    }
}
