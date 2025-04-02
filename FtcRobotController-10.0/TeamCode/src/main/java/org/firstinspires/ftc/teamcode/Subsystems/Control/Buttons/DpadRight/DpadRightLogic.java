package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class DpadRightLogic {
    private final DpadRightControl dpadRightControl = new DpadRightControl();

    public void update() {
        if(iterateHangStates())return;
    }

    private boolean iterateHangStates() {
        if (intakeActive()|| smapleActive() || specimenActive()) return false;

        ButtonStates.setDpadRightState(DpadRightStates.toggleHang);
        dpadRightControl.update();
        // Reset the dpad-right command state to idle.
        ButtonStates.setDpadRightState(DpadRightStates.idle);
        return true;
    }

    private boolean intakeActive()
    {
        return IntakeStates.getIntakeState() != SubsystemState.Idle;
    }

    private boolean smapleActive()
    {
        return OuttakeStates.getSampleReleaseButtonState() != SampleReleaseButtonStates.idle;
    }

    private boolean specimenActive()
    {
        return OuttakeStates.getSpecimenReleaseButtonState() != SpecimenReleaseButtonStates.idle;
    }
}
