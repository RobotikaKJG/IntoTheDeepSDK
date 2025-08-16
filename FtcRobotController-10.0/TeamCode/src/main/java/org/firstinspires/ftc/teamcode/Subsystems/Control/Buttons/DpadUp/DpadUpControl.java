package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class DpadUpControl {
    public void update() {
        switch (ButtonStates.getDpadUpState()) {
            case forward:
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                break;
            case backward:
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                break;
            case idle:
                break;
        }
    }
}
