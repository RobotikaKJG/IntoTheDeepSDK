package org.firstinspires.ftc.teamcode.Main.ManualOpModes;

import com.acmerobotics.roadrunner.ftc.LazyImu;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.Main.Dependencies;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase.DrivebaseController;

@TeleOp
public class DrivebaseTest extends LinearOpMode {
    public RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection =
            RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
    public RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection =
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

    @Override
    public void runOpMode() throws InterruptedException {



        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1,gamepad2, telemetry);
        DrivebaseController drivebaseController = dependencies.createDrivebaseController();
        LazyImu lazyImu = new LazyImu(hardwareMap, "imu", new RevHubOrientationOnRobot(
                logoFacingDirection, usbFacingDirection));
        IMU imu = lazyImu.get();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drivebaseController.updateState();
            dependencies.motorControl.setMotors(MotorConstants.allDrive);
            telemetry.addData("Angle",imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}
