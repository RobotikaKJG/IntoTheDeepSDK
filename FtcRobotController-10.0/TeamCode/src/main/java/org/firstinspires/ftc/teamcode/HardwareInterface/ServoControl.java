package org.firstinspires.ftc.teamcode.HardwareInterface;

import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoControl {

    private final HardwareMap hardwareMap;
    private Servo[] servos;
    private CRServoImpl[] crServos;

    public ServoControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        servoInit();
    }

    private void servoInit() {
        getServos();
        //setServoStartPos();
    }

    private void getServos() {

        servos = new Servo[]{
                hardwareMap.get(Servo.class, "outtakeLeftServo"),
                hardwareMap.get(Servo.class, "outtakeRightServo"),
                hardwareMap.get(Servo.class, "releaseServo")
        };

        crServos = new CRServoImpl[]{
                hardwareMap.get(CRServoImpl.class, "crServo") //placeholder
        };
    }

    public void setServoStartPos() {
        setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoIdlePos); //should be idlepos
        setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoIdlePos);
        if (GlobalVariables.isAutonomous)
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
        else
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
    }

    public double getServoPos(int index) {
        return servos[index].getPosition();
    }

    public void increasePos(int index) {
        double servoPosition = getServoPos(index);

        // First check for max pos overshooting due to unaligned increment before setting it to the servo
        if (servoPosition + ServoConstants.servoIncrement[index] > ServoConstants.servoMaxPos[index])
            servoPosition = ServoConstants.servoMaxPos[index];

        if (servoPosition < ServoConstants.servoMaxPos[index])
            servoPosition += ServoConstants.servoIncrement[index];

        setServoPos(index, servoPosition);
    }

    public void decreasePos(int index) {
        double servoPosition = getServoPos(index);

        // First check for min pos overshooting due to unaligned increment before setting it to the servo
        if (servoPosition - ServoConstants.servoIncrement[index] < ServoConstants.servoMinPos[index])
            servoPosition = ServoConstants.servoMinPos[index];

        if (servoPosition > ServoConstants.servoMinPos[index])
            servoPosition -= ServoConstants.servoIncrement[index];

        setServoPos(index, servoPosition);
    }

    public void setServoPos(int index, double position) {
        if (isInBounds(index, position))
            servos[index].setPosition(position);
    }

    private boolean isInBounds(int index, double position) {
        return position >= ServoConstants.servoMinPos[index] && position <= ServoConstants.servoMaxPos[index];
    }

    public void setServoSpeed(int index, double speed)
    {
        crServos[index].setPower(speed);
    }
}