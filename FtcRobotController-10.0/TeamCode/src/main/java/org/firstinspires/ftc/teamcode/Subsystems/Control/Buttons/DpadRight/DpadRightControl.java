package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;


public class DpadRightControl {
    public void update() {
        switch (ButtonStates.getDpadRightState()) {
            case toggleHang:
                toggleHang();
                break;
            case idle:
                break;
        }
    }

    private void toggleHang() {
        switch(OuttakeStates.getHangState()){
            case idle:
                OuttakeStates.setHangState(HangStates.extendSlides);
                break;
            case waitForButton:
                OuttakeStates.setHangState(HangStates.retractSlides);
                break;

        }
    }
}
