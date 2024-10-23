package org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges;

import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;

public class AutoStop implements IntakeStateChange {
    SensorControl sensorControl;
    SlideLogic slideLogic;

    public AutoStop(SensorControl sensorControl, SlideLogic slideLogic) {
        this.sensorControl = sensorControl;
        this.slideLogic = slideLogic;
    }

    @Override
    public boolean shouldBeStopping() {
        return sensorControl.isColorMatch(IntakeConstants.targetColor, IntakeConstants.threshold);
    }

    @Override
    public void initialiseStop() {
        slideLogic.setSlideExtensionTarget(0);
    }

    @Override
    public void stop() {

    }
}
