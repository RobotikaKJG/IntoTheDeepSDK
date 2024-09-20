package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp

public class TwoIsl29125Test extends LinearOpMode {

    private Isl29125 colorSensor;
    private Isl29125 colorSensor2;

    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.get(Isl29125.class, "colorSensor");
        colorSensor2 = hardwareMap.get(Isl29125.class, "colorSensor2");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // todo: write your code here
            telemetry.addData("Manufacturer ID", colorSensor.getManufacturerIDRaw());
            telemetry.addData("Red 1:", colorSensor.getRed());
            telemetry.addData("Green 1:", colorSensor.getGreen());
            telemetry.addData("Blue 1:", colorSensor.getBlue());
            telemetry.addLine();

            telemetry.addData("Manufacturer ID", colorSensor2.getManufacturerIDRaw());
            telemetry.addData("Red 2:", colorSensor2.getRed());
            telemetry.addData("Green 2:", colorSensor2.getGreen());
            telemetry.addData("Blue 2:", colorSensor2.getBlue());
            telemetry.update();
        }
    }
}
