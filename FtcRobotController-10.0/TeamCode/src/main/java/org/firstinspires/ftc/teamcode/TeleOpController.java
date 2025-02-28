package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.EdgeDetection;


@TeleOp
public class TeleOpController extends LinearOpMode {
    private boolean longer = true;
    private int sleep = 80;

    @Override
    public void runOpMode() throws InterruptedException {
        EdgeDetection edgeDetection = new EdgeDetection();
        IntakeController intakeController = new IntakeController(edgeDetection, hardwareMap);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad prevGamepad1 = new Gamepad();

        // Define the motors
//         DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
//         DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
//         DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
//         DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();

        if (isStopRequested()) return;

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();
            if (gamepad1.triangle) {
                break;
            }

            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            intakeController.updateState();

            // Print out loop time
            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;
            if (longer) telemetry.addData("Loop time;", loopTime);
            else telemetry.addData("Loop time:", loopTime);
            telemetry.update();
        }

    }
}
