package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class ExtendoRetracted implements Action {
    private boolean initialized = false;
    private final SlideLogic intakeSlideLogic;

    public ExtendoRetracted(SlideLogic intakeSlideLogic) {
        this.intakeSlideLogic = intakeSlideLogic;
    }

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        if (!initialized) {
            IntakeStates.setExtendoState(ExtendoStates.retracting);
            initialized = true;
        }
        return isExtending();
    }

    private boolean isExtending() {
        if (Math.abs(intakeSlideLogic.getSlidePosition()) > IntakeConstants.extendoMaxExtension)
            return true;
        IntakeStates.setExtendoState(ExtendoStates.retracted);
        return false;
    }
}
