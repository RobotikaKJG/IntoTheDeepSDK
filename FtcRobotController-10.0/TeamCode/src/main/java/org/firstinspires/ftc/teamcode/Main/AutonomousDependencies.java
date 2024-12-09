package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousControl;
//import org.firstinspires.ftc.teamcode.Autonomous.NewSampleAuton;
import org.firstinspires.ftc.teamcode.Autonomous.SampleAuton;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;

public class AutonomousDependencies extends Dependencies {

    public SampleMecanumDrive drive;
    public final AutonomousControl autonomousControl;

    public AutonomousDependencies(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {
        super(hardwareMap, gamepad1, telemetry);
        drive = new SampleMecanumDrive(hardwareMap);
        autonomousControl = createAutonomousControl();
    }

    public SampleAuton createSampleAuton() {
        return new SampleAuton(drive,elapsedTime,autonomousControl);
    }

    public AutonomousControl createAutonomousControl() {
        return new AutonomousControl(motorControl,createSampleAuton(),createIntakeControl(),createOuttakeControl(),sensorControl);
    }
}
