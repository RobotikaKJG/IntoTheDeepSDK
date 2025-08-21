package org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoPlaceSpec;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class AutoPlaceLogic {
    private double currentWait = 0;

    public AutoPlaceLogic() {}

    public void update() {
        switch (OuttakeStates.getAutoPlaceState()) {
            case activate:
                activate();
                break;
            case placeSpec:
                placeSpec();
                break;
            case releaseSpec:
                releaseSpec();
                break;
            case idle:
                break;
        }
    }

    private void activate() {
        if(isClawClosed() && OuttakeStates.getArmState() == ArmStates.placeSpecimen) {
            OuttakeStates.setAutoPlaceState(AutoPlaceStates.placeSpec);
            addWaitTime(OuttakeConstants.specimenPlaceWait);
        }
    }

    private void placeSpec() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setAutoPlaceState(AutoPlaceStates.waitToRelease);
    }



    private void releaseSpec() {
        OuttakeStates.setAutoPlaceState(AutoPlaceStates.idle);
    }

    private boolean isClawClosed() {
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.freeMove || OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}

