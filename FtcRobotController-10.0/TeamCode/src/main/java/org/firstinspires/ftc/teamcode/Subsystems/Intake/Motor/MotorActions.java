package org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.Actions.MotorBackward;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.Actions.MotorForward;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.Actions.MotorIdle;

public class MotorActions {

    public Action rotateForward(){
        return new MotorForward();
    }
    public Action rotateBackward(){
        return new MotorBackward();
    }
    public Action stop(){
        return new MotorIdle();
    }
}
