package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.Actions.ArmDown;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.Actions.ArmUp;

public class ArmActions {

    public Action up(){
        return new ArmUp();
    }

    public Action down(){
        return new ArmDown();
    }


}
