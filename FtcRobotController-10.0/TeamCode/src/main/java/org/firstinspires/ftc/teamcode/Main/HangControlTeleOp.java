package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.HangControl;

@TeleOp(name = "HangControlTeleOp", group = "Outtake")
public class HangControlTeleOp extends OpMode {
    private HangControl hangControl;
    private boolean lastDpadRightState = false;

    @Override
    public void init() {
        // Initialize SlideLogic and HangControl
        Dependencies dependencies = new Dependencies(hardwareMap, gamepad1, gamepad2, telemetry);
        SlideLogic slideLogic = dependencies.createOuttakeSlideLogic();
        hangControl = new HangControl(slideLogic);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        // Get the current state of the DpadRight button
        boolean currentDpadRightState = gamepad1.dpad_right;

        // Toggle the hang state when DpadRight is pressed
        if (currentDpadRightState && !lastDpadRightState) {
            hangControl.toggleHang();
        }

        // Update the hang control state
        hangControl.update();

        // Update the last state of DpadRight
        lastDpadRightState = currentDpadRightState;

        // Telemetry output for debugging
        telemetry.addData("Hanging State", hangControl.isHanging() ? "Extended" : "Retracted");
        telemetry.update();
    }
}
