package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.Control.SubsystemControl;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.SampleEjectionLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonLogic;
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
    public final StandardTrackingWheelLocalizer localizer;
    public MotorControl motorControl;
    public SensorControl sensorControl;
    public ServoControl servoControl;
    public EdgeDetection edgeDetection = new EdgeDetection();
    public EdgeDetection gamepad2EdgeDetection = new EdgeDetection();
    public SampleMecanumDrive drive;
    public ElapsedTime elapsedTime = new ElapsedTime();

    public Dependencies(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry) {

        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.telemetry = telemetry;
        localizer = new StandardTrackingWheelLocalizer(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
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
        return new IntakeControl(createIntakeMotorControl(), createIntakeServoControl(),
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
        return new AutoCloseLogic(elapsedTime,sensorControl);
    }

    private IntakeServoControl createIntakeServoControl() {
        return new IntakeServoControl(servoControl);
    }

    private ExtendoControl createIntakeExtendoControl() {
        return new ExtendoControl(createIntakeSlideLogic());
    }

    private AutoCloseControl createAutoCloseControl() {
        return new AutoCloseControl(gamepad1);
    }

    public OuttakeControl createOuttakeControl() {
        return new OuttakeControl(createArmControl(),createClawControl(),createVerticalSlideControl(),createReleaseButtonLogic());
    }

    private ReleaseButtonLogic createReleaseButtonLogic() {
        return new ReleaseButtonLogic(elapsedTime);
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
