package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose.ManualCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;

public class IntakeStates {
    private static SubsystemState intakeState = SubsystemState.Idle;
    private static IntakeRotationStates motorState = IntakeRotationStates.idleWasForward;
    private static IntakeRotationStates servoState = IntakeRotationStates.idle;
    private static ExtendoStates extendoState = ExtendoStates.retracted;
    private static AutoCloseStates autoCloseStates = AutoCloseStates.idle;
    private static ManualCloseStates manualCloseStates = ManualCloseStates.idle;
    private static EjectionServoStates ejectionServoStates = EjectionServoStates.closed;
    public static int extensionTarget; //temporary, NOTE

    public static void setInitialStates(){
        intakeState = SubsystemState.Idle;
        motorState = IntakeRotationStates.idleWasForward;
        servoState = IntakeRotationStates.idle;
        extendoState = ExtendoStates.retracted;
        autoCloseStates = AutoCloseStates.idle;
        manualCloseStates = ManualCloseStates.idle;
        ejectionServoStates = EjectionServoStates.closed;
    }

    public static SubsystemState getIntakeState() {
        return intakeState;
    }

    public static void setIntakeState(SubsystemState state) {
        intakeState = state;
    }


    public static IntakeRotationStates getMotorState() {
        return motorState;
    }

    public static void setMotorState(IntakeRotationStates state) {
        motorState = state;
    }


    public static IntakeRotationStates getServoState() { return servoState; }

    public static void setServoState(IntakeRotationStates state) { servoState = state; }


    public static ExtendoStates getExtendoState() { return extendoState; }

    public static void setExtendoState(ExtendoStates state) { extendoState = state; }


    public static AutoCloseStates getAutoCloseStates() { return autoCloseStates; }

    public static void setAutoCloseStates(AutoCloseStates state) { autoCloseStates = state; }


    public static ManualCloseStates getManualCloseStates() { return manualCloseStates; }

    public static void setManualCloseStates(ManualCloseStates state) { manualCloseStates = state; }


    public static EjectionServoStates getEjectionServoState() {
        return ejectionServoStates;
    }

    public static void setEjectionServoState(EjectionServoStates state){
        ejectionServoStates = state;
    }
}
