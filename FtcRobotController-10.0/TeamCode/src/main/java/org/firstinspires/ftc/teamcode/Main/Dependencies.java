package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Drone.DroneController;
import org.firstinspires.ftc.teamcode.Subsystems.Hang.HangController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStateControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeDependencies;

public class Dependencies {
    public final HardwareMap hardwareMap;
    public final Gamepad gamepad1;
    public final Telemetry telemetry;
    public final StandardTrackingWheelLocalizer localizer;
    public MotorControl motorControl;
    public SensorControl sensorControl;
    public ServoControl servoControl;
    public EdgeDetection edgeDetection = new EdgeDetection();
    public SampleMecanumDrive drive;
    public ElapsedTime elapsedTime = new ElapsedTime();
    public OuttakeDependencies outtakeDependencies;

    public Dependencies(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
        localizer = new StandardTrackingWheelLocalizer(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        motorControl = new MotorControl(hardwareMap);
        sensorControl = new SensorControl(hardwareMap, gamepad1, localizer);
        servoControl = new ServoControl(hardwareMap);
        outtakeDependencies = new OuttakeDependencies(motorControl, servoControl, sensorControl);
    }

    public IntakeStateControl createIntakeStateControl() {
        return new IntakeStateControl(motorControl, servoControl);
    }

    public Drivebase createDrivebase() {
        return new Drivebase(gamepad1, motorControl, sensorControl);
    }

    public IntakeController createIntakeController() {
        return new IntakeController(createIntakeStateControl(), servoControl, edgeDetection, elapsedTime);
    }

    public OuttakeController createOuttakeController() {
        return new OuttakeController(outtakeDependencies, edgeDetection, telemetry);
    }

    public HangController createHangController() {
        return new HangController(outtakeDependencies.slideControl, edgeDetection);
    }

    public DroneController createDroneController() {
        return new DroneController(servoControl, edgeDetection);
    }

    public DrivebaseController createDrivebaseController() {
        return new DrivebaseController(createDrivebase(), edgeDetection);
    }
}
