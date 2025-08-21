package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadLeft;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.DownServo.DownServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadLeftControl {
    public void update() {
        switch (ButtonStates.getDpadLeftState()) {
            case hangOn:
                IntakeStates.setHangState(HangStates.pivotUp);
                GlobalVariables.hang = true;
                break;
            case hangOff:
                IntakeStates.setHangState(HangStates.idle);
                IntakeStates.setPivotState(PivotStates.down);
                IntakeStates.setArmSlideState(ArmSlideStates.close);
                OuttakeStates.setDownServoState(DownServoStates.active);
                OuttakeStates.setArmState(ArmStates.idle);
                OuttakeStates.setArmState(ArmStates.takeSpecimen);
                GlobalVariables.hang = false;
                break;
            case idle:
                break;
        }
    }
}
