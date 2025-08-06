package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonControl;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleClaw.SampleClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.OuttakeSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.OuttakeSlideProperties;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.VerticalSlideControl;

public class Dependencies {
    public final HardwareMap hardwareMap;
    public final Gamepad gamepad1;
    public final Gamepad gamepad2;
    public final Telemetry telemetry;
    public final StandardTrackingWheelLocalizer localizer;
    public MotorControl motorControl;
    public SensorControl sensorControl;
    public ServoControl servoControl;
    public EdgeDetection edgeDetection = new EdgeDetection();
    public EdgeDetection gamepad2EdgeDetection = new EdgeDetection();
    private final SlideLogic outtakeSlideLogic;
    public SlideControl intakeSlideControl;
    public SlideControl outtakeSlideControl;

    public Dependencies(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        localizer = new StandardTrackingWheelLocalizer(hardwareMap);
        motorControl = new MotorControl(hardwareMap);
        sensorControl = new SensorControl(hardwareMap, edgeDetection, localizer);
        servoControl = new ServoControl(hardwareMap);
        outtakeSlideLogic = createOuttakeSlideLogic();
        outtakeSlideControl = createOuttakeSlideControl();
    }

    public Drivebase createDrivebase() {
        return new Drivebase(gamepad1,gamepad2, motorControl, sensorControl);
    }

    public DrivebaseController createDrivebaseController() {
        return new DrivebaseController(createDrivebase(), edgeDetection);
    }

    private SlideLogic createOuttakeSlideLogic() {
        return new SlideLogic(createOuttakeSlideControl(), new OuttakeSlideProperties());
    }

    private SlideControl createOuttakeSlideControl() {
        return new OuttakeSlideControl(motorControl,sensorControl);
    }

    ButtonControl createSubsystemControl() {
        return new ButtonControl(edgeDetection, sensorControl);
    }

    ButtonControl createSubsystemControl2() {
        return new ButtonControl(gamepad2EdgeDetection, sensorControl);
    }

    public IntakeControl createOuttakeControl() {
        return new IntakeControl(createSampleClawControl(), createVerticalSlideControl(), createPivotControl());
    }

    private PivotControl createPivotControl() {
        return new PivotControl(motorControl);
    }

    private SampleClawControl createSampleClawControl() {
        return new SampleClawControl(servoControl);
    }

    private VerticalSlideControl createVerticalSlideControl() {
        return new VerticalSlideControl(outtakeSlideLogic);
    }
}
