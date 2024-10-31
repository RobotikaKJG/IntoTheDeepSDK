package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.IntakeChangeStates;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges.AutoClose;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges.CloseButton;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges.RetractedEject;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges.IntakeStateChange;

public class IntakeStateController implements IntakeStateChange {
    private final IntakeStateChange[] intakeStates;
    private IntakeChangeStates currentState;
    private final IntakeChangeStates[] states = IntakeChangeStates.values();

    public IntakeStateController(MotorControl motorControl, ServoControl servoControl, SensorControl sensorControl, EdgeDetection edgeDetection, SlideLogic slideLogic, IntakeController intakeController, ElapsedTime elapsedTime) {
        intakeStates = new IntakeStateChange[]
                {
                        new AutoClose(sensorControl, slideLogic, intakeController, elapsedTime,servoControl,motorControl),
                        new RetractedEject(sensorControl, slideLogic,intakeController,elapsedTime,servoControl,motorControl),
                        new CloseButton(slideLogic,intakeController,elapsedTime,servoControl,motorControl,edgeDetection)
                };
    }

    @Override
    public boolean shouldBeStopping() { //potentially inefficient if multiple check color sensor (3/4?)
        currentState = IntakeChangeStates.idle;
        for(int i = 0; i < intakeStates.length; i++)
            if(intakeStates[i].shouldBeStopping()) {
                currentState = states[i];
                break;
            }

        return currentState != IntakeChangeStates.idle;
    }

    @Override
    public void initialiseStop() {
        switch (currentState) {
            case autoClose:
                intakeStates[0].initialiseStop();
                break;
            case extendedEject:
                intakeStates[1].initialiseStop();
                break;
            case retractedEject:
                intakeStates[2].initialiseStop();
                break;
            case closeButton:
                intakeStates[3].initialiseStop();
                break;
            case idle://This should not be here, but just in case something breaks
                break;//hopefully this won't cause debugging pain
        }
    }

    @Override
    public void stop() {
        switch (currentState) {
            case autoClose:
                intakeStates[0].stop();
                break;
            case extendedEject:
                intakeStates[1].stop();
                break;
            case retractedEject:
                intakeStates[2].stop();
                break;
            case closeButton:
                intakeStates[3].stop();
                break;
            case idle: //same here
                break;
        }

    }
}
