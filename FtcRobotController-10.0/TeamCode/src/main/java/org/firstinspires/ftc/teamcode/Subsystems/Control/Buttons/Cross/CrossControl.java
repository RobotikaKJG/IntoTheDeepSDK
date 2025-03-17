package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TakeSpecimen.TakeSpecimenStates;

public class CrossControl {
    public void update() {
        switch (ButtonStates.getCrossState()) {
            case takeSpecimen:
                takeSpecimen();
                break;
            case idle:
                break;
        }
    }

    private void takeSpecimen() {
//        OuttakeStates.setTakeSpecimenStates(TakeSpecimenStates.takeSpecimen);
    }
}
