package org.firstinspires.ftc.teamcode.Main.ManualOpModes;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.Alliance;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;


@TeleOp
public class RevColorV2Test extends LinearOpMode {
    // Define a variable for our color sensor
    EdgeDetection edgeDetection = new EdgeDetection();
    public  NormalizedColorSensor colorSensor;
    public  LynxI2cColorRangeSensor rangeSensor;
    public int currentColor;
    public int currentRed;
    public int currentGreen;
    public int currentBlue;
    private double currentDistance;

    @Override
    public void runOpMode() {
        
        // Get the color sensor from hardwareMap

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        rangeSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "ColorSensor");
        colorSensor.setGain(5);//2);
        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            updateColor();
            updateDistance();
            telemetry.addData("Red", isRed());
            telemetry.addData("Yellow", isYellow());
            telemetry.addData("Blue", isBlue());

            telemetry.addData("Red", Color.red(currentColor));
            telemetry.addData("Green", Color.green(currentColor));
            telemetry.addData("Blue", Color.blue(currentColor));
            telemetry.addData("Distance correct:",getDistance());
            telemetry.addData("Distance correct:",getDistance() < 60);

            telemetry.update();
        }
    }

    public void updateColor(){
        currentColor = colorSensor.getNormalizedColors().toColor();
        currentRed = Color.red(currentColor);
        currentGreen = Color.green(currentColor);
        currentBlue = Color.blue(currentColor);
    }

    public void resetColor(){
        currentColor = 0;
        currentRed = 0;
        currentGreen = 0;
        currentBlue = 0;
    }

    public void updateDistance(){
        currentDistance = rangeSensor.getDistance(DistanceUnit.MM);
    }

    public void resetDistance(){
        currentDistance = 100;
    }

    public boolean isRed(){
        //return currentGreen < 5 && currentRed > 7 || (currentBlue == 2 && currentGreen == 2 && currentRed == 5);
        return currentGreen < 14 && currentRed > 12;
    }

    public boolean isYellow(){
        return currentGreen > 13;
    }

    public boolean isBlue(){
//        return (currentRed < 5 && currentBlue > 3 && currentGreen < 8) || ( currentRed == 1 && currentBlue == 3 && currentGreen < 4);
        return  currentRed < 10;// && currentBlue >= 6;
    }

    public double getDistance(){
        return currentDistance;
    }
}
