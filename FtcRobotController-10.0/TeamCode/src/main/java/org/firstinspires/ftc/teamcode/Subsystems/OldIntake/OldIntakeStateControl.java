package org.firstinspires.ftc.teamcode.Subsystems.OldIntake;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.StateSwitch;

public class OldIntakeStateControl {

    private final ServoControl servoControl;
    private final OldIntakeMotorControl oldIntakeMotorControl;

    public OldIntakeStateControl(MotorControl motorControl, ServoControl servoControl) {
        this.servoControl = servoControl;
        oldIntakeMotorControl = new OldIntakeMotorControl(motorControl);
    }

    public void takeIn() {
        oldIntakeMotorControl.motor(StateSwitch.downOn);
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMaxPos[ServoConstants.intake]);
    }

    public void openAndTakeIn()
    {
        servoControl.decreasePos(ServoConstants.release1);
        servoControl.decreasePos(ServoConstants.release2);
        takeIn();
    }

    public void pushOut() {
        oldIntakeMotorControl.motor(StateSwitch.upOn);
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMinPos[ServoConstants.intake]);
    }

    public void pushOut(double speed) {
        oldIntakeMotorControl.motor(StateSwitch.upOn,speed);
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMinPos[ServoConstants.intake]);
    }

    public void stop() {
        oldIntakeMotorControl.motor(StateSwitch.noOn);
    }

    public void moveDown(){
        servoControl.setServoPos(ServoConstants.intake, ServoConstants.servoMaxPos[ServoConstants.intake]);
    }
}
