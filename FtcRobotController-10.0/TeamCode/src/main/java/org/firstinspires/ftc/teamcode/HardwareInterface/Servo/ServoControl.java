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
                hardwareMap.get(Servo.class, "outtakeServo"),
                hardwareMap.get(Servo.class, "outtakePivotServo"),
                hardwareMap.get(Servo.class, "releaseServo"),
                hardwareMap.get(Servo.class, "pivotServo")
        };
    }

    public void setServoStartPos() {
        setServoPos(ServoConstants.outtake, OuttakeConstants.outtakeServoMinPos); // idk for now, NIGHTNOTE
        setServoPos(ServoConstants.intake, IntakeConstants.intakeServoSubPos);

        if (GlobalVariables.isAutonomous) {
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
            setServoPos(ServoConstants.outtakePivot, OuttakeConstants.outtakePivotServoMinPos);

        }
        else {
            setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
            setServoPos(ServoConstants.outtakePivot, OuttakeConstants.outtakePivotServoMaxPos);
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