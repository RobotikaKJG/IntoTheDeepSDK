package org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class SampleEjectionLogic {
    private final SensorControl sensorControl;
    private boolean wasWrongColor = false;
    private double currentWait = 0;

    public SampleEjectionLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update(){
        if(GlobalVariables.isAutonomous) return;
//        if(wrongColor()) {
            if (shouldOpen()) {
//            if(!GlobalVariables.isAutonomous)
                IntakeStates.setLatchState(LatchStates.open);
            }
//            wasWrongColor = true;
//        }
//        else
//            wasWrongColor = false;

        if(!ejectionServoClosed() && shouldClose())
        {
            IntakeStates.setLatchState(LatchStates.closed);
            addWaitTime(0.5);
        }
    }

    private boolean shouldOpen() {
//        if(!wasWrongColor && extendoExtended() && motorForward() && ejectionServoClosed() && getSeconds() > currentWait) {
        if(extendoExtended() && motorForward() && ejectionServoClosed() && getSeconds() > currentWait && wrongColor()) {

                addWaitTime(1);
            return  true;
        }
        return false;
    }


    private boolean shouldClose()
    {
        return currentWait < getSeconds() && extendoExtended() && IntakeStates.getAutoCloseState() != AutoCloseStates.idle;
    }

    private boolean wrongColor(){
        return sensorControl.isOtherAllianceColor() || sensorControl.isYellow();
    }


    private static boolean extendoExtended() {
        return IntakeStates.getArmSlideState() != ArmSlideStates.closed;
    }

    private static boolean motorForward() {
        return IntakeStates.getMotorState() == IntakeMotorStates.forward;
    }

    private static boolean ejectionServoClosed() {
        return IntakeStates.getLatchState() == LatchStates.closed;
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}