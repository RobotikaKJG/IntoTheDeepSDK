package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SlidesHighBasket implements Action {
    private boolean initialized = false;
    private final SlideLogic outtakeSlideLogic;

    public SlidesHighBasket(SlideLogic outtakeSlideLogic) {
        this.outtakeSlideLogic = outtakeSlideLogic;
    }

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        if (!initialized) {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
            initialized = true;
        }

        return isExtending();
    }

    private boolean isExtending() {
        return Math.abs(outtakeSlideLogic.getSlidePosition() - OuttakeConstants.highBasketPos) > OuttakeConstants.slideTargetThreshold;
    }
}
