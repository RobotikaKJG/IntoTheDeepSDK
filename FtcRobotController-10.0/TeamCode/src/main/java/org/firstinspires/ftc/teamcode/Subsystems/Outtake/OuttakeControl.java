package org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;

public class OuttakeControl {
    private final ArmControl armControl;
    private final SpecimenClawControl specimenClawControl;

    public OuttakeControl(ArmControl armControl, SpecimenClawControl specimenClawControl) {
        this.armControl = armControl;
        this.specimenClawControl = specimenClawControl;
    }

    public void update() {
        armControl.update();
        specimenClawControl.update();

        updateOuttakeState();
    }

    private void updateOuttakeState(){
        if(outtakeActive())
            OuttakeStates.setOuttakeState(SubsystemState.Run);
        else
            OuttakeStates.setOuttakeState(SubsystemState.Idle);
    }

    private boolean outtakeActive() {
        return OuttakeStates.getArmState() != ArmStates.down && OuttakeStates.getArmState() != ArmStates.idle;
    }
}