//package org.firstinspires.ftc.teamcode.Main;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
//import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight.DpadRightLogic;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangControl;
//
//
//@TeleOp(name = "Hang TeleOp", group = "TeleOp")
//public class HangTeleOp extends LinearOpMode {
//
//    private double prevTime;
//
//    // Dependencies instance that provides access to hardwareMap, gamepads, telemetry, etc.
//    private Dependencies dependencies;
//    private SlideLogic slideLogic;
//    private HangControl hangControl;
//    private DpadRightLogic dpadRightLogic;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // Set global flags (if you use these in your project).
//        GlobalVariables.isAutonomous = false;
//        GlobalVariables.alliance = Alliance.Blue;
//
//        // Initialize dependencies.
//        dependencies = new Dependencies(hardwareMap, gamepad1, gamepad2, telemetry);
//
//        // Create the SlideLogic instance via dependencies.
//        // SlideLogic's constructor requires a SlideControl and SlideProperties.
//        slideLogic = dependencies.createOuttakeSlideLogic();
//
//        // Create hang and dpad-right logic objects.
//        hangControl = new HangControl(slideLogic);
//        dpadRightLogic = new DpadRightLogic();
//
//        // (Optional) Initialize any additional hardware such as servo positions.
//        dependencies.servoControl.setServoStartPos();
//
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//        while (opModeIsActive()) {
//            // Use the gamepad1 dpad_right input to update the hang subsystem.
//            dpadRightLogic.update(gamepad1.dpad_right);
//            hangControl.update();
//
//            // Break out of the opmode if the triangle button is pressed.
//            if (gamepad1.triangle) break;
//
//            // Display current hang state and loop time.
//            telemetry.addData("Hang State", OuttakeStates.getHangState().toString());
//            calculateLoopTime();
//            telemetry.update();
//        }
//    }
//
//    private void calculateLoopTime() {
//        double currentTime = System.nanoTime() / 1_000_000.0;
//        telemetry.addData("Loop time", currentTime - prevTime);
//        prevTime = currentTime;
//    }
//}