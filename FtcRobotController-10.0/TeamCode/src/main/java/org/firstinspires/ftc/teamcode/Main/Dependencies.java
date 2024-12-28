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
import org.firstinspires.ftc.teamcode.Roadrunner.ThreeDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.Control.SubsystemControl;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.OuttakeSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.OuttakeSlideProperties;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.IntakeSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.IntakeSlideProperties;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideControl;

public class Dependencies {
    public final HardwareMap hardwareMap;
    public final Gamepad gamepad1;
    public final Gamepad gamepad2;
    public final Telemetry telemetry;
    public final ThreeDeadWheelLocalizer localizer;
    public MotorControl motorControl;
    public SensorControl sensorControl;
    public ServoControl servoControl;
    public EdgeDetection edgeDetection = new EdgeDetection();
    public EdgeDetection gamepad2EdgeDetection = new EdgeDetection();

    public Dependencies(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        localizer = new ThreeDeadWheelLocalizer(hardwareMap);
        motorControl = new MotorControl(hardwareMap);
        sensorControl = new SensorControl(hardwareMap, edgeDetection, localizer);
        servoControl = new ServoControl(hardwareMap);
    }

    public Drivebase createDrivebase() {
        return new Drivebase(gamepad1,gamepad2, motorControl, sensorControl);
    }

    public DrivebaseController createDrivebaseController() {
        return new DrivebaseController(createDrivebase(), edgeDetection);
    }

    SlideLogic createIntakeSlideLogic() {
        return new SlideLogic(createIntakeSlideControl(), new IntakeSlideProperties());
    }

    SlideControl createIntakeSlideControl() {
        return new IntakeSlideControl(motorControl,sensorControl);
    }

    SlideLogic createOuttakeSlideLogic() {
        return new SlideLogic(createOuttakeSlideControl(), new OuttakeSlideProperties());
    }

    SlideControl createOuttakeSlideControl() {
        return new OuttakeSlideControl(motorControl,sensorControl);
    }

    SubsystemControl createSubsystemControl() {
        return new SubsystemControl(edgeDetection, sensorControl);
    }

    SubsystemControl createSubsystemControl2() {
        return new SubsystemControl(gamepad2EdgeDetection, sensorControl);
    }

    private IntakeMotorControl createIntakeMotorControl() {
        return new IntakeMotorControl(motorControl);
    }

    public IntakeControl createIntakeControl() {
        return new IntakeControl(createIntakeMotorControl(),
                createIntakeExtendoControl(), createAutoCloseControl(),
                createAutoCloseLogic(),createEjectionServoControl(),
                createSampleEjectionLogic());
    }

    private SampleEjectionLogic createSampleEjectionLogic() {
        return new SampleEjectionLogic(sensorControl);
    }

    private EjectionServoControl createEjectionServoControl() {
        return new EjectionServoControl(servoControl);
    }

    private AutoCloseLogic createAutoCloseLogic() {
        return new AutoCloseLogic(sensorControl);
    }

    private ExtendoControl createIntakeExtendoControl() {
        return new ExtendoControl(createIntakeSlideLogic());
    }

    private AutoCloseControl createAutoCloseControl() {
        return new AutoCloseControl(gamepad1);
    }

    public OuttakeControl createOuttakeControl() {
        return new OuttakeControl(createArmControl(),createClawControl(),createVerticalSlideControl());
    }

    private ArmControl createArmControl() {
        return new ArmControl(servoControl);
    }

    private ClawControl createClawControl() {
        return new ClawControl(servoControl);
    }

    private VerticalSlideControl createVerticalSlideControl() {
        return new VerticalSlideControl(createOuttakeSlideLogic());
    }
}
