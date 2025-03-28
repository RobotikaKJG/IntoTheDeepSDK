package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

@TeleOp
public class GeneralBlueTeleOp extends LinearOpMode {

    private double prevTime;
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        GlobalVariables.alliance = Alliance.Blue;
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1, gamepad2, telemetry);
        IterativeController iterativeController = new IterativeController(dependencies);

        waitForStart();

        dependencies.servoControl.setServoStartPos();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            iterativeController.TeleOp();
            if (gamepad1.triangle) break;
            calculateLoopTime();
//            telemetry.addData("AutoCloseStates",IntakeStates.getAutoCloseStates());
            telemetry.addData("outtake slide states",OuttakeStates.getVerticalSlideState());
            //telemetry.addData("slide pos",dependencies.outtakeSlideControl.getSlidePosition());
            telemetry.update();
        }
    }

    private void calculateLoopTime()
    {
        double currentTime = System.nanoTime() / 1_000_000.0;
        telemetry.addData("Loop time:", currentTime - prevTime);
        prevTime = currentTime;
    }
}