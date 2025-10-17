package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;


public class DpadRightControl {
    public void update() {
        System.out.println("3");
        switch (ButtonStates.getDpadRightState()) {
            case toggleHang:
                toggleHang();
                break;
            case idle:
                break;
        }
    }

    private void toggleHang() {
        System.out.println("4");
        switch(OuttakeStates.getHangState()){
            case idle:
            case retracted:
                OuttakeStates.setHangState(HangStates.releasePTO);
                break;

            case releasePTO:
                OuttakeStates.setHangState(HangStates.extendSlides);
                break;

            case extendSlides:
                System.out.println("5");
                OuttakeStates.setHangState(HangStates.retractSlides);
                break;

            case retractSlides:
                OuttakeStates.setHangState(HangStates.lockPTO);
                break;

            case lockPTO:
                OuttakeStates.setHangState(HangStates.hangOnHooks);
                break;
        }
    }
}
