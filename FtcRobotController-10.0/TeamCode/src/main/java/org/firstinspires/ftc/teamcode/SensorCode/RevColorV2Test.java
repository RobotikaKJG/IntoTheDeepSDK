package org.firstinspires.ftc.teamcode.SensorCode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Roadrunner.ThreeDeadWheelLocalizer;

@TeleOp
public class RevColorV2Test extends LinearOpMode {
    // Define a variable for our color sensor
    EdgeDetection edgeDetection = new EdgeDetection();

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap

        SensorControl sensorControl = new SensorControl(hardwareMap,edgeDetection, new ThreeDeadWheelLocalizer(hardwareMap));

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {

            telemetry.addData("Red", sensorControl.isRed());
            telemetry.addData("Yellow", sensorControl.isYellow());
            telemetry.addData("Blue", sensorControl.isBlue());

            telemetry.addData("Red", Color.red(sensorControl.currentColor));
            telemetry.addData("Yellow", Color.green(sensorControl.currentColor));
            telemetry.addData("Blue", Color.blue(sensorControl.currentColor));

            telemetry.update();
        }
    }
}
