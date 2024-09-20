package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp

public class TCS3472Test extends LinearOpMode {

    private TCS3472 colorSensor;

    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.get(TCS3472.class, "colorSensor");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // todo: write your code here
            telemetry.addData("Manufacturer ID", colorSensor.getManufacturerIDRaw());
            telemetry.addData("Red:", colorSensor.getRed());
            telemetry.addData("Green:", colorSensor.getGreen());
            telemetry.addData("Blue:", colorSensor.getBlue());
            //telemetry.addData()
            telemetry.update();
        }
    }
}
