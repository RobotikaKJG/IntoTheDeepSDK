package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class TCRT5000Test extends LinearOpMode {
    TCRT5000 tcrt5000;

    @Override
    public void runOpMode() {

        tcrt5000 = hardwareMap.get(TCRT5000.class, "tcrt5000");

        waitForStart();


        while (opModeIsActive()) {
            //telemetry.addData("mode: ", limitSwitch.getMode());

            telemetry.addData("Voltage", tcrt5000.readVoltage());

            telemetry.update();
        }
    }
}
