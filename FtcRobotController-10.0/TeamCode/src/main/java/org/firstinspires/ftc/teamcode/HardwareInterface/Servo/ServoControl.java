package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
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
                hardwareMap.get(Servo.class, "outtakeArmServo"),
                hardwareMap.get(Servo.class, "outtakeClawTurnServo"),
                hardwareMap.get(Servo.class, "specimenClawServo"),
                hardwareMap.get(Servo.class, "outtakeDownServo"),
                hardwareMap.get(Servo.class, "intakeServo")
        };
    }

    public void setServoStartPos() {
        setServoPos(ServoConstants.outtakeArm, OuttakeConstants.outtakeArmServoIdlePos);
        setServoPos(ServoConstants.outtakeClawTurn, OuttakeConstants.outtakeClawTurnServoMinPos);
        setServoPos(ServoConstants.outtakeDown, OuttakeConstants.outtakeDownServoMaxPos);

        if (GlobalVariables.isAutonomous) {
            setServoPos(ServoConstants.specimenClaw, OuttakeConstants.specimenClawServoMaxPos);
        }
        else {
            setServoPos(ServoConstants.specimenClaw, OuttakeConstants.specimenClawServoMinPos);
        }
    }

    public void setServoPos(int index, double position) {
        if (isInBounds(index, position))
            servos[index].setPosition(position);
    }

    private boolean isInBounds(int index, double position) {
        return position >= ServoConstants.servoMinPos[index] && position <= ServoConstants.servoMaxPos[index];
    }
}