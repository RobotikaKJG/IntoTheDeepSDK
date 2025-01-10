package org.firstinspires.ftc.teamcode.Autonomous.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;

public class RunSubsystems implements Action {

    private final MotorControl motorControl;
    private final IntakeControl intakeControl;
    private final OuttakeControl outtakeControl;
    private final SensorControl sensorControl;

    public RunSubsystems (MotorControl motorControl, IntakeControl intakeControl, OuttakeControl outtakeControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.intakeControl = intakeControl;
        this.outtakeControl = outtakeControl;
        this.sensorControl = sensorControl;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        update();
        return GlobalVariables.isAutonomous;
    }

    public void update(){
        intakeControl.update();
        outtakeControl.update();
        sensorControl.updateColor();
        motorControl.setMotors(MotorConstants.notDrive);
    }
}
