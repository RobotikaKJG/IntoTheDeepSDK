package org.firstinspires.ftc.teamcode.SensorCode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;

@TeleOp
public class RevColorV2Test extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor color;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "ColorSensor");
        SensorControl sensorControl = new SensorControl(hardwareMap,gamepad1,new StandardTrackingWheelLocalizer(hardwareMap));
        int targetColor = Color.rgb(0,0,0);
        int threshold = 10;

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            telemetry.addData("Red", color.red());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("IsColor:",sensorControl.isColorMatch(targetColor,threshold));
            telemetry.update();
        }
    }
}
