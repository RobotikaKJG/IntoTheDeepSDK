package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Old;

import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class OuttakeServoController {
    private final ServoControl servoControl;

    public OuttakeServoController(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void setServoState(OuttakeServoStates state)
    {
        switch(state){
            case downOpen:
                downOpen();
                break;
            case downClose:
                downClose();
                break;
            case upOpen:
                upOpen();
                break;
            case upClose:
                upClose();
                break;
        }
    }

    private void upClose() {
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMaxPos);
        servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
    }

    private void upOpen() {
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMaxPos);
        servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoReleasePos);
    }

    private void downClose() {
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMaxPos);
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMinPos);
        servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
    }

    private void downOpen() {
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoIdlePos);
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoIdlePos);
        servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
    }

    private void upRetracted(){

    }
}
