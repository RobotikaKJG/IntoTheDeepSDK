package org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class SampleEjectionLogic {
    private final SensorControl sensorControl;
    private boolean wasShouldOpen = false;

    public SampleEjectionLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update(){
        if(shouldOpen()) {
            IntakeStates.setEjectionServoState(EjectionServoStates.open);
            wasShouldOpen = true;
        }
        if(wasShouldOpen && shouldClose())
        {
            wasShouldOpen = false;
            IntakeStates.setEjectionServoState(EjectionServoStates.closed);
        }
    }

    private boolean shouldOpen() {
        return wrongColor() && extendoExtended() && motorForward() && ejectionServoClosed();
    }

    private boolean shouldClose()
    {
        return correctColor() && !ejectionServoClosed();
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
        return IntakeStates.getEjectionServoState() == EjectionServoStates.closed;
    }

    private boolean correctColor(){
        return sensorControl.isAllianceColor() ||sensorControl.isYellow();
    }
}