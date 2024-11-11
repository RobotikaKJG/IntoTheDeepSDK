package org.firstinspires.ftc.teamcode.HardwareInterface;

import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Drone.DroneConstants;
import org.firstinspires.ftc.teamcode.Subsystems.OldIntake.OldIntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.OldOuttake.OldOuttakeConstants;


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
                hardwareMap.get(Servo.class, "intakeServo"),
                hardwareMap.get(Servo.class, "outtakeServo"),
                hardwareMap.get(Servo.class, "releaseServo1"),
                hardwareMap.get(Servo.class, "releaseServo2"),
                hardwareMap.get(Servo.class, "droneServo")
        };

        crServos = new CRServoImpl[]{
                hardwareMap.get(CRServoImpl.class, "crServo") //placeholder
        };
    }

//    private void setServoStartPos() {
//        if(isAutonomous)
//            setServoPos(ServoConstants.intake, OldIntakeConstants.intakeServoAutonStartPos);
//        else
//            setServoPos(ServoConstants.intake, OldIntakeConstants.intakeServoMinPos);
//        setServoPos(ServoConstants.outtake, OldOuttakeConstants.outtakeServoMaxPos);
//        setServoPos(ServoConstants.release1, OldOuttakeConstants.releaseServoMinPos);
//        setServoPos(ServoConstants.release2, OldOuttakeConstants.releaseServoMinPos);
//        setServoPos(ServoConstants.drone, DroneConstants.droneServoMinPos);
//    }

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