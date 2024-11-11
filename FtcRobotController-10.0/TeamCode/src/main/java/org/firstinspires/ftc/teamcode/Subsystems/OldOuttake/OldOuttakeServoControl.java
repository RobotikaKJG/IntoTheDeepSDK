package org.firstinspires.ftc.teamcode.Subsystems.OldOuttake;

import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.ReleaseServoState;
import org.firstinspires.ftc.teamcode.Enums.StateSwitch;

public class OldOuttakeServoControl {
    private final ServoControl servoControl;
    private ReleaseServoState releaseState = ReleaseServoState.openSecond; //start with both open

    public OldOuttakeServoControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void turnServo(StateSwitch stateSwitch) {
        switch (stateSwitch) {
            case downOn:
                // Servo starts at max position, which is fully lowered
                servoControl.increasePos(ServoConstants.outtakeLeft);
                break;

            case upOn:
                //decreasing the position raises the servo, as default state is maxPos
                servoControl.decreasePos(ServoConstants.outtakeLeft);
                break;
        }
    }

    public void controlReleaseServo() {
        iterateReleaseServoState();
        releaseServoControl(releaseState);

    }

    //move to the next servo state
    public void iterateReleaseServoState() {
        switch (releaseState) {
            case openFirst: // closed, releases first one
                releaseState = ReleaseServoState.openSecond;
                break;

            case openSecond: // first one open, releases second one
                releaseState = ReleaseServoState.closeBoth;
                break;

            case closeBoth: // closes both
                releaseState = ReleaseServoState.openFirst;
                break;
        }
    }

    // state variable needed due to intake stopping servo close logic
    public void releaseServoControl(ReleaseServoState state) {
        switch (state) {
            case openFirst: // closed, releases first one
                servoControl.decreasePos(ServoConstants.outtakeRight);
                break;

            case openSecond: // first one open, releases second one
                servoControl.decreasePos(ServoConstants.release);
                break;

            case closeBoth: // closes both
                servoControl.increasePos(ServoConstants.outtakeRight);
                servoControl.increasePos(ServoConstants.release);
                break;
        }
    }
    public void checkReleaseServoPos()
    {
        if(areBothServosMaxPos())
            releaseState = ReleaseServoState.closeBoth;
    }

    private boolean areBothServosMaxPos()
    {
        return servoControl.getServoPos(ServoConstants.outtakeRight) >= OldOuttakeConstants.releaseServoMaxPos &&  servoControl.getServoPos(ServoConstants.release) >= OldOuttakeConstants.releaseServoMaxPos;
    }

    public boolean isServoOpen()
    {
        return servoControl.getServoPos(ServoConstants.outtakeLeft) <= OldOuttakeConstants.outtakeServoMinPos;
    }
}
