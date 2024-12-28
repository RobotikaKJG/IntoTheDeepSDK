package org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.Actions.EjectionServoAction;

public class EjectionServoActions {
    private final SampleEjectionLogic sampleEjectionLogic;

    public EjectionServoActions(SampleEjectionLogic sampleEjectionLogic) {
        this.sampleEjectionLogic = sampleEjectionLogic;
    }

    public Action controlEjection(){
        return new EjectionServoAction(sampleEjectionLogic);
    }
}
