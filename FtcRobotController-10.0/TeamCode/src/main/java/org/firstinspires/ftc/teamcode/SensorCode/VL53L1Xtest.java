package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp

public class VL53L1Xtest extends LinearOpMode {

    private VL53L1X distanceSensor;

    public void runOpMode() throws InterruptedException {
        distanceSensor = hardwareMap.get(VL53L1X.class, "distanceSensor");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // todo: write your code here
            //telemetry.addData("Manufacturer ID", distanceSensor.read8(0x0096));
            telemetry.update();
        }
    }
}
