package org.firstinspires.ftc.teamcode.Camera;

import org.firstinspires.ftc.teamcode.Enums.PropPos;

public class PropPosData {
    private double posStrength;
    private PropPos propPos;

    public double getPosStrength() {
        return posStrength;
    }

    public void setPosValues(PropPos propPos,double posStrength)
        {
        this.posStrength = posStrength;
        this.propPos = propPos;
    }

    public PropPos getPropPos() {
        return propPos;
    }
}
