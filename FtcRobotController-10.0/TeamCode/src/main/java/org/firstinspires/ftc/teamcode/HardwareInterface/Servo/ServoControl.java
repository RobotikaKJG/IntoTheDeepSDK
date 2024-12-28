package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoControl {

    private final HardwareMap hardwareMap;
    private Servo[] servos;
    private CRServoImpl[] crServos;

    public ServoControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        getServos();
    }

    private void getServos() {

        servos = new Servo[]{
                hardwareMap.get(Servo.class, "outtakeLeftServo"),
                hardwareMap.get(Servo.class, "outtakeRightServo"),
                hardwareMap.get(Servo.class, "releaseServo"),
                hardwareMap.get(Servo.class, "intakeServo")
        };

        crServos = new CRServoImpl[]{
                hardwareMap.get(CRServoImpl.class, "crServo") //placeholder
        };
    }

    public void setServoStartPos() {
        setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMaxPos); //should be idlepos
        setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMaxPos);
        if (GlobalVariables.isAutonomous)
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
        else
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
        //setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);

        setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMinPos);
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