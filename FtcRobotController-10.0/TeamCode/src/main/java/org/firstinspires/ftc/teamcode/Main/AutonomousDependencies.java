//package org.firstinspires.ftc.teamcode.Main;
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.Autonomous.AudienceSideAuton;
//import org.firstinspires.ftc.teamcode.Autonomous.AutonomousControl;
//import org.firstinspires.ftc.teamcode.Autonomous.BacksideAuton;
//import org.firstinspires.ftc.teamcode.Camera.CameraInterface;
//
//public class AutonomousDependencies extends Dependencies {
//
//    public CameraInterface cameraInterface;
//
//    public AutonomousDependencies(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {
//        super(hardwareMap, gamepad1, telemetry);
//        cameraInterface = new CameraInterface(hardwareMap, telemetry);
//    }
//
////    public AudienceSideAuton createAudienceSideAuton() {
////        return new AudienceSideAuton(drive, createIntakeStateControl(), createOuttakeController(), cameraInterface.getAprilTagCamera());
////    }
////
////    public BacksideAuton createBacksideAuton() {
////        return new BacksideAuton(drive, createIntakeStateControl(), outtakeDependencies);
////    }
////
////    public AutonomousControl createAutonomousControl() {
////        return new AutonomousControl(motorControl,createAudienceSideAuton(),createBacksideAuton());
////    }
//}
