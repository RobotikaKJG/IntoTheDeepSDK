package org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;

public class OuttakeControl {
    private final ArmControl armControl;
    private final SpecimenClawControl specimenClawControl;
    private final AutoTakeControl autoTakeControl;
    private final AutoTakeLogic autoTakeLogic;

    public OuttakeControl(ArmControl armControl, SpecimenClawControl specimenClawControl, AutoTakeControl autoTakeControl, AutoTakeLogic autoTakeLogic) {
        this.armControl = armControl;
        this.specimenClawControl = specimenClawControl;
        this.autoTakeControl = autoTakeControl;
        this.autoTakeLogic = autoTakeLogic;
    }

    public void update() {
        armControl.update();
        specimenClawControl.update();
        autoTakeLogic.update();
        autoTakeControl.update();


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