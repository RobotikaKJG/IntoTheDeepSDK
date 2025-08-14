package org.firstinspires.ftc.teamcode.Other;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

/*
Claw: 0.1 closed, 0.3 open?
Intake pivot: 0.15 down,  clears sub, 0.7 up
claw pivot: 0.8-0.9 take, 0.63 place

 */

@TeleOp
public class TwoServo extends LinearOpMode {

    private double prevTime;
    private final Gamepad currentGamepad1 = new Gamepad();
    private final Gamepad prevGamepad1 = new Gamepad();
    private double currentWait = 0;
    private boolean wasIfCalled = false;
    private Servo leftServo;
    private Servo rightServo;


    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();
        EdgeDetection edgeDetection = new EdgeDetection();
        leftServo = hardwareMap.get(Servo.class, "outtakeLeftServo");
        rightServo = hardwareMap.get(Servo.class, "outtakeRightServo");



        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if(edgeDetection.rising(GamepadIndexValues.rightBumper)) {
                leftServo.setPosition(OuttakeConstants.outtakeLeftServoMaxPos);
                rightServo.setPosition(OuttakeConstants.outtakeRightServoMaxPos);
            }

            if(edgeDetection.rising(GamepadIndexValues.leftBumper)) {
                leftServo.setPosition(OuttakeConstants.outtakeLeftServoStartPos);
                rightServo.setPosition(OuttakeConstants.outtakeRightServoStartPos);
            }

            if (gamepad1.triangle) break;
            calculateLoopTime();
            telemetry.update();
            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);
//            updateServoTiming();
        }
    }


    private void calculateLoopTime()
    {
        double currentTime = System.nanoTime() / 1_000_000.0;
        telemetry.addData("Loop time:", currentTime - prevTime);
        prevTime = currentTime;
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}