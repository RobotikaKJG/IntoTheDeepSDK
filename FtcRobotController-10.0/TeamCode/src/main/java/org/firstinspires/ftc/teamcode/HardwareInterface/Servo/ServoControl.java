package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoControl {

    private final HardwareMap hardwareMap;
    private Servo[] servos;

    public ServoControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        getServos();
    }

    private void getServos() {

        servos = new Servo[]{
                hardwareMap.get(Servo.class, "outtakeLeftServo"),
                hardwareMap.get(Servo.class, "outtakeRightServo"),
                hardwareMap.get(Servo.class, "releaseServo"),
                hardwareMap.get(Servo.class, "specimenClawServo"),
                hardwareMap.get(Servo.class, "intakeServo"),
                hardwareMap.get(Servo.class,"lockServo"),
                hardwareMap.get(Servo.class, "PTORight"),
                hardwareMap.get(Servo.class, "PTOLeft")
        };
        setServoDirection(ServoConstants.release, Servo.Direction.REVERSE);

    }

    public void setServoStartPos() {

        if (GlobalVariables.isAutonomous) {
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
            setServoPos(ServoConstants.lock, IntakeConstants.lockServoMinPos);
            setServoPos(ServoConstants.specimenClaw, OuttakeConstants.specimenClawServoMaxPos);
            setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoStartPos);
            setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoStartPos);
        }
        else {
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
            setServoPos(ServoConstants.specimenClaw, OuttakeConstants.specimenClawServoMinPos);
            setServoPos(ServoConstants.lock, IntakeConstants.lockServoMaxPos);
            setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
            setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMinPos);
        }

        setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMinPos);
    }

    public void setServoPos(int index, double position) {
        if (isInBounds(index, position))
            servos[index].setPosition(position);
    }

    private boolean isInBounds(int index, double position) {
        return position >= ServoConstants.servoMinPos[index] && position <= ServoConstants.servoMaxPos[index];
    }

    private void setServoDirection(int index, Servo.Direction direction){
        servos[index].setDirection(direction);
    }
}