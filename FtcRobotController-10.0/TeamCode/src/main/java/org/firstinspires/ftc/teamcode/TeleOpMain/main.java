package org.firstinspires.ftc.teamcode.TeleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.DriveTrain.DriveBase;
import org.firstinspires.ftc.teamcode.IntakeSystem.Intake.IntakeServo;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class main extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize subsystems
        DriveBase driveBase = new DriveBase(this);
        IntakeServo intakeTest = new IntakeServo(hardwareMap);

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Update the drive base
            driveBase.updateDrive(gamepad1, this);

            // Update the intake system
            intakeTest.updateIntake(gamepad1);

            // Debugging telemetry
            telemetry.addData("Servo Power", intakeTest.getServoPower());
            telemetry.update();
        }
    }
}
