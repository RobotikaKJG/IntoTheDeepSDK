package org.firstinspires.ftc.teamcode.Subsystems.Drone;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class DroneController implements RobotSubsystemController {

    private final DroneTrigger droneTrigger  = new DroneTrigger();
    private final EdgeDetection edgeDetection;

    private final ServoControl servoControl;

    private SubsystemState droneState = SubsystemState.Idle;

    public DroneController(ServoControl servoControl, EdgeDetection edgeDetection) {
        this.edgeDetection = edgeDetection;
        this.servoControl = servoControl;
    }

    @Override
    public void updateState() {
        switch (droneState) {
            case Start:
                start();
                break;
            case Run:
                run();
                break;
            case Stop:
                stop();
                break;
            case Idle:
                idle();
                break;
        }
    }

    @Override
    public void start() {
        droneState = SubsystemState.Run;
    }

    @Override
    public void run() {
        servoControl.setServoPos(ServoConstants.drone, DroneConstants.droneServoMaxPos);
        droneState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        droneState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(droneTrigger.getTrigger())) return;

        droneState = SubsystemState.Start;
    }
}
