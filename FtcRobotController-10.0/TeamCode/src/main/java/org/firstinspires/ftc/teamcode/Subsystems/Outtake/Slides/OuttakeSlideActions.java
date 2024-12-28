package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.Actions.SlidesClosed;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.Actions.SlidesHighBasket;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.Actions.SlidesLowBasket;

public class OuttakeSlideActions {

    private final SlideLogic outtakeSlideLogic;

    public OuttakeSlideActions(SlideLogic outtakeSlideLogic) {
        this.outtakeSlideLogic = outtakeSlideLogic;
    }

    public Action extendToHighBasket(){
        return new SlidesHighBasket(outtakeSlideLogic);
    }
    public Action extendToLowBasket(){
        return new SlidesLowBasket(outtakeSlideLogic);
    }
    public Action retract(){
        return new SlidesClosed(outtakeSlideLogic);
    }


}
