package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp

public class Isl29125Test extends LinearOpMode {

    private Isl29125 colorSensor;

    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.get(Isl29125.class, "colorSensor");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // todo: write your code here
            telemetry.addData("Manufacturer ID", colorSensor.getManufacturerIDRaw());
            telemetry.addData("Red:", colorSensor.getRed());
            telemetry.addData("Green:", colorSensor.getGreen());
            telemetry.addData("Blue:", colorSensor.getBlue());
            telemetry.update();
        }
    }
}
