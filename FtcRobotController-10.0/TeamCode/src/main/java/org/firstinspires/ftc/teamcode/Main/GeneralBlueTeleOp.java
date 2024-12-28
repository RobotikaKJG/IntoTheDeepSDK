package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Roadrunner.ThreeDeadWheelLocalizer;

@TeleOp
public class GeneralBlueTeleOp extends LinearOpMode {

    private double prevTime;
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        GlobalVariables.alliance = Alliance.Blue;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1, gamepad2, telemetry);
        ThreeDeadWheelLocalizer localizer = dependencies.localizer;
        IterativeController iterativeController = new IterativeController(dependencies);

        waitForStart();

        dependencies.servoControl.setServoStartPos();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            iterativeController.TeleOp();
            localizer.update();
            if (gamepad1.triangle) break;
            calculateLoopTime(dependencies.elapsedTime);
            //telemetry.addData("Rotation:",dependencies.sensorControl.getLocalizerAngle());
            telemetry.update();
        }
    }

    private void calculateLoopTime(ElapsedTime elapsedTime)
    {
        double currentTime = elapsedTime.milliseconds();
        telemetry.addData("Loop time:", currentTime - prevTime);
        prevTime = currentTime;
    }
}