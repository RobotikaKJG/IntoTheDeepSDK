package org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;

public class SampleEjectionLogic {
    private final SensorControl sensorControl;
    private boolean wasShouldOpen = false;
    private double currentWait = 0;

    public SampleEjectionLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update(){
        if(shouldOpen()) {
            if(!GlobalVariables.isAutonomous)
                OuttakeStates.setSampleLockState(SampleLockStates.open);
            wasShouldOpen = true;
        }
        if(!ejectionServoClosed() && shouldClose())
        {
            OuttakeStates.setSampleLockState(SampleLockStates.closed);
            addWaitTime(2);
        }

        if(correctColor())
            wasShouldOpen = false;
    }

    private boolean shouldOpen() {
        if(wrongColor() && extendoExtended() && motorForward() && ejectionServoClosed() && getSeconds() > currentWait) {
            addWaitTime(0.6);
            return  true;
        }
        return false;
    }


    private boolean shouldClose()
    {
        return currentWait < getSeconds();
    }

    private boolean wrongColor(){
        return sensorControl.isOtherAllianceColor();
    }


    private static boolean extendoExtended() {
        return IntakeStates.getExtendoState() == ExtendoStates.extended;
    }

    private static boolean motorForward() {
        return IntakeStates.getMotorState() == IntakeMotorStates.forward;
    }

    private static boolean ejectionServoClosed() {
            return OuttakeStates.getSampleLockState() == SampleLockStates.closed;
    }

    private boolean correctColor(){
        return sensorControl.isAllianceColor() ||sensorControl.isYellow();
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
