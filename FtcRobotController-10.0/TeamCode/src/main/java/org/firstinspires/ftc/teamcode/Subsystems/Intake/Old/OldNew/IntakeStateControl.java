package org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.OldNew;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeMotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeServoControl;

public class IntakeStateControl {
    private final IntakeMotorControl intakeMotorControl;
    private final IntakeServoControl intakeServoControl;
    private final ExtendoControl extendoControl;
    private final IntakeStateSetter intakeStateSetter = new IntakeStateSetter();


    public IntakeStateControl(IntakeMotorControl intakeMotorControl, IntakeServoControl intakeServoControl, ExtendoControl extendoControl) {
        this.intakeMotorControl = intakeMotorControl;
        this.intakeServoControl = intakeServoControl;
        this.extendoControl = extendoControl;
    }

    public void update(){
        //intakeStateSetter.update();
        intakeMotorControl.update();
        intakeServoControl.update();
        extendoControl.update();
    }
}
