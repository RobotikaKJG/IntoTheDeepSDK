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
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject.AutoEjectLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.IntakeSlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.IntakeSlideProperties;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideControl;

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
        return new SlideLogic(createOuttakeSlideControl(), new IntakeSlideProperties());
    }

    private SlideControl createOuttakeSlideControl() {
        return new IntakeSlideControl(motorControl,sensorControl);
    }

    ButtonControl createSubsystemControl() {
        return new ButtonControl(edgeDetection, sensorControl);
    }

    ButtonControl createSubsystemControl2() {
        return new ButtonControl(gamepad2EdgeDetection, sensorControl);
    }

    public IntakeControl createIntakeControl() {
        return new IntakeControl(createAutoCloseLogic(), createAutoCloseControl(), createAutoEjectLogic(), createAutoEjectControl(), createArmSlideControl(), createPivotControl(), createIntakeMotorControl(), createLatchControl());
    }

    public OuttakeControl createOuttakeControl() {
        return new OuttakeControl(createArmControl(), createSpecimenClawControl(), createAutoTakeControl(), createAutoTakeLogic());
    }

    private PivotControl createPivotControl() {
        return new PivotControl(motorControl, sensorControl);
    }

    private SpecimenClawControl createSpecimenClawControl() {
        return new SpecimenClawControl(servoControl);
    }

    private AutoCloseLogic createAutoCloseLogic() {
        return new AutoCloseLogic(sensorControl);
    }

    private AutoCloseControl createAutoCloseControl() {
        return new AutoCloseControl(gamepad1);
    }

    private AutoEjectLogic createAutoEjectLogic() {
        return new AutoEjectLogic(sensorControl);
    }

    private AutoEjectControl createAutoEjectControl() {
        return new AutoEjectControl();
    }

    private ArmSlideControl createArmSlideControl() {
        return new ArmSlideControl(outtakeSlideLogic);
    }

    private ArmControl createArmControl() {
        return new ArmControl(servoControl);
    }

    private IntakeMotorControl createIntakeMotorControl() {
        return new IntakeMotorControl(motorControl);
    }

    private LatchControl createLatchControl() {
        return new LatchControl(servoControl, sensorControl);
    }

    private AutoTakeLogic createAutoTakeLogic() {
        return new AutoTakeLogic();
    }

    private AutoTakeControl createAutoTakeControl() {
        return new AutoTakeControl();
    }
}
