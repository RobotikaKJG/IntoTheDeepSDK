package org.firstinspires.ftc.teamcode.Subsystems.Outtake.TakeSpecimen;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class TakeSpecimenLogic {

    private double currentWait = 0;

    public void update() {
        switch (OuttakeStates.getTakeSpecimenStates())
        {
            case takeSpecimen:
                takeSpecimen();
                break;
            case waitToTake:
                waitToTake();
                break;
            case raiseSlides:
                raiseSlides();
                break;
            case idle:
                break;
        }
    }

    private void takeSpecimen() {
        addWaitTime(OuttakeConstants.takeSpecimenWait);
        OuttakeStates.setTakeSpecimenStates(TakeSpecimenStates.waitToTake);
    }

    private void waitToTake() {
        if(currentWait > getSeconds()) return;

        OuttakeStates.setTakeSpecimenStates(TakeSpecimenStates.raiseSlides);
    }

    private void raiseSlides() {
        OuttakeStates.setTakeSpecimenStates(TakeSpecimenStates.idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}