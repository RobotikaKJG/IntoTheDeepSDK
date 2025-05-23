package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;

@TeleOp
public class GeneralTeleOp extends LinearOpMode {

    private double prevTime;
    /**
     * @noinspection RedundantThrows
     */
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1, telemetry);
        StandardTrackingWheelLocalizer localizer = dependencies.localizer;
        IterativeController iterativeController = new IterativeController(dependencies);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            iterativeController.TeleOp();
            localizer.update();
            if (gamepad1.y)
                break;
            calculateLoopTime(dependencies.elapsedTime);
            telemetry.addData("Rotation:",dependencies.sensorControl.getLocalizerAngle());
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