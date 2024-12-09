package org.firstinspires.ftc.teamcode.Subsystems.Intake.Old;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.StateChanges.AutoClose;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.StateChanges.CloseButton;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.StateChanges.RetractedEject;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.StateChanges.IntakeStateChange;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Old.OuttakeServoController;

public class IntakeStateController implements IntakeStateChange {
    private final IntakeStateChange[] intakeStates;
    private IntakeChangeStates currentState;
    private final IntakeChangeStates[] states = IntakeChangeStates.values();

    public IntakeStateController(MotorControl motorControl, ServoControl servoControl, SensorControl sensorControl, EdgeDetection edgeDetection, SlideLogic slideLogic, IntakeController intakeController, ElapsedTime elapsedTime, OuttakeServoController outtakeServoController) {
        intakeStates = new IntakeStateChange[]
                {
                        new AutoClose(sensorControl, slideLogic, intakeController, elapsedTime,servoControl,motorControl,edgeDetection,outtakeServoController),
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
            case retractedEject:
                intakeStates[1].initialiseStop();
                break;
            case closeButton:
                intakeStates[2].initialiseStop();
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
            case retractedEject:
                intakeStates[1].stop();
                break;
            case closeButton:
                intakeStates[2].stop();
                break;
            case idle: //same here
                break;
        }

    }
}
