package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ExtendoControl {
    private final SlideLogic slideLogic;
    private ExtendoStates prevExtendoState = ExtendoStates.idle;

    public ExtendoControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        if(IntakeStates.getExtendoState() != prevExtendoState) {
            updateStates();
            prevExtendoState = IntakeStates.getExtendoState();
        }
        if(IntakeStates.getExtendoState() == ExtendoStates.retracting) {
            if(slideLogic.slidesBottomReached()) {
                IntakeStates.setExtendoState(ExtendoStates.retracted);
            }
        }
    }

    private void updateStates() {
        switch(IntakeStates.getExtendoState()){
            case retracting:
                slideLogic.stepDown();
                IntakeStates.setEjectionServoState(EjectionServoStates.closed); // to ensure closed, NOTE
                OuttakeStates.setClawState(ClawStates.fullyOpen);
                break;
            case extended:
                slideLogic.stepUp();
                break;
            case idle:
                break;
        }
    }
}
