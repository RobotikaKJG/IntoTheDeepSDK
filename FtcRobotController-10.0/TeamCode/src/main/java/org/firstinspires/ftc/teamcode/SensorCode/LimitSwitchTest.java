package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class LimitSwitchTest extends LinearOpMode {
    LimitSwitch limitSwitch;

    @Override
    public void runOpMode() {

        limitSwitch = hardwareMap.get(LimitSwitch.class, "limitSwitch");
        limitSwitch.setMode(LimitSwitch.SwitchConfig.NC);

        waitForStart();


        while (opModeIsActive()) {
            //telemetry.addData("mode: ", limitSwitch.getMode());
            telemetry.addData("Is pressed?", limitSwitch.getIsPressed());

            telemetry.update();
        }
    }
}
