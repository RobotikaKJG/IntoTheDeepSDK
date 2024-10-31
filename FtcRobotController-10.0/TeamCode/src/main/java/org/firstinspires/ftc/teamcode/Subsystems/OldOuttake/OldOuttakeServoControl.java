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
                servoControl.increasePos(ServoConstants.outtake);
                break;

            case upOn:
                //decreasing the position raises the servo, as default state is maxPos
                servoControl.decreasePos(ServoConstants.outtake);
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
                servoControl.decreasePos(ServoConstants.release1);
                break;

            case openSecond: // first one open, releases second one
                servoControl.decreasePos(ServoConstants.release2);
                break;

            case closeBoth: // closes both
                servoControl.increasePos(ServoConstants.release1);
                servoControl.increasePos(ServoConstants.release2);
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
        return servoControl.getServoPos(ServoConstants.release1) >= OldOuttakeConstants.releaseServoMaxPos &&  servoControl.getServoPos(ServoConstants.release2) >= OldOuttakeConstants.releaseServoMaxPos;
    }

    public boolean isServoOpen()
    {
        return servoControl.getServoPos(ServoConstants.outtake) <= OldOuttakeConstants.outtakeServoMinPos;
    }
}
