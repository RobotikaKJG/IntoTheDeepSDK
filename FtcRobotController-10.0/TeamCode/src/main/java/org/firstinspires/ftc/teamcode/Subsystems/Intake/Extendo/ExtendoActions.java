package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.Actions.ExtendoExtended;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.Actions.ExtendoRetracted;

public class ExtendoActions {

    private final SlideLogic intakeSlideLogic;

    public ExtendoActions(SlideLogic intakeSlideLogic) {
        this.intakeSlideLogic = intakeSlideLogic;
    }

    public Action extend(int extensionTarget){
        return new ExtendoExtended(intakeSlideLogic, extensionTarget);
    }
    public Action retract(){
        return new ExtendoRetracted(intakeSlideLogic);
    }


}
