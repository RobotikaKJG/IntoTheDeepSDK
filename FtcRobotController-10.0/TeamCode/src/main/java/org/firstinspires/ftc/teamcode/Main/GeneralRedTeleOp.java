package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Roadrunner.ThreeDeadWheelLocalizer;

@TeleOp
public class GeneralRedTeleOp extends LinearOpMode {

    private double prevTime;
    @Override
    public void runOpMode() throws InterruptedException {

        GlobalVariables.isAutonomous = false;
        GlobalVariables.alliance = Alliance.Red;
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
            telemetry.addData("Gamepad active",gamepadActive());
            telemetry.addData("Gamepad 2 active",gamepad2Active());
            telemetry.addData("speed", IntakeConstants.getIntakeSpeed());
            telemetry.addData("sloMode", GlobalVariables.slowMode);
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

    private boolean gamepadActive(){
        return gamepad1.square || gamepad1.triangle || gamepad1.dpad_up || gamepad1.dpad_down
                || !gamepad1.atRest() || gamepad1.left_bumper || gamepad1.left_trigger != 0
                || gamepad1.right_bumper || gamepad1.right_trigger != 0;
    }

    private boolean gamepad2Active(){
        return gamepad2.square || gamepad2.triangle || gamepad2.dpad_up || gamepad2.dpad_down
                || !gamepad2.atRest() || gamepad2.left_bumper || gamepad2.left_trigger != 0
                || gamepad2.right_bumper || gamepad2.right_trigger != 0;
    }
}