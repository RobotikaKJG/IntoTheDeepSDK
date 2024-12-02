package org.firstinspires.ftc.teamcode.IntakeSystem.Intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeServo {

    private CRServo intakeServo;

    public IntakeServo(HardwareMap hardwareMap) {
        // Initialize the servo
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
    }

    public void updateIntake(Gamepad gamepad) {
        // Control servo direction with R1 and L1 buttons
        if (gamepad.right_bumper) {
            intakeServo.setPower(1.0);  // Spin clockwise indefinitely
        } else if (gamepad.left_bumper) {
            intakeServo.setPower(-1.0); // Spin anti-clockwise indefinitely
        } else {
            intakeServo.setPower(0.0);  // Stop spinning when no button is pressed
        }
    }

    public double getServoPower() {
        return intakeServo.getPower();
    }
}
