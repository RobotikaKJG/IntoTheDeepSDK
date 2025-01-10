package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Autonomous.Actions.RunSubsystems;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;

public class AutonomousActions {
    MotorControl motorControl;
    IntakeControl intakeControl;
    OuttakeControl outtakeControl;
    SensorControl sensorControl;


    Action runSubsystems(){
        return new RunSubsystems(motorControl,intakeControl,outtakeControl,sensorControl);
    }
}
