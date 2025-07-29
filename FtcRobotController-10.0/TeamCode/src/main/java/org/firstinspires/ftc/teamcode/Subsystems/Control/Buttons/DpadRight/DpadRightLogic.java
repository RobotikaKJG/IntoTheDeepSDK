package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp.DpadUpStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class DpadRightLogic {
    private final DpadRightControl dpadRightControl = new DpadRightControl();

    public void update() {
        iterateHangStates();
    }

    private void completeAction(){
        dpadRightControl.update();
        ButtonStates.setDpadUpState(DpadUpStates.idle);
    }

    private boolean iterateHangStates() {
        if (!intakeIdle()) return false;//|| sampleActive() || specimenActive()) return false;

        ButtonStates.setDpadRightState(DpadRightStates.toggleHang);
        completeAction();
        return true;
    }

    private boolean intakeIdle()
    {
        return IntakeStates.getIntakeState() == SubsystemState.Idle;
    }

    private boolean sampleActive()
    {
        return OuttakeStates.getSampleReleaseButtonState() != SampleReleaseButtonStates.idle;
    }

    private boolean specimenActive()
    {
        return OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.idle;
    }
}
