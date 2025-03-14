package org.firstinspires.ftc.teamcode.Autonomous;

public enum SampleAutonState {
    waitForFlip,
    releaseSample,
    driveToSecondSample,
    startIntake,
    checkSamplePickup,
    retractOuttake,
    prepareNextCycle,
    waitForFlipSecondSample,
    releaseSecondSample,

    thirdSampleIntakePath,
    startIntakeForThirdSample,
    checkSamplePickupForThirdSample,
    retractOuttakeForThirdSample,
    thirdSampleOuttakePath,
    prepareNextCycleForThirdSample,
    waitForFlipThirdSample,
    releaseThirdSample,

    forthSampleIntakePath,
    startIntakeForForthSample,
    checkSamplePickupForForthSample,
    retractOuttakeForForthSample,
    forthSampleOuttakePath,
    prepareNextCycleForForthSample,
    waitForFlipForthSample,
    releaseForthSample,

    fifthSampleIntakePath,
    fifthSampleOuttakePath,
    prepareNextCycleForFifthSample,
    startIntakeForFifthSample,
    waitForFlipFifthSample,
    releaseFifthSample,
    prepareNextCycleForSubSample,
    moveToSub,
    waiting,

    startOuttakeSample,
    startIntakeSample,

    stop            // End of autonomous sequence
}

