package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousControl;
import org.firstinspires.ftc.teamcode.Autonomous.SampleAuton;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;

public class AutonomousDependencies extends Dependencies {

    public SampleMecanumDrive drive;
    private final SlideLogic intakeSlideLogic;

    public AutonomousDependencies(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {
        super(hardwareMap, gamepad1, telemetry);
        drive = new SampleMecanumDrive(hardwareMap);
        intakeSlideLogic = createIntakeSlideLogic();
    }

    public SampleAuton createSampleAuton() {
        return new SampleAuton(drive,elapsedTime,createOuttakeController(),createIntakeController(),intakeSlideLogic);
    }

    public AutonomousControl createAutonomousControl() {
        return new AutonomousControl(motorControl,createSampleAuton());
    }
}
