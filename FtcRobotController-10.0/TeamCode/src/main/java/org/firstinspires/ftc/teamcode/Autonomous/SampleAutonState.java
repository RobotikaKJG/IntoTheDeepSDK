package org.firstinspires.ftc.teamcode.Autonomous;

public enum SampleAutonState {
    waitForFlip,
    releaseSample,
    driveToPlaceSecondSample,
    secondSampleIntakePath,
    startIntakeForSecondSample,
    checkSecondSamplePickup,
    retractOuttakeForSecondSample,
    prepareNextCycle,
    waitForFlipSecondSample,
    releaseSecondSample,

    thirdSampleIntakePath,
    startIntakeForThirdSample,
    checkThirdSamplePickup,
    retractOuttakeForThirdSample,
    thirdSampleOuttakePath,
    prepareNextCycleForThirdSample,
    waitForFlipThirdSample,
    releaseThirdSample,

    forthSampleIntakePath,
    startIntakeForForthSample,
    checkForthSamplePickup,
    retractOuttakeForForthSample,
    forthSampleOuttakePath,
    prepareNextCycleForForthSample,
    waitForFlipForthSample,
    releaseForthSample,

    prepareNextCycleForFifthSample,
    fifthSampleIntakePath,
    extendExtendoForFifthSample,
    startIntakeForFifthSample,
    waiting,
    fifthSampleOuttakePath,

    waitForFlipFifthSample,
    releaseFifthSample,
    prepareNextCycleForSubSample,

    stop            // End of autonomous sequence
}

