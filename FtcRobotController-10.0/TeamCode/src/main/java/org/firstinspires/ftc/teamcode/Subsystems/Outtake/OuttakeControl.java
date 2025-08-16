package org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoPlaceSpec.AutoPlaceControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoPlaceSpec.AutoPlaceLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.AutoTakeSpec.AutoTakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.TurnServo.TurnServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmControl;

public class OuttakeControl {
    private final ArmControl armControl;
    private final SpecimenClawControl specimenClawControl;
    private final AutoTakeControl autoTakeControl;
    private final AutoTakeLogic autoTakeLogic;
    private final AutoPlaceControl autoPlaceControl;
    private final AutoPlaceLogic autoPlaceLogic;
    private final TurnServoControl turnServoControl;

    public OuttakeControl(ArmControl armControl, SpecimenClawControl specimenClawControl, AutoTakeControl autoTakeControl, AutoTakeLogic autoTakeLogic, AutoPlaceControl autoPlaceControl, AutoPlaceLogic autoPlaceLogic, TurnServoControl turnServoControl) {
        this.armControl = armControl;
        this.specimenClawControl = specimenClawControl;
        this.autoTakeControl = autoTakeControl;
        this.autoTakeLogic = autoTakeLogic;
        this.autoPlaceControl = autoPlaceControl;
        this.autoPlaceLogic = autoPlaceLogic;
        this.turnServoControl = turnServoControl;
    }

    public void update() {
        armControl.update();
        specimenClawControl.update();
        autoTakeLogic.update();
        autoTakeControl.update();
        autoPlaceLogic.update();
        autoPlaceControl.update();
        turnServoControl.update();


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