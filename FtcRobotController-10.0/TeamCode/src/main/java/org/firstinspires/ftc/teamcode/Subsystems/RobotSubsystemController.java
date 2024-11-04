package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;

public interface RobotSubsystemController {
    void updateState();

    void start();

    void run();

    void stop();

    void idle();

    SubsystemState getState();
}
