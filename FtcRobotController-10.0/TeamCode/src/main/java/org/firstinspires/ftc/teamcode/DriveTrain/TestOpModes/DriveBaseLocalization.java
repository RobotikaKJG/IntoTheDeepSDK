package org.firstinspires.ftc.teamcode.DriveTrain.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.Localisation.DriveTrain.DrivingLocalisation;

@TeleOp(name = "Drive Base with Localization")
public class DriveBaseLocalization extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        // Create an instance of the localization system
        DrivingLocalisation localization = new DrivingLocalisation(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);

        waitForStart();

        while (opModeIsActive()) {
            // Update the robot's position based on encoder changes
            localization.updatePosition();

            // Display the current X and Z position
            telemetry.addData("X Position (inches)", localization.getXPosition());
            telemetry.addData("Z Position (inches)", localization.getZPosition());
            telemetry.update();

            sleep(50);  // Adjust as necessary for your loop speed
        }
    }
}
