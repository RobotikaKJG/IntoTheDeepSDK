package org.firstinspires.ftc.teamcode.Autonomous;

public enum SpecimenAutonState {
    goToHangFirstSpecimen,
    hangFirstSpecimen,

    goToTakeFirstSample,
    extendSlidesForFirstSample,
    startIntakeForFirstSample,
    checkFirstSamplePickup,
    goToEjectFirstSample,
    ejectFirstSample,

    goToTakeSecondSample,
    extendSlidesForSecondSample,
    startIntakeForSecondSample,
    checkSecondSamplePickup,
    goToEjectSecondSample,
    ejectSecondSample,

    goToTakeThirdSample,
    extendSlidesForThirdSample,
    startIntakeForThirdSample,
    checkThirdSamplePickup,
    goToEjectThirdSample,
    ejectThirdSample,

    goToTakeSpecimen,
    takeSpecimen,
    closeClaw,
    goToPlaceSpecimen,
    placeSpecimen,

    extendExtendoForPark,
    stop,
    idle
}
