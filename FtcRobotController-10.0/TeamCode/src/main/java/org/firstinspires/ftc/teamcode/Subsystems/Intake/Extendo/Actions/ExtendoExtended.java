package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

//TODO redo due to logic changes, NOTE
public class ExtendoExtended implements Action {
    private boolean initialized = false;
    private final SlideLogic intakeSlideLogic;
    private final int extensionTarget;

    public ExtendoExtended(SlideLogic intakeSlideLogic, int extensionTarget) {
        this.intakeSlideLogic = intakeSlideLogic;
        this.extensionTarget = extensionTarget;
    }

    @Override
    public boolean run (@NonNull TelemetryPacket packet) {
        if (!initialized) {
            intakeSlideLogic.setSlideExtensionTarget(extensionTarget);
            initialized = true;
        }
        return isExtending();
    }

    private boolean isExtending() {
        return Math.abs(intakeSlideLogic.getSlidePosition() - intakeSlideLogic.getSlideExtensionTarget()) > IntakeConstants.extendoThreshold;
    }
}
