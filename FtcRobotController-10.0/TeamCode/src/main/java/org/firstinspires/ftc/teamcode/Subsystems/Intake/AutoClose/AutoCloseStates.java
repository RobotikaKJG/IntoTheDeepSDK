package org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose;

public enum AutoCloseStates {
    checkColor,
    securedGoodSample,
    waitForRetractConfirmation,
    waitToRetract,
    waitForPivotConfirmation,
    pivot,
    waitForReleaseConfirmation,
    openLatch,
    release,
    pivotDown,
    idle
}
