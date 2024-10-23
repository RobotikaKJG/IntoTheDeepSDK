package org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges;

public interface IntakeStateChange {
    public boolean shouldBeStopping();
    public void initialiseStop();
    public void stop();
}
