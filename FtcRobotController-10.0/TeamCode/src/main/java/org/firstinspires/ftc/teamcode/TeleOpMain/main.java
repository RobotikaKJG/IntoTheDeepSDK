package org.firstinspires.ftc.teamcode.TeleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.DriveTrain.DriveBase;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class main extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DriveBase driveBase = new DriveBase(this);

        waitForStart();

        while (opModeIsActive()) {
            driveBase.updateDrive(gamepad1, this);
        }
    }
}
