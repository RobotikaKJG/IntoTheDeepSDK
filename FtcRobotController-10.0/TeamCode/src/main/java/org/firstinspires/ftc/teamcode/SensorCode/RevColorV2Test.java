package org.firstinspires.ftc.teamcode.SensorCode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;

@TeleOp
public class RevColorV2Test extends LinearOpMode {
    // Define a variable for our color sensor
    NormalizedColorSensor color;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        SensorControl sensorControl = new SensorControl(hardwareMap,gamepad1,new StandardTrackingWheelLocalizer(hardwareMap));
        int targetColor = Color.rgb(165,120,69);
        int threshold = 60;
        color.setGain(10);

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            NormalizedRGBA currentColor = color.getNormalizedColors();
            int convertedColor = currentColor.toColor();
            telemetry.addData("Red", sensorControl.isColorMatch(IntakeConstants.red,IntakeConstants.redThreshold));
            telemetry.addData("Yellow", sensorControl.isColorMatch(IntakeConstants.yellow,IntakeConstants.yellowThreshold));
            telemetry.addData("Blue", sensorControl.isColorMatch(IntakeConstants.blue,IntakeConstants.blueThreshold));
            telemetry.addData("Gain:",color.getGain());
            telemetry.update();
        }
    }
}
