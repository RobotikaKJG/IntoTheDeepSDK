package org.firstinspires.ftc.teamcode.Autonomous;

public enum SampleAutonState {
    waitForFlip,
    releaseSample,
    driveToPlaceFirstSample,
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
    startOuttakeSample2,

    searchingForward,
    returningAfterPickup,

    stop            // End of autonomous sequence
}

